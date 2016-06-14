package oop.ex6.blockAnalayzer;

import oop.ex6.error.IllegalCodeException;

public class MethodBlock extends Block {

	
	
	private enum MethodBlockType {
		Void("void");

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
		public static MethodBlockType methodTypeFromString(
				String stringRepresentation) throws IllegalCodeException {
			for (MethodBlockType nonMethodType : MethodBlockType.values()) {
				if (nonMethodType.getRepresentation().equals(stringRepresentation)) {
					return nonMethodType;
				}
			}
			throw new UnknownBlockTypeException();
		}
	}
	
	@Override
	protected boolean checkCondition(String condition) {
		// TODO Auto-generated method stub
		return false;
	}

}
