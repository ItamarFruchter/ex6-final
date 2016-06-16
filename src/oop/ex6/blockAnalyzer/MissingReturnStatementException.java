package oop.ex6.blockAnalyzer;

import oop.ex6.general.IllegalCodeException;

/**
 * Arises when there is no return statement at the end of a method.
 */
class MissingReturnStatementException extends IllegalCodeException {

	private static final long serialVersionUID = 1L;
	private static final String MESSAGE = "method ended withot return statement.";
	
	public MissingReturnStatementException() {
		this.meaningfulMessage = MESSAGE;
	}

}
