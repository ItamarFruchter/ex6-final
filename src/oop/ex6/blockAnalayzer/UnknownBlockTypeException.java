package oop.ex6.blockAnalayzer;

import oop.ex6.error.IllegalCodeException;

public class UnknownBlockTypeException extends IllegalCodeException {

	private static final long serialVersionUID = 1L;
	private static final String MESSAGE = "there is no such block on S-java.";

	public UnknownBlockTypeException() {
		this.meaningfulMessage = MESSAGE;
	}
}
