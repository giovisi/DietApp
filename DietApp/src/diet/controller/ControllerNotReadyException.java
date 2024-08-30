package diet.controller;

public class ControllerNotReadyException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ControllerNotReadyException() {
	}

	public ControllerNotReadyException(String message) {
		super(message);
	}

	public ControllerNotReadyException(Throwable cause) {
		super(cause);
	}

	public ControllerNotReadyException(String message, Throwable cause) {
		super(message, cause);
	}

	public ControllerNotReadyException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
