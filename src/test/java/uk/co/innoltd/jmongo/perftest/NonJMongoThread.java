package uk.co.innoltd.jmongo.perftest;

import org.bson.types.ObjectId;

import uk.co.innoltd.jmongo.entities.EntityWithString;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class NonJMongoThread extends SamplerThread {
	Object obj;

	public NonJMongoThread(int count) {
		super(count);
	}

	@Override
	public void doIt() throws Exception {
		EntityWithString entity = new EntityWithString("hello world");
		DBObject dbObject = new BasicDBObject();
		dbObject.put("_id", entity.get_id());
		dbObject.put("string", entity.getString());

		obj = (ObjectId) dbObject.get("_id");
		obj = (String) dbObject.get("string");
	}
}
