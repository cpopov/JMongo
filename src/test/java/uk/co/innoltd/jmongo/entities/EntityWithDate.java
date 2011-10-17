package uk.co.innoltd.jmongo.entities;

import java.util.Date;

import org.bson.types.ObjectId;

public class EntityWithDate {
	private ObjectId _id;
	private Date date;

	public EntityWithDate() {
	}
	
	public EntityWithDate(Date date) {
		this.date = date;
	}

	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
