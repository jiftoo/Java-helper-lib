package exceptions;

public class AlreadyRunningException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public AlreadyRunningException() {
		super("The thead is already running!");
	}
	public AlreadyRunningException(String msg) {
		super(msg);
	}
	public AlreadyRunningException(String msg, Throwable trw) {
		super(msg, trw);
	}
	public AlreadyRunningException(Throwable trw) {
		super(trw);
	}
}
