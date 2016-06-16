package oop.ex6.variables;

import oop.ex6.general.IllegalCodeException;

/**
 * Arises whenever a final member is created without a value.
 */
class NonInitiallizedFinalMemberException extends IllegalCodeException {
	private static final long serialVersionUID = 1L;
	private static final String MESSAGE = "Can't create a final member with no value.";

	public NonInitiallizedFinalMemberException() {
		this.meaningfulMessage = MESSAGE;
	}
}
