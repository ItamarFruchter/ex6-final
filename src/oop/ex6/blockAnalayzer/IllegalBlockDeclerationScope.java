package oop.ex6.blockAnalayzer;

import oop.ex6.error.IllegalCodeException;

/**
 * Arises whenever the code contains a deceleration of a non-method block in the
 * highest scope.
 */
public class IllegalBlockDeclerationScope extends IllegalCodeException {

	private static final String MESSAGE = "";

	public IllegalBlockDeclerationScope() {
		this.meaningfulMessage = MESSAGE;
	}
}
