package uk.co.innoltd.jmongo.entities;

import java.util.List;

import org.bson.types.ObjectId;

public class EntityWithList {
	private ObjectId _id;
	private List<EntityWithString> list;

	public EntityWithList(List<EntityWithString> list) {
		this.list = list;
	}

	public EntityWithList() {
	}

	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}

	public List<EntityWithString> getList() {
		return list;
	}

}
