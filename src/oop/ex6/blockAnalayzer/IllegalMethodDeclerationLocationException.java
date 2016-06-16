package oop.ex6.blockAnalayzer;

import oop.ex6.general.IllegalCodeException;

/**
 * Arises whenever a method is declared not from the most global scope.
 */
class IllegalMethodDeclerationLocationException
		extends IllegalCodeException {
	
	private static final long serialVersionUID = 1L;
	private static final String MESSAGE = "Can't create a method not from the highest scope.";

	/**
	 * Creates an exception of this type.
	 */
	public IllegalMethodDeclerationLocationException() {
		this.meaningfulMessage = MESSAGE;
	}
}
