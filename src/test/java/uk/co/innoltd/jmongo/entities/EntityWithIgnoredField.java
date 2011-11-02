package uk.co.innoltd.jmongo.entities;

import org.bson.types.ObjectId;

import uk.co.innoltd.jmongo.annotations.MongoIgnore;

public class EntityWithIgnoredField {
	private ObjectId _id;
	private String string;
	@MongoIgnore
	private Double ignored;

	public EntityWithIgnoredField() {
	}

	public EntityWithIgnoredField(String string, double ignored) {
		this.string = string;
		this.ignored = ignored;
	}

	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}

	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}

	public Double getIgnored() {
		return ignored;
	}

	public void setIgnored(Double ignored) {
		this.ignored = ignored;
	}

}
