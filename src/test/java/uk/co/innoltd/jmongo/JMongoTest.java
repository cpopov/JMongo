package uk.co.innoltd.jmongo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import uk.co.innoltd.jmongo.entities.EntityWithPrimitives;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class JMongoTest {
	private JMongo jMongo;

	@Before
	public void init() {
		jMongo = new JMongo();
	}

	@Test
	public void shouldReturnNullIfNull() {
		assertNull(jMongo.fromDBObject(String.class, null));
		assertNull(jMongo.toDBObject(null));
	}

	@Test
	public void shouldMarshallPrimitives() throws Exception {
		EntityWithPrimitives entity = new EntityWithPrimitives((byte) 12, 'c', (short) 11, 13, 23l, 14.5f, 16.3d, true);
		DBObject dbObject = jMongo.toDBObject(entity);
		assertEquals('c', dbObject.get("c"));
		assertEquals((short) 11, dbObject.get("sh"));
		assertEquals(13, dbObject.get("number"));
		assertEquals((byte) 12, dbObject.get("a"));
		assertEquals(23l, dbObject.get("l"));
		assertEquals(14.5f, dbObject.get("f"));
		assertEquals(16.3d, dbObject.get("d"));
		assertEquals(true, dbObject.get("b"));		
	}

	@Test
	public void shouldUnMarshallPrimitives() throws Exception {
		DBObject dbObject = new BasicDBObject();
		dbObject.put("number", 13);
		dbObject.put("c", "c");
		dbObject.put("a", 12);
		dbObject.put("sh", 11);
		dbObject.put("l", 23l);
		dbObject.put("f", 14.5f);
		dbObject.put("d", 16.3d);
		dbObject.put("b", true);

		EntityWithPrimitives entity = jMongo.fromDBObject(EntityWithPrimitives.class, dbObject);
		assertEquals(13, entity.getNumber());
		assertEquals('c', entity.getC());
		assertEquals(12, entity.getA());
		assertEquals(11, entity.getSh());
		assertEquals(23l, entity.getL());
		assertTrue(14.5f == entity.getF());
		assertTrue(16.3d == entity.getD());
		assertTrue(entity.isB());
	}
}
