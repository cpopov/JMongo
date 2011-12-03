package uk.co.innoltd.jmongo.entities;

import org.bson.types.ObjectId;

public class EntityWithBoolean {
	private ObjectId _id;
	private Boolean bool;

	public EntityWithBoolean() {
	}

	public EntityWithBoolean(boolean bool) {
		this.bool = bool;
	}

	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}

	public Boolean getBool() {
		return bool;
	}

	public void setBool(Boolean bool) {
		this.bool = bool;
	}

}
