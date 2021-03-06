package oop.ex6.blockAnalyzer;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import oop.ex6.general.IllegalCodeException;
import oop.ex6.general.ReservedWord;
import oop.ex6.variables.Member;
import oop.ex6.variables.MemberFactory;
import oop.ex6.variables.Type;

/**
 * this class represents a method block
 */
class MethodBlock extends Block {
	// A name pattern for a method's name.
	private static final Pattern NAME_PATTERN = Pattern.compile("[a-zA-Z]\\w*");

	private static final Pattern EMPTY_STRING_PATTERN = Pattern.compile("\\s*");

	// The separator char between two arguments.
	private static final String ARGUMENT_SEPERATOR = ",";

	// This method's name.
	private String name;
	private Member[] arguments;

	public MethodBlock(String type, String name, String arguments,
			String[] content, LinkedList<Member> higherScopeMembers,
			LinkedList<MethodBlock> previousCreatedMethods, int startingLine)
			throws IllegalCodeException {
		this.content = content;
		this.higherScopeMembers = deepCopyMembers(higherScopeMembers);
		this.startingLine = startingLine + 1;
		this.type = BlockType.blockTypeFromString(type);
		this.knownMethods = previousCreatedMethods;
		this.localMembers = new LinkedList<Member>();
		if (checkName(name)) {
			this.name = name;
			Matcher emptyStringMatcher = EMPTY_STRING_PATTERN
					.matcher(arguments);
			if (!emptyStringMatcher.matches()) {
				LinkedList<Member> methodArguments = new LinkedList<Member>();
				String[] argumentStrings = arguments.split(ARGUMENT_SEPERATOR);
				for (String argumentString : argumentStrings) {
					methodArguments
							.addAll(MemberFactory.createMembers(argumentString,
									higherScopeMembers, methodArguments));
				}
				for (Member argument : methodArguments) {
					argument.setValue(argument.getType().getDefaultValue());
				}
				this.arguments = methodArguments
						.toArray(new Member[methodArguments.size()]);
				if (methodArguments != null) {
					this.localMembers.addAll(methodArguments);
				}
			}
			this.containedBlocks = new LinkedList<Block>();
		} else {
			throw new InvalidMethodNameException();
		}
	}

	/**
	 * Sets all the known methods to this method block.
	 * 
	 * @param knownMethods
	 *            All of the known methods in this code.
	 * @throws IllegalCodeException
	 */
	public void informMethod(LinkedList<MethodBlock> knownMethods,
			LinkedList<Member> globalMembers) throws IllegalCodeException {
		this.knownMethods = knownMethods;
		this.higherScopeMembers = deepCopyMembers(globalMembers);
	}

	/*
	 * Returns true if the given string is indeed a legal name.
	 */
	private boolean checkName(String name) {
		String trimmedName = name.trim();
		Matcher nameMatcher = NAME_PATTERN.matcher(trimmedName);
		boolean isKnownMethod = false;
		for (MethodBlock knownMethod : knownMethods) {
			if (knownMethod.getName().equals(name)) {
				isKnownMethod = true;
			}
		}
		return (nameMatcher.matches()
				&& !ReservedWord.isReservedWord(trimmedName) && !isKnownMethod);
	}

	@Override
	protected void shallowProcessing() throws IllegalCodeException {
		super.shallowProcessing();
		boolean foundReturnStatement = false;
		for (int lineIndex = content.length - 1; lineIndex >= 0; lineIndex--) {
			String line = content[lineIndex];
			Matcher emptyLineMatcher = EMPTY_STRING_PATTERN.matcher(line);
			if (!emptyLineMatcher.matches()) {
				if (LineType.fitType(line).equals(LineType.RETURN_STATEMENT)) {
					foundReturnStatement = true;
					break;
				}
			}
		}
		if (!foundReturnStatement) {
			IllegalCodeException e = new MissingReturnStatementException();
			throw new IllegalCodeException(e, startingLine);
		}
	}

	/**
	 * @param inputArguments
	 *            the arguments the method is called with
	 * @return true if the arguments are valid, false otherwise
	 */
	public boolean isVallidMethodCall(Type[] argumentsType) {
		if (arguments.length != argumentsType.length) {
			return false;
		}
		boolean isValid = true;
		for (int i = 0; i < arguments.length; i++) {
			isValid = Type.canBeCasted(arguments[i].getType(),
					argumentsType[i]);
			if (!isValid) {
				break;
			}
		}
		return isValid;
	}

	/**
	 * A getter for this method's name.
	 * 
	 * @return The name of this method.
	 */
	public String getName() {
		return name;
	}
}