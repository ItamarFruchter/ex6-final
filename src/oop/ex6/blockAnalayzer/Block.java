package oop.ex6.blockAnalayzer;

import java.util.AbstractSequentialList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

import oop.ex6.error.IllegalCodeException;
import oop.ex6.fileprocessing.LineType;
import oop.ex6.fileprocessing.MeaninglessLineException;
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

	// Constants meant to help with handling method call.
	private static final String METHOD_ARGUMENTS_SEPERATOR = ",",
			METHOD_ARGUMENDS_OPEN_BOUNDERY = ")",
			METHOD_ARGUMENDS_CLOSE_BOUNDERY = "(";

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
	/** All the methods available. */
	protected LinkedList<MethodBlock> knownMethods;

	/**
	 * Initializes all the members in this block's scope, checks validity of the
	 * creation of inner blocks (but dose not create them), checks the block
	 * structure.
	 * 
	 * @throws IllegalCodeException
	 */
	public void shellowProcessing() throws IllegalCodeException {
		for (int lineCounter = 0; lineCounter < content.length; lineCounter++) {
			String line = content[lineCounter];
			LineType currentLineType = LineType.fitType(line);
			switch (currentLineType) {
			case DECLERATION:
				handleDecleration(line);
				break;

			case ASSIGNMENT:
				handleAssignment(line);
				break;

			case NON_METHOD_BLOCK:
				handleNonMethodBlockDecleration(line);
				int blockEndIndex = findBlockEnd(lineCounter);
				if (blockEndIndex == -1) {
					throw new UnclosedBlockException();
				} else {
					lineCounter = blockEndIndex + 1;
				}
				break;

			case METHOD_DECLERATION:
				handleMethodBlockDecleration(line);
				int MethodEndIndex = findBlockEnd(lineCounter);
				if (MethodEndIndex < 0) {
					throw new UnclosedBlockException();
				} else {
					lineCounter = MethodEndIndex + 1;
				}
				break;

			case CLOSING_BLOCK:
				if (lineCounter != content.length - 1) {
					throw new NonValidBlockClosingException();
					// Iff it is not the last line.
				}
				break;

			case METHOD_CALLING:
				handleMethodCall()
				break;
				
			case RETURN_STATEMENT:
				handleReturnStatement();
				break;

			default:
				break;
			}
		}
	}

	/**
	 * Checks the content of the block (it's code lines).
	 */
	public void deepProcessing() throws IllegalCodeException {
		for (int curLineIndex = 0; curLineIndex < content.length; curLineIndex++) {
			String line = content[curLineIndex];
			LineType currentLineType = LineType.fitType(line);
			switch (currentLineType) {
			case NON_METHOD_BLOCK:
				int blockEndIndex = findBlockEnd(curLineIndex);
				String[] blockLines = cutBlockFromContent(curLineIndex,
						blockEndIndex);
				LinkedList<Member> newOuterScope = joinScopes();
				BlockFactory.createNonMethodBlock(blockLines, newOuterScope,
						knownMethods);
				curLineIndex = blockEndIndex + 1;
				break;

			case METHOD_DECLERATION:
				handleMethodBlockDecleration(line);
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
			throw new UnknownMemberNameException();
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

	/**
	 * Dose nothing except for the main block.
	 */
	protected void handleReturnStatement() throws IllegalCodeException {

	}

	/**
	 * Handles a call to a method.
	 * 
	 * @param line
	 *            The method call line to check.
	 */
	protected void handleMethodCall(String line) {
		int argumentStartIndex = line.indexOf(METHOD_ARGUMENDS_OPEN_BOUNDERY);
		int argumentsEndIndex = line.indexOf(METHOD_ARGUMENDS_CLOSE_BOUNDERY);
		String methodName = line.substring(0, argumentStartIndex - 1).trim();
		String methodArgumentsString = line.substring(argumentStartIndex,
				argumentsEndIndex);

		String[] argumentStrings = methodArgumentsString
				.split(METHOD_ARGUMENTS_SEPERATOR);
		
		Type[] argumentTypes = MemberFactory(argumentStrings, relevantMembers);

		for (MethodBlock methodBlock : knownMethods) {
			if (methodBlock.getName().equals(methodName)) {
				if (methodBlock.isVallidMethodCall(argumentTypes)) {
					
				}
			}
		}
	}

	// Cuts the block.
	private String[] cutBlockFromContent(int startLine, int endLine) {
		String[] blockLines = new String[(endLine + 1) - startLine];
		for (int curLineIndex = startLine; curLineIndex <= endLine; curLineIndex++) {
			blockLines[curLineIndex - startLine] = content[curLineIndex];
		}
		return blockLines;
	}

	/*
	 * Returns the joint scope - a linkedList of this scope's and all previous
	 * scopes members.
	 */
	private LinkedList<Member> joinScopes() {
		LinkedList<Member> jointScopeMembers = new LinkedList<Member>();
		jointScopeMembers.addAll(localMembers);
		Iterator<Member> higherScopeMemberIterator = higherScopeMembers.iterator();
		Member currentHigherScopeMember = higherScopeMemberIterator.next();
		while (higherScopeMemberIterator.hasNext()) {
			Iterator<Member> localMemberIterator = localMembers.iterator();
			Member currentMember = localMemberIterator.next();
			while (localMemberIterator.hasNext()) {
				if (currentMember.getName().equals(anObject)) {
					
				}
				currentMember = localMemberIterator.next();
			}
			currentHigherScopeMember = higherScopeMemberIterator.next();
		}
		return jointScopeMembers;
	}

	/**
	 * Finds the end of the block declared in the given line. Returns -1 if the
	 * block was never closed.
	 * 
	 * @param startLineNumber
	 * @return
	 */
	private int findBlockEnd(int startLineNumber) {
		int ScopeCounter = 0;
		for (int curLineIndex = startLineNumber; curLineIndex < content.length; curLineIndex++) {
			LineType currentLineType;
			try {
				currentLineType = LineType.fitType(content[curLineIndex]);
			} catch (IllegalCodeException e) { // Can only throw meaningless
												// line exception.
				currentLineType = null;
			}

			switch (currentLineType) {
			case NON_METHOD_BLOCK:
				ScopeCounter++;
				break;

			case METHOD_DECLERATION:
				ScopeCounter++;
				break;

			case CLOSING_BLOCK:
				ScopeCounter--;
				if (ScopeCounter == 0) {
					return curLineIndex;
				}
				break;

			default:
				break;
			}
		}
		return -1;
	}
}