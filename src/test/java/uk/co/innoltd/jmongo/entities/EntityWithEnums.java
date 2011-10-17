package uk.co.innoltd.jmongo.entities;

import org.bson.types.ObjectId;

public class EntityWithEnums {
	private ObjectId _id;
	private EntityType type;

	public EntityWithEnums() {
	}

	public EntityWithEnums(EntityType type) {
		this.type = type;
	}

	public enum EntityType {
		SIMPLE, COMPLEX
	}

	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}

	public EntityType getType() {
		return type;
	}

	public void setType(EntityType type) {
		this.type = type;
	}

}
