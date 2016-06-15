package oop.ex6.blockAnalayzer;

import oop.ex6.error.IllegalCodeException;

/**
 * Arises whenever the number of block closing brackets (}) is greater than the
 * number of block opening brackets ({).
 */
public class NonValidBlockClosingException extends IllegalCodeException {
	private static final long serialVersionUID = 1L;
	private static final String MESSAGE = "Closing bracket dose not close any block.";

	public NonValidBlockClosingException() {
		this.meaningfulMessage = MESSAGE;
	}
}