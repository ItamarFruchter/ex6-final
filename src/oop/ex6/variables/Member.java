package oop.ex6.variables;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import oop.ex6.fileprocessing.ReservedWord;
import oop.ex6.error.IllegalCodeException;

/**
 * A variable in Sjava.
 */
public class Member {
	// The pattern of a legal name.
	private static final Pattern NAME_PATTERN = Pattern
			.compile("(_\\w+|[A-Za-z])\\w*");

	// This member's type.
	private final Type type;

	/** A string representation of this member's value. */
	private boolean hasValue;

	/** This member's name. */
	private final String name;

	/** This member's modifier. */
	private final Modifier modifier;

	/**
	 * Creates a member.
	 * 
	 * @param nameString
	 *            This member's name.
	 * @param typeString
	 *            This member's type.
	 * @param valueString
	 *            This member's value.
	 * @param modifierString
	 *            A string representation of a modifier.
	 * @throws IllegalCodeException
	 * @throws NonValidValueException
	 */
	public Member(String nameString, String typeString, String valueString,
			String modifierString)
			throws IllegalCodeException, NonValidValueException {
		if (checkName(nameString)) {
			this.name = nameString.trim();
			this.type = Type.findType(typeString); // May throw invalid type
													// exception.
			Modifier modifier = Modifier.modifierFromString(modifierString);
			if (modifier != null) {
				this.modifier = modifier;
			} else {
				throw new InvalidModifierException();
			}

			if (valueString != null) {
				this.hasValue = true; // Is always initialized as true, and will
										// be ignored if it is an illegal value.
				if (!type.isValidValue(valueString)) {
					throw new NonValidValueException(type, valueString.trim());
				}
			} else {
				if (modifier.equals(Modifier.FINAL)) {
					throw new NonInitiallizedFinalMemberException();
				} else {
					this.hasValue = false;
				}
			}
		} else {
			throw new IllegalNameException();
		}
	}

	/**
	 * Creates a member with no modifier.
	 * 
	 * @param nameString
	 *            This member's name.
	 * @param typeString
	 *            This member's type.
	 * @param valueString
	 *            This member's value.
	 * @throws IllegalCodeException
	 * @throws NonValidValueException
	 */
	public Member(String nameString, String typeString, String valueString)
			throws NonValidValueException, IllegalCodeException {
		this(nameString, typeString, valueString, null);
	}

	/*
	 * Returns true if the given string is indeed a legal name.
	 */
	private boolean checkName(String name) {
		String trimmedName = name.trim();
		Matcher nameMatcher = NAME_PATTERN.matcher(trimmedName);
		return (nameMatcher.matches()
				&& !ReservedWord.isReservedWord(trimmedName));
	}

	/**
	 * Returns true iff this member has value.
	 */
	public boolean isInitiallized() {
		return hasValue;
	}

	/**
	 * Tries to set the value of a parameter.
	 * 
	 * @param newValue
	 *            The new value to try and put.
	 */
	public void setValue(String newValue) throws IllegalCodeException {
		if (modifier.equals(Modifier.FINAL)) {
			if (hasValue) {
				throw new ChangeFinalMemberValueException();
			} else {
				if (newValue != null) {
					this.hasValue = true;
					if (!type.isValidValue(newValue)) {
						throw new NonValidValueException(type, newValue.trim());
					}
				}
			}
		}
	}

	/**
	 * A getter for the type of the Member.
	 * 
	 * @return This member's type.
	 */
	public Type getType() {
		return type;
	}

	/**
	 * A getter for the name of the Member.
	 * 
	 * @return This member's name.
	 */
	public String getName() {
		return name;
	}
}