package uk.co.innoltd.jmongo.perftest;


import uk.co.innoltd.jmongo.JMongo;
import uk.co.innoltd.jmongo.entities.EntityWithString;

import com.mongodb.DBObject;

public class UnCachedJMongoThread extends SamplerThread {

	public UnCachedJMongoThread(int count) {
		super(count);
	}

	@Override
	public void doIt() throws Exception {
		JMongo jMongo = new JMongo();
		EntityWithString entity = new EntityWithString("hello world");
		DBObject dbObject = jMongo.toDBObject(entity);
		jMongo.fromDBObject(EntityWithString.class, dbObject);
	}
}
