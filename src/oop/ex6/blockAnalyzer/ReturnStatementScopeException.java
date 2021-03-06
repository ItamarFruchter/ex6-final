package oop.ex6.blockAnalyzer;

import oop.ex6.general.IllegalCodeException;

/**
 * Arises whenever a return statement exist outside a method.
 */
class ReturnStatementScopeException extends IllegalCodeException {
	
	private static final long serialVersionUID = 1L;
	private static final String MESSAGE = "A return statement is out of a method block.";

	/**
	 * Creates an exception of this type.
	 */
	public ReturnStatementScopeException() {
		this.meaningfulMessage = MESSAGE;
	}
}
