package uk.co.innoltd.jmongo.entities;

import org.bson.types.ObjectId;

public class EntityWithPrimitives {
	private ObjectId _id;
	private byte a;
	private char c;
	private short sh;
	private int number;
	private long l;
	private float f;
	private double d;
	private boolean b;

	public EntityWithPrimitives() {
	}

	public EntityWithPrimitives(byte a, char c, short sh, int number, long l, float f, double d, boolean b) {
		this.a = a;
		this.c = c;
		this.sh = sh;
		this.number = number;
		this.l = l;
		this.f = f;
		this.d = d;
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

	public char getC() {
		return c;
	}

	public void setC(char c) {
		this.c = c;
	}

	public short getSh() {
		return sh;
	}

	public void setSh(short sh) {
		this.sh = sh;
	}

	public long getL() {
		return l;
	}

	public void setL(long l) {
		this.l = l;
	}

	public double getD() {
		return d;
	}

	public void setD(double d) {
		this.d = d;
	}

}
