package oop.ex6.blockAnalyzer;

import oop.ex6.general.IllegalCodeException;

/**
 * Arises whenever an unknown member is accessed through assignment.
 */
class UnknownMemberNameException extends IllegalCodeException {

	private static final long serialVersionUID = 1L;
	private static final String MESSAGE = "The variable we want to assign does not exists.";

	/**
	 * Creates an exception of this type.
	 */
	public UnknownMemberNameException() {
		this.meaningfulMessage = MESSAGE;
	}
}
