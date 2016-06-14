package oop.ex6.fileprocessing;

import java.util.regex.Pattern;

import oop.ex6.error.IllegalCodeException;
import oop.ex6.variables.Type;

public class MethodBlockFactory {

	private static final Pattern NAME_PATTERN = Pattern.compile("[a-zA-Z]\\w*");

	private class Argument {

		private Type typeOfArgument;
		private String nameOfArgument;

		private Argument(String type, String name) throws IllegalCodeException {
			this.typeOfArgument = Type.findType(type);
			this.nameOfArgument = checkName(name);
		}
	}
	
	

}
