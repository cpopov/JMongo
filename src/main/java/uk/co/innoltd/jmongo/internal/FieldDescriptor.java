package uk.co.innoltd.jmongo.internal;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import uk.co.innoltd.jmongo.JMongoException;

public class FieldDescriptor {
	private Field field;
	private boolean simple;
	private boolean primitive;
	private boolean array;
	private boolean iterable;
	private boolean list;
	private boolean set;
	private boolean map;
	private boolean object;
	private boolean isEnum;
	private List<FieldDescriptor> children = new ArrayList<FieldDescriptor>();

	private void addChild(FieldDescriptor child) {
		this.children.add(child);
	}

	private FieldDescriptor(Field field) {
		if (ReflectionUtils.isList(field)) {
			this.iterable = true;
			this.list = true;
		} else if (ReflectionUtils.isSet(field)) {
			this.iterable = true;
			this.set = true;
		} else if (ReflectionUtils.isMap(field)) {
			this.map = true;
		} else if (ReflectionUtils.isArray(field)) {
			this.array = true;
		} else if (ReflectionUtils.isSimpleField(field)) {
			this.simple = true;
			if (ReflectionUtils.isPrimitive(field)) {
				this.primitive = true;
			}
		} else {
			this.object = true;
		}
		this.isEnum = field.getType().isEnum();
		this.field = field;
		this.field.setAccessible(true);
	}

	public static FieldDescriptor get(Field field) {
		FieldDescriptor fieldDescriptor = new FieldDescriptor(field);
		if (ReflectionUtils.isSimpleField(field)) {
			return fieldDescriptor;
		}
		// get all fields for current and all super classes
		Class<?> fieldClass = field.getType();
		while (fieldClass != null && !fieldClass.equals(Object.class)) {
			for (Field childField : fieldClass.getDeclaredFields()) {
				if (ReflectionUtils.shouldPersist(childField)) {
					fieldDescriptor.addChild(get(childField));
				}
			}
			fieldClass = fieldClass.getSuperclass();
		}
		return fieldDescriptor;
	}

	public Field getField() {
		return field;
	}

	public boolean isSimple() {
		return simple;
	}

	public boolean isPrimitive() {
		return primitive;
	}

	public boolean isArray() {
		return array;
	}

	public boolean isIterable() {
		return iterable;
	}

	public boolean isList() {
		return list;
	}

	public boolean isSet() {
		return set;
	}

	public boolean isMap() {
		return map;
	}

	public boolean isObject() {
		return object;
	}

	public boolean isEnum() {
		return isEnum;
	}

	public String getName() {
		return field.getName();
	}

	public List<FieldDescriptor> getChildren() {
		return children;
	}

	public Object getFieldValue(Object object) {
		try {
			Object fieldValue = field.get(object);
			if (fieldValue instanceof Enum) {
				fieldValue = ((Enum<?>) fieldValue).name();
			}
			return fieldValue;
		} catch (Exception e) {
			throw new JMongoException("Unbale to read field " + field.getName(), e);
		}
	}

	@SuppressWarnings("rawtypes")
	public Object newInstance() {
		try {
			if (field.getType().isInterface()) {
				if (isList()) {
					return new ArrayList();
				} else if (isSet()) {
					return new HashSet();
				} else if (isMap()) {
					return new HashMap();
				} else {
					throw new JMongoException("Unable to instanciate " + field.getType());
				}
			} else {
				return field.getType().newInstance();
			}
		} catch (Exception e) {
			throw new JMongoException("Failed to instanciate " + field.getType().getName()
					+ ". A no-arg constructor is required.", e);
		}
	}

	@Override
	public String toString() {
		return field.getName();
	}
}
