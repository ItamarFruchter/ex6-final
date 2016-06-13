package oop.ex6.variables;

import oop.ex6.error.IllegalCodeException;

/**
 * Occurs when the value given to a parameter was not legal. It might be another
 * variable's name, so this exception holds any needed information to check
 * this. Not necessarily an illegal code exception!
 */
public class NonValidValueException extends IllegalCodeException {
	
	private static final long serialVersionUID = 1L;
	/** The variable type to check. */
	public Type type;
	/** The name to check. */
	public String name;
	
	public NonValidValueException(Type type, String name) {
		this.type = type;
		this.name = name;
	}
}
