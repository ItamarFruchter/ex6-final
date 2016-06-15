package oop.ex6.blockAnalayzer;

import oop.ex6.error.IllegalCodeException;

/**
 * Arises whenever a invalid name is given to a method.
 */
public class InvalidMethodName extends IllegalCodeException {

	private static final long serialVersionUID = 1L;
	private static final String MESSAGE = "the name method is not valid.";
	
	public InvalidMethodName() {
		this.meaningfulMessage = MESSAGE;
	}
}
