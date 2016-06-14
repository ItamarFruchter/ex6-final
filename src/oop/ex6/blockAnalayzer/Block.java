package oop.ex6.blockAnalayzer;

import java.util.LinkedList;

import oop.ex6.error.IllegalCodeException;
import oop.ex6.variables.Member;
import oop.ex6.variables.MemberFactory;
import oop.ex6.variables.NonValidValueException;

/**
 * Represents any block in S-java.
 */
public abstract class Block {

	/**
	 * All of the possible block types. Currently - only while, if and void.
	 */
	protected enum BlockType {
		IF("if", false), WHILE("while", false), VOID("void", true);

		// The string representation of this block type.
		private String stringRepresentation;

		// Indicates whether the type is a method block.
		private boolean isMethod;

		// A constructor.
		private BlockType(String stringRepresentation, boolean isMethod) {
			this.stringRepresentation = stringRepresentation;
		}

		private String getRepresentation() {
			return stringRepresentation;
		}

		/**
		 * Returns the block type fitting for the given string. Throws an
		 * exception if non exist.
		 * 
		 * @param stringRepresentation
		 *            The requested type's string representation.
		 * @return The block type fitting for this string representation, if
		 *         exists.
		 * @throws IllegalCodeException
		 */
		public static BlockType blockTypeFromString(String stringRepresentation)
				throws IllegalCodeException {
			for (BlockType blockType : BlockType.values()) {
				if (blockType.getRepresentation()
						.equals(stringRepresentation)) {
					return blockType;
				}
			}
			throw new UnknownBlockTypeException();
		}
	}

	/** The known local members this block (scope wise). */
	protected LinkedList<Member> localMembers;
	/** The known local members this block (scope wise). */
	protected Member[] HigherScopeMembers;
	/** The content of this block. */
	protected String[] content;

	/**
	 * Checks the content of the block (it's code lines).
	 */
	public abstract void checkContent(MethodBlock[] knownMethods)
			throws IllegalCodeException;

	protected void handleDecleration(String line) throws IllegalCodeException {
		try {
			Member[] newMembers = MemberFactory.createMembers(line);
			for (Member newMember : newMembers) {
				localMembers.add(newMember);
			}
		} catch (NonValidValueException e) {
			for (Member knownMember : localMembers) {
				if (knownMember.name.equals(e.name)) {
					if (knownMember.getType().equals(e.type)) {

					}
				}
			}
		}
	}
}