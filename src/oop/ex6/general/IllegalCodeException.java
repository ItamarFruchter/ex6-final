package oop.ex6.general;

/**
 * Represents any exception which should result in the S-java code not working.
 */
public class IllegalCodeException extends Exception {
	private static final long serialVersionUID = 1L;

	/** The line number in which the exception was created. */
	protected int lineNumber;
	protected String meaningfulMessage;
	private static final String LINE_NUMBER_MESSAGE = " error in line: ";

	public String getMessage() {
		return meaningfulMessage;
	}

	public IllegalCodeException() {
		
	}

	public IllegalCodeException(IllegalCodeException error, int lineNumber) {
		this.meaningfulMessage = error.getMessage() + LINE_NUMBER_MESSAGE
				+ lineNumber;
	}
}
