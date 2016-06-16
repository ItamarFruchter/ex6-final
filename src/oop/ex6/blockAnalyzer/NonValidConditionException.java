package oop.ex6.blockAnalyzer;

import oop.ex6.general.IllegalCodeException;

/**
 * Arises whenever a non valid condition is given to a non method block (if,
 * while).
 */
class NonValidConditionException extends IllegalCodeException {

	private static final long serialVersionUID = 1L;
	private final static String MESSAGE = "The condition given is not valid.";
	
	/**
	 * Creates an exception of this type.
	 */
	public NonValidConditionException() {
		this.meaningfulMessage = MESSAGE;
	}
}
