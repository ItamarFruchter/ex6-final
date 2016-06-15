package oop.ex6.blockAnalayzer;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import oop.ex6.error.IllegalCodeException;
import oop.ex6.fileprocessing.ReservedWord;
import oop.ex6.variables.Member;
import oop.ex6.variables.MemberFactory;
import oop.ex6.variables.Type;

/**
 * this class represents a method block
 */
public class MethodBlock extends Block {
	// A name pattern for a method's name.
	private static final Pattern NAME_PATTERN = Pattern.compile("[a-zA-Z]\\w*");

	// This method's name.
	private String name;
	private Member[] arguments;

	public MethodBlock(String type, String name, String arguments,
			String[] content, LinkedList<Member> higherScopeMembers,
			LinkedList<MethodBlock> previousCreatedMethods, int startingLine)
			throws IllegalCodeException {
		this.startingLine = startingLine;
		this.type = BlockType.blockTypeFromString(type);
		this.knownMethods = previousCreatedMethods;
		if (checkName(name)) {
			this.name = name;
			LinkedList<Member> methodArguments = new LinkedList<Member>();
			String[] argumentsArray = arguments.split(",");
			for (String argument : argumentsArray) {
				methodArguments.addAll(MemberFactory.createMembers(argument,
						higherScopeMembers, methodArguments));
			}
			for (Member argument : methodArguments) {
				argument.setValue(argument.getType().getDefaultValue());
			}
			this.arguments = methodArguments
					.toArray(new Member[methodArguments.size()]);
			this.localMembers.addAll(methodArguments);
			this.higherScopeMembers = higherScopeMembers;
			this.containedBlocks = new LinkedList<Block>();
		} else {
			throw new InvalidMethodName();
		}
	}

	/**
	 * Sets all the known methods to this method block.
	 * 
	 * @param knownMethods
	 *            All of the known methods in this code.
	 * @throws IllegalCodeException
	 */
	public void informMethod(LinkedList<MethodBlock> knownMethods)
			throws IllegalCodeException {
		this.knownMethods = knownMethods;
	}

	/*
	 * Returns true if the given string is indeed a legal name.
	 */
	private boolean checkName(String name) {
		String trimmedName = name.trim();
		Matcher nameMatcher = NAME_PATTERN.matcher(trimmedName);
		boolean isKnownMethod = false;
		Iterator<MethodBlock> knownMethodsIterator = knownMethods.iterator();
		MethodBlock currentMethod = knownMethodsIterator.next();
		while (knownMethodsIterator.hasNext()) {
			if (currentMethod.getName().equals(trimmedName)) {
				isKnownMethod = true;
			}
		}
		return (nameMatcher.matches()
				&& !ReservedWord.isReservedWord(trimmedName) && !isKnownMethod);
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