package uk.co.innoltd.jmongo.inttest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import uk.co.innoltd.jmongo.JMongo;
import uk.co.innoltd.jmongo.entities.ChildObject;
import uk.co.innoltd.jmongo.entities.EntityWithArray;
import uk.co.innoltd.jmongo.entities.EntityWithArrayOfPrimitives;
import uk.co.innoltd.jmongo.entities.EntityWithDate;
import uk.co.innoltd.jmongo.entities.EntityWithEmbeddedDoc;
import uk.co.innoltd.jmongo.entities.EntityWithEnums;
import uk.co.innoltd.jmongo.entities.EntityWithEnums.EntityType;
import uk.co.innoltd.jmongo.entities.EntityWithIgnoredField;
import uk.co.innoltd.jmongo.entities.EntityWithInt;
import uk.co.innoltd.jmongo.entities.EntityWithList;
import uk.co.innoltd.jmongo.entities.EntityWithListOfString;
import uk.co.innoltd.jmongo.entities.EntityWithMap;
import uk.co.innoltd.jmongo.entities.EntityWithObjectId;
import uk.co.innoltd.jmongo.entities.EntityWithSetOfGenerics;
import uk.co.innoltd.jmongo.entities.EntityWithStatics;
import uk.co.innoltd.jmongo.entities.EntityWithString;
import uk.co.innoltd.jmongo.entities.ParentObject;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class JMongoIntTest {
	private JMongo jMongo;
	private DB db;

	@Before
	public void setup() throws Exception {
		Mongo m = new Mongo("localhost", 27017);
		db = m.getDB("jmongo-test");
		db.dropDatabase();
		jMongo = new JMongo();
	}

	@Test
	public void canSaveStringProperty() throws Exception {
		EntityWithString entity = new EntityWithString("hello world");
		DBObject dbObject = jMongo.toDBObject(entity);
		DBCollection collection = db.getCollection("entity");
		collection.save(dbObject);
		dbObject = collection.findOne();
		assertNotNull(dbObject.get("_id"));
		assertEquals("hello world", dbObject.get("string"));
		entity = jMongo.fromDBObject(EntityWithString.class, dbObject);
		assertEquals(entity.get_id(), dbObject.get("_id"));
		assertEquals("hello world", entity.getString());
	}

	@Test
	public void canSaveIntProperty() throws Exception {
		EntityWithInt entity = new EntityWithInt(12);
		DBObject dbObject = jMongo.toDBObject(entity);
		DBCollection collection = db.getCollection("entity");
		collection.save(dbObject);
		dbObject = collection.findOne();
		assertNotNull(dbObject.get("_id"));
		assertEquals(12, dbObject.get("number"));
		entity = jMongo.fromDBObject(EntityWithInt.class, dbObject);
		assertEquals(entity.get_id(), dbObject.get("_id"));
		assertEquals(12, entity.getNumber());
	}

	@Test
	public void shouldNotSaveStaticProperty() throws Exception {
		EntityWithStatics entity = new EntityWithStatics();
		DBObject dbObject = jMongo.toDBObject(entity);
		DBCollection collection = db.getCollection("entity");
		collection.save(dbObject);
		dbObject = collection.findOne();
		assertNotNull(dbObject.get("_id"));
		assertNull(dbObject.get("n"));
	}

	@Test
	public void canSaveDateProperty() throws Exception {
		Date date = new Date();
		EntityWithDate entity = new EntityWithDate(date);
		DBObject dbObject = jMongo.toDBObject(entity);
		DBCollection collection = db.getCollection("entity");
		collection.save(dbObject);
		dbObject = collection.findOne();
		assertNotNull(dbObject.get("_id"));
		assertEquals(date, dbObject.get("date"));
		entity = jMongo.fromDBObject(EntityWithDate.class, dbObject);
		assertEquals(entity.get_id(), dbObject.get("_id"));
		assertEquals(date, entity.getDate());
	}

	@Test
	public void canSaveEnumProperty() throws Exception {
		EntityWithEnums entity = new EntityWithEnums(EntityType.COMPLEX);
		DBObject dbObject = jMongo.toDBObject(entity);
		DBCollection collection = db.getCollection("entity");
		collection.save(dbObject);
		dbObject = collection.findOne();
		assertNotNull(dbObject.get("_id"));
		assertEquals("COMPLEX", dbObject.get("type"));
		entity = jMongo.fromDBObject(EntityWithEnums.class, dbObject);
		assertEquals(entity.get_id(), dbObject.get("_id"));
		assertEquals(EntityType.COMPLEX, entity.getType());
	}

	@Test
	public void canSaveArrayOfDocuments() throws Exception {
		EntityWithString[] array = new EntityWithString[2];
		array[0] = new EntityWithString("S1");
		array[1] = new EntityWithString("S2");
		EntityWithArray entity = new EntityWithArray(array);
		DBObject dbObject = jMongo.toDBObject(entity);
		DBCollection collection = db.getCollection("entity");
		collection.save(dbObject);
		dbObject = collection.findOne();
		assertNotNull(dbObject.get("_id"));
		BasicDBList list = (BasicDBList) dbObject.get("array");
		assertEquals(2, list.size());
		assertEquals("S1", ((DBObject) list.get(0)).get("string"));
		assertEquals("S2", ((DBObject) list.get(1)).get("string"));
		entity = jMongo.fromDBObject(EntityWithArray.class, dbObject);
		assertEquals(dbObject.get("_id"), entity.get_id());
		assertEquals(2, entity.getArray().length);
		assertEquals("S1", entity.getArray()[0].getString());
		assertEquals("S2", entity.getArray()[1].getString());
	}

	@Test
	public void canSaveArrayOfPrimitives() throws Exception {
		EntityWithArrayOfPrimitives entity = new EntityWithArrayOfPrimitives(new long[] { 1l, 2l });
		DBObject dbObject = jMongo.toDBObject(entity);
		DBCollection collection = db.getCollection("entity");
		collection.save(dbObject);
		dbObject = collection.findOne();
		assertNotNull(dbObject.get("_id"));
		BasicDBList list = (BasicDBList) dbObject.get("array");
		assertEquals(2, list.size());
		assertEquals(1l, list.get(0));
		assertEquals(2l, list.get(1));
		entity = jMongo.fromDBObject(EntityWithArrayOfPrimitives.class, dbObject);
		assertEquals(dbObject.get("_id"), entity.get_id());
		assertEquals(2, entity.getArray().length);
		assertEquals(1l, entity.getArray()[0]);
		assertEquals(2l, entity.getArray()[1]);
	}

	@Test
	public void canSaveListOfDocuments() throws Exception {
		List<EntityWithString> list = new ArrayList<EntityWithString>();
		list.add(new EntityWithString("S1"));
		list.add(new EntityWithString("S2"));
		EntityWithList entity = new EntityWithList(list);
		DBObject dbObject = jMongo.toDBObject(entity);
		DBCollection collection = db.getCollection("entity");
		collection.save(dbObject);
		dbObject = collection.findOne();
		assertNotNull(dbObject.get("_id"));
		BasicDBList dbList = (BasicDBList) dbObject.get("list");
		assertEquals(2, list.size());
		assertEquals("S1", ((DBObject) dbList.get(0)).get("string"));
		assertEquals("S2", ((DBObject) dbList.get(1)).get("string"));
		entity = jMongo.fromDBObject(EntityWithList.class, dbObject);
		assertEquals(dbObject.get("_id"), entity.get_id());
		assertEquals(2, entity.getList().size());
		assertEquals("S1", ((EntityWithString) entity.getList().get(0)).getString());
		assertEquals("S2", ((EntityWithString) entity.getList().get(1)).getString());
	}

	@Test
	public void canSaveListOfPrimitives() throws Exception {
		List<String> list = new ArrayList<String>();
		list.add("S1");
		list.add("S2");
		EntityWithListOfString entity = new EntityWithListOfString(list);
		DBObject dbObject = jMongo.toDBObject(entity);
		DBCollection collection = db.getCollection("entity");
		collection.save(dbObject);
		dbObject = collection.findOne();
		assertNotNull(dbObject.get("_id"));
		BasicDBList dbList = (BasicDBList) dbObject.get("list");
		assertEquals(2, list.size());
		assertEquals("S1", dbList.get(0));
		assertEquals("S2", dbList.get(1));
		entity = jMongo.fromDBObject(EntityWithListOfString.class, dbObject);
		assertEquals(dbObject.get("_id"), entity.get_id());
		assertEquals(2, entity.getList().size());
		assertEquals("S1", entity.getList().get(0));
		assertEquals("S2", entity.getList().get(1));
	}

	@Test
	public void canSaveEmbeddedDocuments() throws Exception {
		EntityWithString embedded = new EntityWithString("string");
		Date date = new Date();
		EntityWithEmbeddedDoc entity = new EntityWithEmbeddedDoc(date, embedded);
		DBObject dbObject = jMongo.toDBObject(entity);
		DBCollection collection = db.getCollection("entity");
		collection.save(dbObject);
		dbObject = collection.findOne();
		assertNotNull(dbObject.get("_id"));
		assertNotNull(dbObject.get("date"));
		assertEquals("string", ((DBObject) dbObject.get("entity")).get("string"));
		entity = jMongo.fromDBObject(EntityWithEmbeddedDoc.class, dbObject);
		assertEquals(dbObject.get("_id"), entity.get_id());
		assertEquals("string", entity.getEntity().getString());
	}

	@Test
	public void canSaveSetOfDocuments() throws Exception {
		Set<EntityWithString> set = new HashSet<EntityWithString>();
		EntityWithString obj1 = new EntityWithString("S1");
		set.add(obj1);
		EntityWithString obj2 = new EntityWithString("S2");
		set.add(obj2);
		EntityWithSetOfGenerics entity = new EntityWithSetOfGenerics(set);
		DBObject dbObject = jMongo.toDBObject(entity);
		DBCollection collection = db.getCollection("entity");
		collection.save(dbObject);
		dbObject = collection.findOne();
		assertNotNull(dbObject.get("_id"));
		BasicDBList dbList = (BasicDBList) dbObject.get("set");
		assertEquals(2, set.size());
		assertEquals("S1", ((DBObject) dbList.get(0)).get("string"));
		assertEquals("S2", ((DBObject) dbList.get(1)).get("string"));
		entity = jMongo.fromDBObject(EntityWithSetOfGenerics.class, dbObject);
		assertEquals(dbObject.get("_id"), entity.get_id());
		assertEquals(2, entity.getSet().size());
		assertTrue(entity.getSet().contains(obj1));
		assertTrue(entity.getSet().contains(obj2));
	}

	@Test
	public void canSaveInheritance() throws Exception {
		ParentObject entity = new ChildObject("A", "B");
		DBObject dbObject = jMongo.toDBObject(entity);
		DBCollection collection = db.getCollection("entity");
		collection.save(dbObject);
		dbObject = collection.findOne();
		assertNotNull(dbObject.get("_id"));
		assertEquals("A", dbObject.get("a"));
		assertEquals("B", dbObject.get("b"));
		ChildObject savedEntity = jMongo.fromDBObject(ChildObject.class, dbObject);
		assertEquals(dbObject.get("_id"), savedEntity.getId());
		assertEquals("A", savedEntity.getA());
		assertEquals("B", savedEntity.getB());
	}

	@Test
	public void canSaveMap() throws Exception {
		Map<String, EntityWithString> map = new HashMap<String, EntityWithString>();
		EntityWithString obj1 = new EntityWithString("S1");
		map.put("obj1", obj1);
		EntityWithString obj2 = new EntityWithString("S2");
		map.put("obj2", obj2);
		EntityWithMap entity = new EntityWithMap(map);
		DBObject dbObject = jMongo.toDBObject(entity);
		DBCollection collection = db.getCollection("entity");
		collection.save(dbObject);
		dbObject = collection.findOne();
		assertNotNull(dbObject.get("_id"));
		DBObject dbMap = (DBObject) dbObject.get("map");
		assertEquals(2, map.size());
		assertEquals("S1", ((DBObject) dbMap.get("obj1")).get("string"));
		assertEquals("S2", ((DBObject) dbMap.get("obj2")).get("string"));
		entity = jMongo.fromDBObject(EntityWithMap.class, dbObject);
		assertEquals(dbObject.get("_id"), entity.get_id());
		assertEquals(2, entity.getMap().size());
		assertEquals(obj1, entity.getMap().get("obj1"));
		assertEquals(obj2, entity.getMap().get("obj2"));
	}

	@Test
	public void shouldIgnoreMissingDBObjectProperties() throws Exception {
		DBCollection collection = db.getCollection("entity");
		DBObject dbObject = new BasicDBObject("random", "random");
		collection.save(dbObject);
		dbObject = collection.findOne();
		EntityWithString entity = jMongo.fromDBObject(EntityWithString.class, dbObject);
		assertEquals(entity.get_id(), dbObject.get("_id"));
		assertNull(entity.getString());
	}

	@Test
	public void shouldIgnoreMissingObjectIdProperties() throws Exception {
		DBCollection collection = db.getCollection("entity");
		DBObject dbObject = new BasicDBObject("random", "random");
		collection.save(dbObject);
		dbObject = collection.findOne();
		EntityWithObjectId entity = jMongo.fromDBObject(EntityWithObjectId.class, dbObject);
		assertEquals(entity.get_id(), dbObject.get("_id"));
		assertNull(entity.getObjectId());
	}

	@Test
	public void shouldIgnoreMissingObject() throws Exception {
		DBCollection collection = db.getCollection("entity");
		DBObject dbObject = new BasicDBObject("random", "random");
		collection.save(dbObject);
		dbObject = collection.findOne();
		EntityWithEmbeddedDoc entity = jMongo.fromDBObject(EntityWithEmbeddedDoc.class, dbObject);
		assertEquals(entity.get_id(), dbObject.get("_id"));
		assertNull(entity.getEntity());
	}

	@Test
	public void shouldIgnoreIgnoredProperty() throws Exception {
		EntityWithIgnoredField entity = new EntityWithIgnoredField("test", 1.23);
		DBObject dbObject = jMongo.toDBObject(entity);
		assertEquals(entity.getString(), dbObject.get("string"));
		assertNull(dbObject.get("ignored"));

		DBCollection collection = db.getCollection("entity");
		dbObject = new BasicDBObject("string", "test");
		collection.save(dbObject);
		dbObject = collection.findOne();
		entity = jMongo.fromDBObject(EntityWithIgnoredField.class, dbObject);
		assertEquals(entity.get_id(), dbObject.get("_id"));
		assertEquals("test", entity.getString());
		assertNull(entity.getIgnored());
	}
}
