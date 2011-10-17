package uk.co.innoltd.jmongo.entities;

import java.io.Serializable;

import org.bson.types.ObjectId;

public class EntityWithStatics implements Serializable {
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private static int n = 2;
	private ObjectId _id;

	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}

}
