package oop.ex6.blockAnalyzer;

import oop.ex6.general.IllegalCodeException;

/**
 * Arises whenever a invalid name is given to a method.
 */
class InvalidMethodNameException extends IllegalCodeException {

	private static final long serialVersionUID = 1L;
	private static final String MESSAGE = "the name method is not valid.";
	
	/**
	 * Creates an exception of this type.
	 */
	public InvalidMethodNameException() {
		this.meaningfulMessage = MESSAGE;
	}
}
