package oop.ex6.variables;

import oop.ex6.general.IllegalCodeException;

class ValueWithoutTypeException extends IllegalCodeException {

	private static final long serialVersionUID = 1L;

	private static final String MESSAGE = "The value that was assigned has no known type.";
	
	public ValueWithoutTypeException() {
		this.meaningfulMessage = MESSAGE;
	}
}
