package oop.ex6.blockAnalayzer;

import java.util.LinkedList;

import oop.ex6.error.IllegalCodeException;
import oop.ex6.fileprocessing.LineType;
import oop.ex6.variables.Member;
import oop.ex6.variables.MemberFactory;
import oop.ex6.variables.NonValidValueException;
import oop.ex6.variables.Type;

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

		/** Indicates whether the type represents a method block. */
		public boolean isMethod;

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

	// Constants meant to help with handling assignment.
	private static final String ASSIGNMENT_SEPERATOR = "=";
	private static final int MEMBER_INDEX = 0, VALUE_INDEX = 1;

	// this block's type.
	protected BlockType type;
	/** The known local members this block (scope wise). */
	protected LinkedList<Member> localMembers;
	/** The known local members this block (scope wise). */
	protected LinkedList<Member> higherScopeMembers;
	/** The content of this block. */
	protected String[] content;
	/** The contained blocks within this block. */
	protected LinkedList<Block> containedBlocks;

	/**
	 * Initializes all the members in this block's scope.
	 * 
	 * @throws IllegalCodeException
	 */
	public void initializesAllMembers() throws IllegalCodeException {
		int ScopeCounter = 0;
		for (String line : content) {
			LineType currentLineType = LineType.fitType(line);
			switch (currentLineType) {
			case DECLERATION:
				handleDecleration(line);
				break;

			case ASSIGNMENT:
				handleAssignment(line);
				break;

			case NON_METHOD_BLOCK:
				ScopeCounter++;
				break;
				CONTINUE FROM HERE
			// Consider noting the index of the lines in which the blocks start
			// and end.
			case METHOD_DECLERATION:
				ScopeCounter++;
				break;

			default:
				break;
			}
		}
	}

	/**
	 * Checks the content of the block (it's code lines).
	 */
	public void checkContent(MethodBlock[] knownMethods)
			throws IllegalCodeException {
		for (String line : content) {
			LineType currentLineType = LineType.fitType(line);
			switch (currentLineType) {
			case DECLERATION:
				handleDecleration(line);
				break;

			case ASSIGNMENT:
				handleAssignment(line);
				break;

			case NON_METHOD_BLOCK:
				break;

			case METHOD_DECLERATION:
				break;

			case METHOD_CALLING:
				break;

			case COMMENT_LINE:
				break;

			case CLOSING_BLOCK:
				break;

			case RETURN_STATEMENT:
				break;

			default:
				break;
			}
		}
	}

	/**
	 * Checks whether a given string is a name of a member (local or global
	 * relatively to this block's scope.
	 * 
	 * @param nameToCheck
	 *            The name to check.
	 * @return The member found if one was found, none if none was found.
	 */
	protected Member isKnownMember(String nameToCheck) {
		for (Member knownMember : localMembers) {
			if (knownMember.name.equals(nameToCheck)) {
				return knownMember;
			}
		}

		for (Member knownGlobalMember : higherScopeMembers) {
			if (knownGlobalMember.name.equals(nameToCheck)) {
				return knownGlobalMember;
			}
		}

		return null;
	}

	/**
	 * Handles deceleration of a parameter in this block.
	 * 
	 * @param line
	 *            The line to check.
	 * @throws IllegalCodeException
	 */
	protected void handleDecleration(String line) throws IllegalCodeException {
		/*
		 * LinkedList<Member> newMembers = MemberFactory.createMembers(line,
		 * higherScopeMembers); for (Member newMember : newMembers) {
		 * localMembers.add(newMember); }
		 */
	}

	/**
	 * Handles an assignment line in the block.
	 * 
	 * @param line
	 *            The assignment line to validate.
	 * @throws IllegalCodeException
	 */
	protected void handleAssignment(String line) throws IllegalCodeException {
		String[] lineArguments = line.split(ASSIGNMENT_SEPERATOR);
		String memberName = lineArguments[MEMBER_INDEX];
		String valueString = lineArguments[VALUE_INDEX];

		Member memberFound = isKnownMember(memberName);

		if (memberFound == null) {
			throw new UnknownMemberName();
		}

		try {
			memberFound.setValue(valueString);
		} catch (NonValidValueException e) {
			Member knownMember = isKnownMember(e.name);
			if (knownMember != null) {
				if (knownMember.hasValue
						&& Type.canBeCasted(e.type, knownMember.getType())) {
					memberFound
							.setValue(knownMember.getType().getDefaultValue());
				}
			} else {
				throw e;
			}
		}
	}

	/**
	 * Handles a creation of an inner block.
	 * 
	 * @param line
	 *            The decleration line for this block.
	 */
	protected void handleNonMethodBlockDecleration(String line) {

	}
}