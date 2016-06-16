package oop.ex6.blockAnalyzer;

import oop.ex6.general.IllegalCodeException;

/**
 * Arises whenever the code contains an unclosed block (not enough '}').
 */
class UnclosedBlockException extends IllegalCodeException {
	private static final long serialVersionUID = 1L;
	private static final String MESSAGE = "There is an unclosed block in your code.";
	
	/**
	 * Creates an exception of this type.
	 */
	public UnclosedBlockException() {
		this.meaningfulMessage = MESSAGE;
	}
}
