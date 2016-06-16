package oop.ex6.blockAnalayzer;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import oop.ex6.general.IllegalCodeException;
import oop.ex6.variables.Member;
import oop.ex6.variables.Type;

/**
 * A non-method block. In the current build - an if block or while block.
 */
class NonMethodBlock extends Block {
	// A regular expression for the structure of condition.
	private static final String MEMBER_NAME_REGEX = "\\s*\\w+\\s*",
			RAW_MEMBER_REGEX = "\\s*((-?\\d+(.\\d+)?)|(\\s*true\\s*)|(\\s*false\\s*))\\s*";
	private static final String CONDITION_REGEX = "(" + MEMBER_NAME_REGEX + "|"
			+ RAW_MEMBER_REGEX + ")(((\\|\\|)|(\\&\\&))(" + MEMBER_NAME_REGEX
			+ "|" + RAW_MEMBER_REGEX + "))*";
	private static final Pattern CONDITION_PATTERN = Pattern
			.compile(CONDITION_REGEX),
			RAW_MEMBER_PATTERN = Pattern.compile(RAW_MEMBER_REGEX),
			MEMBER_NAME_PATTERN = Pattern.compile(MEMBER_NAME_REGEX);

	// The regular expression for condition splitters.
	private static final String CONDITION_SEPERATOR_REGEX = "((\\|\\|)|(\\&\\&))";

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
			LinkedList<Member> higherScopeMembers,
			LinkedList<MethodBlock> knownMethods, int startingLine)
			throws IllegalCodeException {
		this.localMembers = new LinkedList<Member>();
		this.startingLine = startingLine + 1;
		this.type = BlockType.blockTypeFromString(type); // May throw
															// UnknownBlockTypeException.
		this.higherScopeMembers = deepCopyMembers(higherScopeMembers);

		if (!checkCondition(condition)) {
			throw new NonValidConditionException();
		}

		this.content = content;
		this.containedBlocks = new LinkedList<Block>();
		this.knownMethods = knownMethods;
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
						.matcher(conditionString);
				Matcher memberNameMatcher = MEMBER_NAME_PATTERN
						.matcher(conditionString);
				if (rawMemberMatcher.matches()) {
					if (!CONDITION_DEFAULT_TYPE
							.isValidValue(conditionString.trim())) {
						return false;
					}
				} else if (memberNameMatcher.matches()) {
					boolean foundKnownMember = false;
					for (Member knownMember : higherScopeMembers) {
						if (knownMember.getName().equals(conditionString.trim())) {
							if (Type.canBeCasted(CONDITION_DEFAULT_TYPE,
									knownMember.getType())) {
								if (knownMember.isInitiallized()) {
									foundKnownMember = true;
								}
							}
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
}