package oop.ex6.variables;

import oop.ex6.general.IllegalCodeException;

/**
 * Arises whenever we try to initiate a member that already exists in the local scope.
 */
class MemberAlreadyExistsException extends IllegalCodeException {

	private static final long serialVersionUID = 1L;
	private final static String MESSAGE = "This variable's name already exists in relevant scopes";

	public MemberAlreadyExistsException() {
		this.meaningfulMessage = MESSAGE;
	}
}
