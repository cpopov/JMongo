package uk.co.innoltd.jmongo.entities;

import org.bson.types.ObjectId;

public class EntityWithInt {
	private ObjectId _id;
	private int number;

	public EntityWithInt() {
	}
	
	public EntityWithInt(int number) {
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
