package oop.ex6.blockAnalayzer;

import oop.ex6.general.IllegalCodeException;

/**
 * Arises whenever the code contains a deceleration of a non-method block in the
 * highest scope.
 */
class IllegalBlockDeclerationScopeException
		extends IllegalCodeException {

	private static final long serialVersionUID = 1L;
	private static final String MESSAGE = "Can't declare a non-method "
			+ "block from the highest scope.";

	/**
	 * Creates an exception of this type.
	 */
	public IllegalBlockDeclerationScopeException() {
		this.meaningfulMessage = MESSAGE;
	}
}
