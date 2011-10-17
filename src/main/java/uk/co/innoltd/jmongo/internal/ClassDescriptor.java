package uk.co.innoltd.jmongo.internal;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import uk.co.innoltd.jmongo.JMongoException;

public class ClassDescriptor {
	private final List<FieldDescriptor> fields = new ArrayList<FieldDescriptor>();
	private Class<?> theClass;

	public List<FieldDescriptor> getFields() {
		return fields;
	}

	public static ClassDescriptor get(Class<?> theClass) {
		ClassDescriptor classDescriptor = new ClassDescriptor();
		classDescriptor.theClass = theClass;
		// get all fields for current and all super classes
		while (theClass != null && !theClass.equals(Object.class)) {
			for (Field field : theClass.getDeclaredFields()) {
				if (ReflectionUtils.shouldPersist(field)) {
					classDescriptor.fields.add(FieldDescriptor.get(field));
				}
			}
			theClass = theClass.getSuperclass();
		}
		return classDescriptor;
	}

	public Object newInstance() {
		try {
			return theClass.newInstance();
		} catch (Exception e) {
			throw new JMongoException("Failed to instanciate " + theClass.getName()
					+ ". A no-arg constructor is required.", e);
		}
	}

	public Class<?> getTheClass() {
		return theClass;
	}

	@Override
	public String toString() {
		return "ClassDescriptor [theClass=" + theClass + "]";
	}

}
