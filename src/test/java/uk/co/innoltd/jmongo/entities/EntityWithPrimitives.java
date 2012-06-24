package uk.co.innoltd.jmongo.entities;

import org.bson.types.ObjectId;

public class EntityWithPrimitives {
	private ObjectId _id;
	private byte a;
	private int number;
	private float f;
	private boolean b;

	public EntityWithPrimitives() {
	}

	public EntityWithPrimitives(byte a, int number, float f, boolean b) {
		this.a = a;
		this.number = number;
		this.f = f;
		this.b = b;
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

	public byte getA() {
		return a;
	}

	public void setA(byte a) {
		this.a = a;
	}

	public float getF() {
		return f;
	}

	public void setF(float f) {
		this.f = f;
	}

	public boolean isB() {
		return b;
	}

	public void setB(boolean b) {
		this.b = b;
	}

}
