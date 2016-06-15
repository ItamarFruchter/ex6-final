package oop.ex6.blockAnalayzer;

import oop.ex6.error.IllegalCodeException;

/**
 * Arises whenever a method is declared not from the most global scope.
 */
public class IllegalMethodDeclerationLocationException
		extends IllegalCodeException {
	
	private static final long serialVersionUID = 1L;
	private static final String MESSAGE = "Can't create a method not from the highest scope.";

	public IllegalMethodDeclerationLocationException() {
		this.meaningfulMessage = MESSAGE;
	}
}
