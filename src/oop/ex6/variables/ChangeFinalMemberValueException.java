package oop.ex6.variables;

import oop.ex6.general.IllegalCodeException;

/**
 * Arises whenever a code tries to modify a final member's value.
 */
class ChangeFinalMemberValueException extends IllegalCodeException {

	private final static String MESSAGE = "can not assign new value because variable is final.";

	public ChangeFinalMemberValueException() {
		this.meaningfulMessage = MESSAGE;
	}
	
	private static final long serialVersionUID = 1L;

}
