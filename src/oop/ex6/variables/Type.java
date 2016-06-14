package oop.ex6.variables;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Every type is represented by an Enum in this class. Each holds a method to
 * check whether a given string represents a legal value for the said type.
 */
public enum Type {
	STRING("\"\"") {
		@Override
		public boolean isValidValue(String value) {
			Matcher STRING_MATCHER = STRING_PATTERN.matcher(value);
			if (!STRING_MATCHER.matches()) {
				return false;
			} else {
				return true;
			}
		}
	},
	CHAR("'A'") {
		@Override
		public boolean isValidValue(String value) {
			Matcher CHAR_MATCHER = CHAR_PATTERN.matcher(value);
			if (!CHAR_MATCHER.matches()) {
				return false;
			} else {
				return true;
			}
		}
	},
	INT("0") {
		@Override
		public boolean isValidValue(String value) {
			Matcher INT_MATCHER = INT_PATTERN.matcher(value);
			if (!INT_MATCHER.matches()) {
				return false;
			} else {
				return true;
			}
		}
	},
	DOUBLE("0") {
		@Override
		public boolean isValidValue(String value) {
			Matcher DOUBLE_MATCHER = DOUBLE_PATTERN.matcher(value);
			if (!DOUBLE_MATCHER.matches()) {
				return false;
			} else {
				return true;
			}
		}
	},
	BOOLEAN("true") {
		@Override
		public boolean isValidValue(String value) {
			Matcher BOOLEAN_MATCHER = BOOLEAN_PATTERN.matcher(value);
			if (!BOOLEAN_MATCHER.matches()) {
				return false;
			} else {
				return true;
			}
		}
	};

	private String defaultValue;

	private Type(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	// The declaration representation of the types.
	private static final String STRING_STRING = "String", CHAR_STRING = "char",
			INT_STRING = "int", DOUBLE_STRING = "double",
			BOOLEAN_STRING = "boolean";

	// The regular expression patterns for each variable.
	private static final String STRING_REGEX = "\\s*\".*\"\\s*",
			CHAR_REGEX = "\\s*\'.\'\\s*", INT_REGEX = "\\s*-?\\d+\\s*",
			DOUBLE_REGEX = "\\s*-?\\d+(.\\d+)?\\s*",
			BOOLEAN_REGEX = "\\s*((-?\\d+(.\\d+)?)|true|false)\\s*";

	// The patterns.
	private static final Pattern STRING_PATTERN = Pattern.compile(STRING_REGEX),
			CHAR_PATTERN = Pattern.compile(CHAR_REGEX),
			INT_PATTERN = Pattern.compile(INT_REGEX),
			DOUBLE_PATTERN = Pattern.compile(DOUBLE_REGEX),
			BOOLEAN_PATTERN = Pattern.compile(BOOLEAN_REGEX);

	/**
	 * Checks if a given value fits the variable specifications.
	 * 
	 * @param value
	 *            String representation of the desired value.
	 * @return true iff the value given fit's the type.
	 */
	public abstract boolean isValidValue(String value);

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
		switch (typeString) {
		case STRING_STRING:
			return STRING;
		case CHAR_STRING:
			return CHAR;
		case INT_STRING:
			return INT;
		case DOUBLE_STRING:
			return DOUBLE;
		case BOOLEAN_STRING:
			return BOOLEAN;
		default:
			throw new InvalidTypeException();
		}
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