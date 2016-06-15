package oop.ex6.blockAnalayzer;

import oop.ex6.error.IllegalCodeException;

/**
 * Arises whenever an unknown member is accessed through assignment.
 */
public class UnknownMemberNameException extends IllegalCodeException {

	private static final long serialVersionUID = 1L;
	private static final String MESSAGE = "the variable we want to asiign does not exists";

	public UnknownMemberNameException() {
		this.meaningfulMessage = MESSAGE;
	}
}