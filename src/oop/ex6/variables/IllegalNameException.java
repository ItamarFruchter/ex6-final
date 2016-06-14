package oop.ex6.variables;

import oop.ex6.error.IllegalCodeException;

/**
 * Arises whenever a illegal name is given.
 */
public class IllegalNameException extends IllegalCodeException {

	private static final long serialVersionUID = 1L;
	
	private final static String MESSAGE = "The name of the variable is not valid.";

	public IllegalNameException() {
		this.meaningfulMessage = MESSAGE;
	}

}
