package oop.ex6.blockAnalayzer;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import oop.ex6.error.IllegalCodeException;
import oop.ex6.fileprocessing.ReservedWord;
import oop.ex6.variables.Member;
import oop.ex6.variables.MemberFactory;

/**
 * this class represents a method block
 */
public class MethodBlock extends Block {
	// A name pattern for a method's name.
	private static final Pattern NAME_PATTERN = Pattern.compile("[a-zA-Z]\\w*");

	// This method's name.
	private String name;

	public MethodBlock(String type, String name, String arguments,
			String[] content, LinkedList<Member> higherScopeMembers)
			throws IllegalCodeException {
		this.type = BlockType.blockTypeFromString(type);
		if (checkName(name)) {
			this.name = name;
			String[] argumentsArray = arguments.split(",");
			for (String argument : argumentsArray) {
				localMembers.addAll(MemberFactory.createMembers(argument,
						higherScopeMembers));
			}
			this.higherScopeMembers = higherScopeMembers;
			this.containedBlocks = new LinkedList<Block>();
		} else {
			throw new InvalidMethodName();
		}
	}

	/*
	 * Returns true if the given string is indeed a legal name.
	 */
	private boolean checkName(String name) {
		String trimmedName = name.trim();
		Matcher nameMatcher = NAME_PATTERN.matcher(trimmedName);
		return (nameMatcher.matches()
				&& !ReservedWord.isReservedWord(trimmedName));
	}

	/**
	 * A getter for this method's name.
	 * 
	 * @return The name of this method.
	 */
	public String getName() {
		return name;
	}

	@Override
	public void checkContent(MethodBlock[] knownMethods)
			throws IllegalCodeException {

	}
}
