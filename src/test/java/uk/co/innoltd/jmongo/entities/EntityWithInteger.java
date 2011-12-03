package uk.co.innoltd.jmongo.entities;

import org.bson.types.ObjectId;

public class EntityWithInteger {
	private ObjectId _id;
	private Integer number;

	public EntityWithInteger() {
	}
	
	public EntityWithInteger(int number) {
		this.number = number;
	}

	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

}
