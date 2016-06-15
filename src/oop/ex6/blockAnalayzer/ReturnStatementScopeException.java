package oop.ex6.blockAnalayzer;

import oop.ex6.error.IllegalCodeException;

/**
 * Arises whenever a return statement exist outside a method.
 */
public class ReturnStatementScopeException extends IllegalCodeException {
	
	private static final String MESSAGE = "A return statement is out of a method block.";

	public ReturnStatementScopeException() {
		this.meaningfulMessage = MESSAGE;
	}
}
