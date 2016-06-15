package oop.ex6.variables;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import oop.ex6.error.IllegalCodeException;

/**
 * Every type is represented by an Enum in this class. Each holds a method to
 * check whether a given string represents a legal value for the said type.
 */
public enum Type {
	STRING("String", "\\s*\".*\"\\s*", "\"\""), CHAR("char", "\\s*\'.\'\\s*",
			"'A'"), INT("int", "\\s*-?\\d+\\s*", "0"), DOUBLE("double",
					"\\s*-?\\d+(\\.\\d+)?\\s*", "0"), BOOLEAN("boolean",
							"\\s*((-?\\d+(\\.\\d+)?)|true|false)\\s*", "true");

	private String stringRepresentation;
	private Pattern valuePattern;
	private String defaultValue;

	private Type(String stringRepresentation, String valueRegex,
			String defaultValue) {
		this.stringRepresentation = stringRepresentation;
		this.valuePattern = Pattern.compile(valueRegex);
		this.defaultValue = defaultValue;
	}

	/**
	 * Returns a default value of this type.
	 * 
	 * @return The defined default value.
	 */
	public String getDefaultValue() {
		return defaultValue;
	}

	/**
	 * A getter method for this type's string representation.
	 * 
	 * @return This type's string representation.
	 */
	public String getStringRepresentation() {
		return stringRepresentation;
	}

	/**
	 * Checks if a given value fits the variable specifications.
	 * 
	 * @param value
	 *            String representation of the desired value.
	 * @return true iff the value given fit's the type.
	 */
	public boolean isValidValue(String value) {
		Matcher valueMatcher = valuePattern.matcher(value);
		return valueMatcher.matches();
	}

	/**
	 * Returns the type object fitting for this string.
	 * 
	 * @param typeString
	 *            The string to check.
	 * @return The type fitting for the string given. null if no type was
	 *         identified.
	 * @throws InvalidTypeException
	 */
	public static Type findType(String typeString) throws InvalidTypeException {
		for (Type type : Type.values()) {
			if (type.stringRepresentation.equals(typeString)) {
				return type;
			}
		}
		throw new InvalidTypeException(); // If non were found.
	}

	public static Type typeOfValue(String value) throws IllegalCodeException {
		for (Type type : Type.values()) {
			if (type.isValidValue(value)) {
				return type;
			}
		}
		throw new ValueWithoutTypeException();
	}

	/**
	 * @param neededType
	 *            the type we want to assign value to
	 * @param givenType
	 *            the type of the value we want to assign
	 * @return true if we can assign the value, false otherwise
	 */
	public static boolean canBeCasted(Type neededType, Type givenType) {
		boolean matched = false;
		switch (neededType) {
		case DOUBLE:
			if (givenType.equals(DOUBLE) || givenType.equals(INT)) {
				matched = true;
			}
			break;
		case BOOLEAN:
			if (givenType.equals(BOOLEAN) || givenType.equals(INT)
					|| givenType.equals(DOUBLE)) {
				matched = true;
			}
			break;
		case CHAR:
			if (givenType.equals(CHAR)) {
				matched = true;
			}
			break;
		case INT:
			if (givenType.equals(INT)) {
				matched = true;
			}
			break;
		case STRING:
			if (givenType.equals(STRING)) {
				matched = true;
			}
		default:
			break;
		}
		return matched;
	}
}