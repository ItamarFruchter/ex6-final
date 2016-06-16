package oop.ex6.variables;

import oop.ex6.general.IllegalCodeException;

/**
 * Arises whenever an illegal modifier is given.
 */
class InvalidModifierException extends IllegalCodeException{

	private static final long serialVersionUID = 1L;
	
	private final static String MESSAGE = "The modifier given is not valid.";
	
	public InvalidModifierException() {
		this.meaningfulMessage = MESSAGE;
	}


}
