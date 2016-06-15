package oop.ex6.variables;

import oop.ex6.error.IllegalCodeException;

public class ValueWithoutTypeException extends IllegalCodeException {

	private static final long serialVersionUID = 1L;

	private static final String MESSAGE = "the value that was assigned has no known type.";
	
	public ValueWithoutTypeException() {
		this.meaningfulMessage = MESSAGE;
	}
}
