package uk.co.innoltd.jmongo.perftest;


import uk.co.innoltd.jmongo.JMongo;
import uk.co.innoltd.jmongo.entities.EntityWithString;

import com.mongodb.DBObject;

public class CachedJMongoThread extends SamplerThread {
	private JMongo jMongo;

	public CachedJMongoThread(JMongo jMongo, int count) {
		super(count);
		this.jMongo = jMongo;
	}

	@Override
	public void doIt() throws Exception {
		EntityWithString entity = new EntityWithString("hello world");
		DBObject dbObject = jMongo.toDBObject(entity);
		jMongo.fromDBObject(EntityWithString.class, dbObject);
	}

}
