package oop.ex6.blockAnalayzer;

import oop.ex6.general.IllegalCodeException;

public class MissingReturnStatementException extends IllegalCodeException {

	private static final long serialVersionUID = 1L;
	private static final String MESSAGE = "method ended withot return statement.";
	
	public MissingReturnStatementException() {
		this.meaningfulMessage = MESSAGE;
	}

}
