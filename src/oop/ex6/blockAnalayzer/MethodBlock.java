package oop.ex6.blockAnalayzer;

import java.util.regex.Pattern;

import oop.ex6.error.IllegalCodeException;
import oop.ex6.variables.Member;
import oop.ex6.variables.Type;

public class MethodBlock extends Block {

	private static final Pattern NAME_PATTERN = Pattern.compile("[a-zA-Z]\\w*");

	/*
	private class Argument {

		private Type typeOfArgument;
		private String nameOfArgument;

		private Argument(String type, String name) throws IllegalCodeException {
			this.typeOfArgument = Type.findType(type);
			this.nameOfArgument = checkName(name);
		}
	}
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
	
	public MethodBlock(String type, String name, String arguments, String[] content, Member[] higherScopeMembers) {
		// TODO Auto-generated constructor stub
	}
}
