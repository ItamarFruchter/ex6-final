package oop.ex6.blockAnalayzer;

import oop.ex6.general.IllegalCodeException;

/**
 * Arises whenever an unknown block is declared.
 */
class UnknownBlockTypeException extends IllegalCodeException {

	private static final long serialVersionUID = 1L;
	private static final String MESSAGE = "there is no such block on S-java.";

	/**
	 * Creates an exception of this type.
	 */
	public UnknownBlockTypeException() {
		this.meaningfulMessage = MESSAGE;
	}
}
