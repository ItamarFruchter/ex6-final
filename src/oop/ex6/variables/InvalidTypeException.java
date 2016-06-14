package oop.ex6.variables;

import oop.ex6.error.IllegalCodeException;

/**
 * Arises whenever an invalid type is entered.
 */
public class InvalidTypeException extends IllegalCodeException {

	private static final long serialVersionUID = 1L;
	private final static String MESSAGE = "The type given is not valid.";

	public InvalidTypeException() {
		this.meaningfulMessage = MESSAGE;
	}
}
