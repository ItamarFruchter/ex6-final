package oop.ex6.blockAnalayzer;

import oop.ex6.error.IllegalCodeException;

/**
 * Arises whenever the code contains an unclosed block (not enough '}').s
 */
public class UnclosedBlockException extends IllegalCodeException {
	private static final String MESSAGE = "There is an unclosed block in your code";
	
	public UnclosedBlockException() {
		this.meaningfulMessage = MESSAGE;
	}
}
