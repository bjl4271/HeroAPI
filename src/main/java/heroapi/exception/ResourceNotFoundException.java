package heroapi.exception;

public class ResourceNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 226236826984864806L;

	public ResourceNotFoundException() {
		super();
	}
	
	public ResourceNotFoundException(String message) {
		super(message);
	}
}
