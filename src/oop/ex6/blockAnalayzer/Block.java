package oop.ex6.blockAnalayzer;

import java.util.Iterator;
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

		// A flag field.
		private static String MAIN_STRING_REPRESENTATION = "MAIN";

		// The string representation of this block type.
		private String stringRepresentation;

		/** Indicates whether the type represents a method block. */
		public boolean isMethod;

		// A constructor.
		private BlockType(String stringRepresentation, boolean isMethod) {
			this.stringRepresentation = stringRepresentation;
		}

		private String getRepresentation() {
			if (stringRepresentation == null) {
				stringRepresentation = MAIN_STRING_REPRESENTATION;
			}
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
	/** The starting line for this block. */
	protected int startingLine;

	/**
	 * Processes the content of this block.
	 * 
	 * @throws IllegalCodeException
	 */
	protected void process() throws IllegalCodeException {
		shallowProcessing();
		deepProcessing();
	}

	/**
	 * Initializes all the members in this block's scope, checks validity of the
	 * creation of inner blocks (but dose not create them), checks the block
	 * structure.
	 * 
	 * @throws IllegalCodeException
	 */
	protected void shallowProcessing() throws IllegalCodeException {
		for (int lineCounter = 0; lineCounter < content.length; lineCounter++) {
			String line = content[lineCounter];
			try {
				LineType currentLineType = LineType.fitType(line);
				switch (currentLineType) {
				case DECLERATION:
					handleDecleration(line);
					break;

				case ASSIGNMENT:
					handleAssignment(line);
					break;

				case NON_METHOD_BLOCK:
					int blockEndIndex = findBlockEnd(lineCounter);
					String[] innerBlockContent = cutBlockFromContent(
							lineCounter, blockEndIndex);
					handleNonMethodBlockDecleration(innerBlockContent,
							relativeLine(lineCounter));
					if (blockEndIndex == -1) {
						throw new UnclosedBlockException();
					} else {
						lineCounter = blockEndIndex;
					}
					break;

				case METHOD_DECLERATION:
					int methodEndIndex = findBlockEnd(lineCounter);
					String[] innerMethodContent = cutBlockFromContent(
							lineCounter, methodEndIndex);
					handleMethodBlockDecleration(innerMethodContent,
							relativeLine(lineCounter));
					if (methodEndIndex < 0) {
						throw new UnclosedBlockException();
					} else {
						lineCounter = methodEndIndex;
					}
					break;

				case CLOSING_BLOCK:
					if (lineCounter != content.length - 1) {
						throw new NonValidBlockClosingException();
						// Iff it is not the last line.
					}
					break;

				case METHOD_CALLING:
					handleMethodCall(line);
					break;

				case RETURN_STATEMENT:
					handleReturnStatement();
					break;

				default:
					break;
				}
			} catch (IllegalCodeException e) {
				throw new IllegalCodeException(e, relativeLine(lineCounter));
			}
		}
	}

	// returns the relative line.
	private int relativeLine(int lineCounter) {
		return lineCounter + startingLine;
	}

	/**
	 * Checks the content of the block (it's code lines).
	 */
	protected void deepProcessing() throws IllegalCodeException {
		Iterator<Block> containedBlockIterator = containedBlocks.iterator();
		if (containedBlockIterator.hasNext()) {
			Block currentContainedBlock = containedBlockIterator.next();
			while (currentContainedBlock != null) {
				currentContainedBlock.process();
				if (containedBlockIterator.hasNext()) {
					currentContainedBlock = containedBlockIterator.next();
				} else {
					currentContainedBlock = null;
				}
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
			if (knownMember.getName().equals(nameToCheck)) {
				return knownMember;
			}
		}

		for (Member knownGlobalMember : higherScopeMembers) {
			if (knownGlobalMember.getName().equals(nameToCheck)) {
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
		LinkedList<Member> newMembers = MemberFactory.createMembers(line,
				higherScopeMembers, localMembers);
		if (newMembers != null) {
			for (Member newMember : newMembers) {
				localMembers.add(newMember);
			}
		}
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
				if (knownMember.isInitiallized() && Type
						.canBeCasted(e.getType(), knownMember.getType())) {
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
	 * @param content
	 *            The content of this block, including the deceleration line.
	 * @throws IllegalCodeException
	 */
	protected void handleNonMethodBlockDecleration(String[] content,
			int blockStartingLine) throws IllegalCodeException {
		LinkedList<Member> relevantScope = joinScopes();
		containedBlocks.add(BlockFactory.createNonMethodBlock(content,
				relevantScope, knownMethods, blockStartingLine));
	}

	/**
	 * Handles deceleration of a method block inside this block.
	 * 
	 * @param content
	 *            The content of this block, including the deceleration line.
	 * @throws IllegalCodeException
	 */
	protected void handleMethodBlockDecleration(String[] content,
			int blockStartingLine) throws IllegalCodeException {
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
	 * @throws IllegalCodeException
	 */
	protected void handleMethodCall(String line) throws IllegalCodeException {
		int argumentStartIndex = line.indexOf(METHOD_ARGUMENDS_OPEN_BOUNDERY);
		int argumentsEndIndex = line.indexOf(METHOD_ARGUMENDS_CLOSE_BOUNDERY);
		String methodName = line.substring(0, argumentStartIndex - 1).trim();
		if (argumentsEndIndex != argumentsEndIndex) {
			String methodArgumentsString = line.substring(argumentStartIndex,
					argumentsEndIndex);
			String[] argumentStrings = methodArgumentsString
					.split(METHOD_ARGUMENTS_SEPERATOR);

			Type[] argumentTypes = MemberFactory
					.createArgumentsType(argumentStrings, joinScopes());

			for (MethodBlock methodBlock : knownMethods) {
				if (methodBlock.getName().equals(methodName)) {
					if (methodBlock.isVallidMethodCall(argumentTypes)) {

					}
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
		Iterator<Member> higherScopeMemberIterator = higherScopeMembers
				.iterator();
		Member currentHigherScopeMember = higherScopeMemberIterator.next();
		while (higherScopeMemberIterator.hasNext()) {
			boolean alreadyExist = false;
			Iterator<Member> localMemberIterator = localMembers.iterator();
			Member currentMember = localMemberIterator.next();
			while (localMemberIterator.hasNext() && !alreadyExist) {
				if (currentMember.getName()
						.equals(currentHigherScopeMember.getName())) {
					alreadyExist = true;
				}
				currentMember = localMemberIterator.next();
			}

			if (!alreadyExist) {
				jointScopeMembers.add(currentHigherScopeMember);
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
	 * @return returns the index of the line (in this block) which closes the
	 *         block.
	 * @throws IllegalCodeException
	 */
	protected int findBlockEnd(int startLineNumber)
			throws IllegalCodeException {
		int ScopeCounter = 0;
		for (int curLineIndex = startLineNumber; curLineIndex < content.length; curLineIndex++) {
			LineType currentLineType;
			currentLineType = LineType.fitType(content[curLineIndex]);
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