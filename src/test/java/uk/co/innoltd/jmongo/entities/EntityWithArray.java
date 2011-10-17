package uk.co.innoltd.jmongo.entities;

import org.bson.types.ObjectId;

public class EntityWithArray {
	private ObjectId _id;
	private EntityWithString[] array;

	public EntityWithArray() {
	}
	
	public EntityWithArray(EntityWithString[] array) {
		this.array = array;
	}

	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}

	public EntityWithString[] getArray() {
		return array;
	}

}
