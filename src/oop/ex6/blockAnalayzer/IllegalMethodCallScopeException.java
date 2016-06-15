package oop.ex6.blockAnalayzer;

import oop.ex6.error.IllegalCodeException;

/**
 * Arises whenever a method is called from the highest scope.
 */
public class IllegalMethodCallScopeException extends IllegalCodeException {

	private static final long serialVersionUID = 1L;
	private static final String MESSAGE = "Can't call methods from the "
			+ "highest scope.";

	public IllegalMethodCallScopeException() {
		this.meaningfulMessage = MESSAGE;
	}
}
