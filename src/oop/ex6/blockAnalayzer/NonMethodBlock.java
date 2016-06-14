package oop.ex6.blockAnalayzer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import oop.ex6.error.IllegalCodeException;
import oop.ex6.variables.Member;
import oop.ex6.variables.MemberFactory;
import oop.ex6.variables.Type;
import oop.ex6.fileprocessing.LineType;

/**
 * A non-method block. In the current build - an if block or while block.
 */
public class NonMethodBlock extends Block {
	/**
	 * All of the possible non-method block types. Currently - only while and
	 * if.
	 */
	private enum NonMethodBlockType {
		IF("if"), WHILE("while");

		// The string representation of this block type.
		private String stringRepresentation;

		// A constructor.
		private NonMethodBlockType(String stringRepresentation) {
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
		public static NonMethodBlockType blockTypeFromString(
				String stringRepresentation) throws IllegalCodeException {
			for (NonMethodBlockType nonMethodType : NonMethodBlockType
					.values()) {
				if (nonMethodType.getRepresentation()
						.equals(stringRepresentation)) {
					return nonMethodType;
				}
			}
			throw new UnknownBlockTypeException();
		}
	}

	// This block's type.
	private final NonMethodBlockType type;

	// A regular expression for the structure of condition.
	private static final String MEMBER_NAME_REGEX = "\\s*\\w+\\s*",
			RAW_MEMBER_REGEX = "\\s*((-?\\d+(.\\d+)?)|true|false)\\s*";
	private static final String CONDITION_REGEX = "(" + MEMBER_NAME_REGEX + "|"
			+ RAW_MEMBER_REGEX + ")(((\\|\\|)|(\\&\\&))(" + MEMBER_NAME_REGEX
			+ "|" + RAW_MEMBER_REGEX + "))*";
	private static final Pattern CONDITION_PATTERN = Pattern
			.compile(CONDITION_REGEX),
			RAW_MEMBER_PATTERN = Pattern.compile(RAW_MEMBER_REGEX),
			MEMBER_NAME_PATTERN = Pattern.compile(MEMBER_NAME_REGEX);

	// The regular expression for condition splitters.
	private static final String CONDITION_SEPERATOR_REGEX = "(\\|\\|)|(\\&\\&)";

	// The condition type (boolean at this build. includes int and double).
	private String DEFAULT_TYPE_STRING = "boolean";
	private Type CONDITION_DEFAULT_TYPE = Type.findType(DEFAULT_TYPE_STRING);

	/**
	 * Constructs a non method block, or throws a relevant exception if there
	 * was a problem.
	 * 
	 * @param type
	 *            The string representation of a non method block type.
	 * @param condition
	 *            The condition of this block.
	 * @param content
	 *            A strings array with the commands in this block.
	 * @throws IllegalCodeException
	 */
	public NonMethodBlock(String type, String condition, String[] content,
			Member[] higherScopeMembers) throws IllegalCodeException {
		this.type = NonMethodBlockType.blockTypeFromString(type); // May throw
																	// UnknownBlockTypeException.
		this.HigherScopeMembers = higherScopeMembers;

		if (!checkCondition(condition)) {
			throw new NonValidConditionException();
		}

		this.content = content;
	}

	/*
	 * Checks the condition: for an if/while checks whether the string
	 * represents a legal condition.
	 * 
	 * @param condition The condition to check.
	 * 
	 * @return true iff the given string represents a legal condition.
	 */
	private boolean checkCondition(String condition) {
		Matcher coditionMatcher = CONDITION_PATTERN.matcher(condition);
		if (coditionMatcher.matches()) {
			String[] conditionVariables = condition
					.split(CONDITION_SEPERATOR_REGEX);
			for (String conditionString : conditionVariables) {
				Matcher rawMemberMatcher = RAW_MEMBER_PATTERN
						.matcher(condition);
				Matcher memberNameMatcher = MEMBER_NAME_PATTERN
						.matcher(condition);
				if (rawMemberMatcher.matches()) {
					if (!CONDITION_DEFAULT_TYPE
							.isValidValue(conditionString.trim())) {
						return false;
					}
				} else if (memberNameMatcher.matches()) {
					boolean foundKnownMember = false;
					for (Member knownMember : HigherScopeMembers) {
						if (knownMember.name.equals(conditionString)) {
							foundKnownMember = true;
						}
					}
					if (!foundKnownMember) {
						return false;
					}
				} else {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void checkContent(MethodBlock[] knownMethods)
			throws IllegalCodeException {
		for (String line : content) {
			LineType currentLineType = LineType.fitType(line);
			switch (currentLineType) {
			case DECLERATION:
				
				break;

			case ASSIGNMENT:
				
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
}