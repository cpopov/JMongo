package uk.co.innoltd.jmongo.internal;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import uk.co.innoltd.jmongo.entities.EntityWithString;
import uk.co.innoltd.jmongo.internal.ClassDescriptor;
import uk.co.innoltd.jmongo.internal.FieldDescriptor;

public class ClassDescriptorTest {

	@Test
	public void testInstanciation() {
		ClassDescriptor desc = ClassDescriptor.get(EntityWithString.class);
		assertEquals(EntityWithString.class, desc.getTheClass());
		FieldDescriptor fd1 = desc.getFields().get(0);
		FieldDescriptor fd2 = desc.getFields().get(1);
		assertEquals("_id", fd1.getName());
		assertEquals("string", fd2.getName());
	}

}
