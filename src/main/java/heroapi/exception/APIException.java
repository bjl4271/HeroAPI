package heroapi.exception;

public class APIException extends Exception {
	private static final long serialVersionUID = -2306471104248668257L;
	
	public APIException() {
		super();
	}
	
	public APIException(String message) {
		super(message);
	}
}
