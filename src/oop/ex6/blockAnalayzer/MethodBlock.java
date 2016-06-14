package oop.ex6.blockAnalayzer;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import oop.ex6.error.IllegalCodeException;
import oop.ex6.variables.Member;
import oop.ex6.variables.MemberFactory;
import oop.ex6.variables.Type;

/**
 * this class represents a method block
 */
public class MethodBlock extends Block {
	private static final Pattern NAME_PATTERN = Pattern.compile("[a-zA-Z]\\w*");

	// The reserved S-java words.
	private static final String[] RESERVED_WORDS = new String[] { "int", "double", "boolean",
			"char", "String", "void", "final", "if", "while", "true", "false", "return" };

	private String name;

	public MethodBlock(String type, String name, String arguments, String[] content,
			LinkedList<Member> higherScopeMembers) throws IllegalCodeException {
		if (checkName(name)) {
			this.name = name;
			String[] argumentsArray = arguments.split(",");
			for (String argument : argumentsArray) {
				localMembers.addAll(MemberFactory.createMembers(argument));
			}
			this.HigherScopeMembers = higherScopeMembers;
		}
	}

	/*
	 * Returns true if the given string is indeed a legal name.
	 */
	private boolean checkName(String name) {
		String trimmedName = name.trim();
		Matcher nameMatcher = NAME_PATTERN.matcher(trimmedName);
		return (nameMatcher.matches() && !isReservedWord(trimmedName));
	}

	private boolean isReservedWord(String name) {
		for (String reserved : RESERVED_WORDS) {
			if (reserved.equals(name)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void checkContent(MethodBlock[] knownMethods) throws IllegalCodeException {
		// TODO Auto-generated method stub

	}
}
