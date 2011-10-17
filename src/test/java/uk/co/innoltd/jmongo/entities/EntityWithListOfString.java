package uk.co.innoltd.jmongo.entities;

import java.util.List;

import org.bson.types.ObjectId;

public class EntityWithListOfString {
	private ObjectId _id;
	private List<String> list;

	public EntityWithListOfString() {
	}

	public EntityWithListOfString(List<String> list) {
		this.list = list;
	}

	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}

	public List<String> getList() {
		return list;
	}

}
