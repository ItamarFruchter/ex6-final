package oop.ex6.blockAnalayzer;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import oop.ex6.error.IllegalCodeException;
import oop.ex6.variables.Member;
import oop.ex6.variables.MemberFactory;
import oop.ex6.variables.Type;

/**
 * this class represents a method block
 */
public class MethodBlock extends Block {

	/*
	 * private class Argument {
	 * 
	 * private Type typeOfArgument; private String nameOfArgument;
	 * 
	 * private Argument(String type, String name) throws IllegalCodeException {
	 * this.typeOfArgument = Type.findType(type); this.nameOfArgument =
	 * checkName(name); } }
	 */

	private enum MethodBlockType {
		VOID("void");

		// The string representation of this method type.
		private String stringRepresentation;

		// A constructor.
		private MethodBlockType(String stringRepresentation) {
			this.stringRepresentation = stringRepresentation;
		}

		private String getRepresentation() {
			return stringRepresentation;
		}

		/**
		 * Returns the non-method block type fitting for the given string.
		 * Throws an exception if non exist.
		 * 
		 * @param stringRepresentation
		 *            The requested type's string representation.
		 * @return The non-method block type fitting for this string
		 *         representation, if exists.
		 * @throws IllegalCodeException
		 */
		public static MethodBlockType methodTypeFromString(String stringRepresentation)
				throws IllegalCodeException {
			for (MethodBlockType nonMethodType : MethodBlockType.values()) {
				if (nonMethodType.getRepresentation().equals(stringRepresentation)) {
					return nonMethodType;
				}
			}
			throw new UnknownBlockTypeException();
		}
	}

	private static final Pattern NAME_PATTERN = Pattern.compile("[a-zA-Z]\\w*");

	private String name;

	public MethodBlock(String type, String name, String arguments, String[] content,
			Member[] higherScopeMembers) throws IllegalCodeException {
		if (checkName(name)) {
			this.name = name;
			String[] argumentsArray = arguments.split(",");
			for (String argument : argumentsArray) {
				localMembers.addAll(MemberFactory.createMembers(argument));
			}
			this.content = content;
			
		}
	}

	private boolean isReservedWord(String name) {
		for (String reserved : RESERVED_WORDS) {
			if (reserved.equals(name)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean checkName(String name) {
		Matcher nameMatcer = NAME_PATTERN.matcher(name.trim());
		return (nameMatcer.matches() && !isReservedWord(name));
	}

	@Override
	public void checkContent(MethodBlock[] knownMethods) throws IllegalCodeException {
		// TODO Auto-generated method stub
		
	}
}
