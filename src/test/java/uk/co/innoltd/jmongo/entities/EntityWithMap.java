package uk.co.innoltd.jmongo.entities;

import java.util.Map;

import org.bson.types.ObjectId;

public class EntityWithMap {
	private ObjectId _id;
	private Map<String, EntityWithString> map;

	public EntityWithMap(Map<String, EntityWithString> map) {
		this.map = map;
	}

	public EntityWithMap() {
	}

	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}

	public Map<String, EntityWithString> getMap() {
		return map;
	}

}
