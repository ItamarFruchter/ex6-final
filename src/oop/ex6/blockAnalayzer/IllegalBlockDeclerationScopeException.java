package oop.ex6.blockAnalayzer;

import oop.ex6.error.IllegalCodeException;

/**
 * Arises whenever the code contains a deceleration of a non-method block in the
 * highest scope.
 */
public class IllegalBlockDeclerationScopeException
		extends IllegalCodeException {

	private static final long serialVersionUID = 1L;
	private static final String MESSAGE = "Can't declare a non-method "
			+ "block from the highest scope.";

	public IllegalBlockDeclerationScopeException() {
		this.meaningfulMessage = MESSAGE;
	}
}
