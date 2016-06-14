package oop.ex6.blockAnalayzer;

import java.util.LinkedList;
import java.util.regex.Pattern;

import oop.ex6.error.IllegalCodeException;
import oop.ex6.variables.Member;
import oop.ex6.variables.MemberFactory;
import oop.ex6.variables.Type;

/**
 * this class represents a method block
 */
public class MethodBlock extends Block {

	/*
	private class Argument {

		private Type typeOfArgument;
		private String nameOfArgument;

		private Argument(String type, String name) throws IllegalCodeException {
			this.typeOfArgument = Type.findType(type);
			this.nameOfArgument = checkName(name);
		}
	}
	*/
	private static final Pattern NAME_PATTERN = Pattern.compile("[a-zA-Z]\\w*");

	private String name;
	
	public MethodBlock(String type, String name, String arguments, String[] content, Member[] higherScopeMembers) {
		if (checkName(name)){
			this.name = name;
			String[] argumentsArray = arguments.split(",");
			for (String argument : argumentsArray){
				localMembers.addAll(MemberFactory.createMembers(argument));
			}
		}
	}
}
