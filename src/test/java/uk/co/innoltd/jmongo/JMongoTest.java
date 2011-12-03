package uk.co.innoltd.jmongo;

import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

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
}
