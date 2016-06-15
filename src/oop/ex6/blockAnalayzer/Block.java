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
		IF("if", false), WHILE("while", false), VOID("void", true), MAIN(null,
				false);

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
	 * Initializes all the members in this block's scope, checks validity of the
	 * creation of inner blocks (but dose not create them), checks the block
	 * structure.
	 * 
	 * @throws IllegalCodeException
	 */
	public void shellowProcessing() throws IllegalCodeException {
		int ScopeCounter = 0;
		for (int lineCounter = 0; lineCounter < content.length; lineCounter++) {
			String line = content[lineCounter];
			LineType currentLineType = LineType.fitType(line);
			switch (currentLineType) {
			case DECLERATION:
				if (ScopeCounter == 0) {
					handleDecleration(line);
				}
				break;

			case ASSIGNMENT:
				if (ScopeCounter == 0) {
					handleAssignment(line);
				}
				break;

			case NON_METHOD_BLOCK:
				ScopeCounter++;
				handleNonMethodBlockDecleration(line);
				break;

			case METHOD_DECLERATION:
				ScopeCounter++;
				handleMethodBlockDecleration(line);
				break;

			case CLOSING_BLOCK:
				ScopeCounter--;
				if (ScopeCounter < 0) {
					throw new NonValidBlockClosingException();
				}
			default:
				break;
			}
		}

		if (ScopeCounter > 0) {
			throw new UnclosedBlockException();
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

			case IGNORABLE_LINE:
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
			Member knownMember = isKnownMember(e.getName());
			if (knownMember != null) {
				if (knownMember.hasValue && Type.canBeCasted(e.getType(),
						knownMember.getType())) {
					memberFound
							.setValue(knownMember.getType().getDefaultValue());
				}
			} else {
				throw e;
			}
		}
	}

	/**
	 * Handles deceleration of a non method block inside this block.
	 * 
	 * @param line
	 *            The deceleration line for this block.
	 * @throws IllegalCodeException
	 */
	protected void handleNonMethodBlockDecleration(String line) {

	}

	/**
	 * Handles deceleration of a method block inside this block.
	 * 
	 * @param line
	 *            The deceleration line for this block.
	 * @throws IllegalCodeException
	 */
	protected void handleMethodBlockDecleration(String line)
			throws IllegalCodeException {
		throw new IllegalMethodDeclerationLocationException();
	}

	private int findBlockEnd(int startLineNumber) {
		ScopeCounter
		for (int curLine = startLineNumber; curLine < content.length; curLine++) {
			
		}
		return -1;
	}
}