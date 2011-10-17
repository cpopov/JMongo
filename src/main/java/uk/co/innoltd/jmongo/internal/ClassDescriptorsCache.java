package uk.co.innoltd.jmongo.internal;

import java.util.HashMap;
import java.util.Map;

public class ClassDescriptorsCache {
	private final Map<String, ClassDescriptor> descriptors;

	public ClassDescriptorsCache() {
		descriptors = new HashMap<String, ClassDescriptor>();
	}

	public ClassDescriptor get(Class<?> clazz) {
		ClassDescriptor descriptor = descriptors.get(clazz.getCanonicalName());
		if (descriptor==null) {
			descriptor = ClassDescriptor.get(clazz);
			// TODO see if there is any danger here when multithreaded access.			
			descriptors.put(clazz.getCanonicalName(), descriptor);
		}
		return descriptor;
	}
	
	int getSize() {
		return descriptors.size();
	}

}
