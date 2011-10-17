package uk.co.innoltd.jmongo.entities;

import java.util.Set;

import org.bson.types.ObjectId;

public class EntityWithSetOfGenerics {
	private ObjectId _id;
	private Set<? extends EntityWithString> set;

	public EntityWithSetOfGenerics(Set<? extends EntityWithString> set) {
		this.set = set;
	}

	public EntityWithSetOfGenerics() {
	}

	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}

	public Set<? extends EntityWithString> getSet() {
		return set;
	}
}
