package uk.co.innoltd.jmongo;

public class JMongoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public JMongoException() {
	}

	public JMongoException(String msg) {
		super(msg);
	}

	public JMongoException(Throwable t) {
		super(t);
	}

	public JMongoException(String msg, Throwable t) {
		super(msg, t);
	}

}
