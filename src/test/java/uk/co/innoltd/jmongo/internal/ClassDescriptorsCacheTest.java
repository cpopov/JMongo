package uk.co.innoltd.jmongo.internal;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import uk.co.innoltd.jmongo.entities.EntityWithString;
import uk.co.innoltd.jmongo.internal.ClassDescriptor;
import uk.co.innoltd.jmongo.internal.ClassDescriptorsCache;

public class ClassDescriptorsCacheTest {
	private ClassDescriptorsCache cache;
	
	@Before
	public void setup() {
		cache=new ClassDescriptorsCache();
	}
	
	@Test
	public void shouldPopulateCacheWhenEmpty() {
		assertEquals(0, cache.getSize());
		ClassDescriptor descriptor = cache.get(EntityWithString.class);
		assertEquals(EntityWithString.class,descriptor.getTheClass());
		assertEquals(1, cache.getSize());
	}
	
	@Test
	public void shouldRetrieveObjectsFromCache() {
		cache.get(String.class);
		assertEquals(1, cache.getSize());
		cache.get(EntityWithString.class);
		assertEquals(2, cache.getSize());
		ClassDescriptor descriptor = cache.get(EntityWithString.class);
		assertEquals(2, cache.getSize());
		assertEquals(EntityWithString.class,descriptor.getTheClass());		
	}
}
