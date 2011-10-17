package uk.co.innoltd.jmongo.entities;

import org.bson.types.ObjectId;

public class EntityWithArrayOfPrimitives {
	private ObjectId _id;
	private long[] array;

	public EntityWithArrayOfPrimitives() {
	}

	public EntityWithArrayOfPrimitives(long[] array) {
		this.array = array;
	}

	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}

	public long[] getArray() {
		return array;
	}

}
