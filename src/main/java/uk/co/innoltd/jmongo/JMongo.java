package uk.co.innoltd.jmongo;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import uk.co.innoltd.jmongo.internal.ClassDescriptor;
import uk.co.innoltd.jmongo.internal.ClassDescriptorsCache;
import uk.co.innoltd.jmongo.internal.FieldDescriptor;
import uk.co.innoltd.jmongo.internal.ReflectionUtils;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class JMongo {
	private ClassDescriptorsCache cache = new ClassDescriptorsCache();

	public DBObject toDBObject(Object obj) {
		if (obj==null) {
			return null;
		}
		DBObject dbObject = new BasicDBObject();
		ClassDescriptor classDescriptor = cache.get(obj.getClass());
		for (FieldDescriptor fieldDescriptor : classDescriptor.getFields()) {
			dbObject.put(fieldDescriptor.getName(), toDBObjectRecursive(obj, fieldDescriptor));
		}
		return dbObject;
	}

	@SuppressWarnings("rawtypes")
	public Object toDBObjectRecursive(Object object, FieldDescriptor fieldDescriptor) {
		if (fieldDescriptor.isArray()) {
			if (ReflectionUtils.isSimpleClass(fieldDescriptor.getField().getType().getComponentType())) {
				return fieldDescriptor.getFieldValue(object);
			} else {
				Object[] array = (Object[]) fieldDescriptor.getFieldValue(object);
				BasicDBList fieldObj = new BasicDBList();
				for (Object el : array) {
					fieldObj.add(toDBObject(el));
				}
				return fieldObj;
			}
		} else if (fieldDescriptor.isIterable()) {
			Iterable col = (Iterable) fieldDescriptor.getFieldValue(object);
			BasicDBList fieldObj = new BasicDBList();
			for (Object el : col) {
				if (ReflectionUtils.isSimpleClass(el.getClass())) {
					fieldObj.add(el);
				} else {
					fieldObj.add(toDBObject(el));
				}
			}
			return fieldObj;
		} else if (fieldDescriptor.isObject()) {
			DBObject dbObject = new BasicDBObject();
			for (FieldDescriptor childDescriptor : fieldDescriptor.getChildren()) {
				Object fieldValue = fieldDescriptor.getFieldValue(object);
				dbObject.put(childDescriptor.getName(), toDBObjectRecursive(fieldValue, childDescriptor));
			}
			return dbObject;
		} else if (fieldDescriptor.isMap()) {
			DBObject dbObject = new BasicDBObject();
			Map map = (Map) fieldDescriptor.getFieldValue(object);
			for (Object key : map.keySet()) {
				Object el = map.get(key);
				if (ReflectionUtils.isSimpleClass(el.getClass())) {
					dbObject.put(key.toString(), el);
				} else {
					dbObject.put(key.toString(), toDBObject(el));
				}
			}
			return dbObject;
		} else {
			return fieldDescriptor.getFieldValue(object);
		}
	}

	@SuppressWarnings("unchecked")
	public <T> T fromDBObject(Class<T> clazz, DBObject dbObject) {
		if (dbObject==null) {
			return null;
		}
		ClassDescriptor classDescriptor = cache.get(clazz);
		Object object = classDescriptor.newInstance();
		for (FieldDescriptor fieldDescriptor : classDescriptor.getFields()) {
			try {
				fieldDescriptor.getField().set(object,
						fromDBObjectRecursive(dbObject.get(fieldDescriptor.getName()), fieldDescriptor));
			} catch (Exception e) {
				throw new JMongoException("Failed to set field value " + fieldDescriptor.getName(), e);
			}
		}
		return (T) object;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Object fromDBObjectRecursive(Object dbObject, FieldDescriptor fieldDescriptor) {
		if (dbObject==null) {
			return null;
		}
		Class<?> fieldType = fieldDescriptor.getField().getType();
		if (fieldDescriptor.isSimple()) {
			if (fieldDescriptor.isEnum()) {
				return Enum.valueOf((Class<Enum>) fieldType, (String) dbObject);
			}
			return dbObject;
		} else if (fieldDescriptor.isArray()) {
			BasicDBList dbList = (BasicDBList) dbObject;
			if (fieldType.getComponentType().isPrimitive()) {
				return ReflectionUtils.dbListToArrayOfPrimitives(dbList, fieldType);
			}
			List list = new ArrayList();
			for (Object listEl : dbList) {
				if (ReflectionUtils.isSimpleClass(listEl.getClass())) {
					list.add(listEl);
				} else {
					list.add(fromDBObject((Class<Object>) fieldType.getComponentType(), (DBObject) listEl));
				}
			}

			Object[] arrayPrototype = (Object[]) Array.newInstance(fieldType.getComponentType(), 0);
			return list.toArray(arrayPrototype);
		} else if (fieldDescriptor.isList()) {
			BasicDBList dbList = (BasicDBList) dbObject;
			List list = (List) fieldDescriptor.newInstance();
			for (Object listEl : dbList) {
				if (ReflectionUtils.isSimpleClass(listEl.getClass())) {
					list.add(listEl);
				} else {
					list.add(fromDBObject(ReflectionUtils.genericType(fieldDescriptor.getField()), (DBObject) listEl));
				}
			}
			return list;
		} else if (fieldDescriptor.isSet()) {
			BasicDBList dbList = (BasicDBList) dbObject;
			Set set = (Set) fieldDescriptor.newInstance();
			for (Object listEl : dbList) {
				if (ReflectionUtils.isSimpleClass(listEl.getClass())) {
					set.add(listEl);
				} else {
					set.add(fromDBObject(ReflectionUtils.genericType(fieldDescriptor.getField()), (DBObject) listEl));
				}
			}
			return set;
		} else if (fieldDescriptor.isMap()) {
			DBObject dbMap = (DBObject) dbObject;
			Map map = (Map) fieldDescriptor.newInstance();
			for (Object key : dbMap.keySet()) {
				Object mapEl = dbMap.get(key.toString());
				if (ReflectionUtils.isSimpleClass(mapEl.getClass())) {
					map.put(key,mapEl);
				} else {
					map.put(key,fromDBObject(ReflectionUtils.genericTypeOfMapValue(fieldDescriptor.getField()), (DBObject) mapEl));
				}
			}
			return map;
		} else if (fieldDescriptor.isObject()) {
			Object object = fieldDescriptor.newInstance();
			for (FieldDescriptor childDescriptor : fieldDescriptor.getChildren()) {
				try {
					childDescriptor.getField()
							.set(object,
									fromDBObjectRecursive(((DBObject) dbObject).get(childDescriptor.getName()),
											childDescriptor));
				} catch (Exception e) {
					throw new JMongoException("Failed to set field value " + childDescriptor.getName(), e);
				}
			}
			return object;
		}

		return null;
	}
}
