package uk.co.innoltd.jmongo.entities;

import java.util.Date;

import org.bson.types.ObjectId;

public class EntityWithEmbeddedDoc {
	private ObjectId _id;
	private Date date;
	private EntityWithString entity;

	public EntityWithEmbeddedDoc() {		
	}
	
	public EntityWithEmbeddedDoc(Date date, EntityWithString entity) {
		this.date = date;
		this.entity = entity;
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

	public EntityWithString getEntity() {
		return entity;
	}

	public void setEntity(EntityWithString entity) {
		this.entity = entity;
	}
	
	
}
