package oop.ex6.variables;

import oop.ex6.general.IllegalCodeException;

public class MemberAlreadyExistsException extends IllegalCodeException {

	private static final long serialVersionUID = 1L;
	private final static String MESSAGE = "This variable's name already exists in relevant scopes";

	public MemberAlreadyExistsException() {
		this.meaningfulMessage = MESSAGE;
	}
}
