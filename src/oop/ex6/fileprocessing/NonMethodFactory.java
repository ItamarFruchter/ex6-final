package oop.ex6.fileprocessing;

import oop.ex6.error.IllegalCodeException;
import oop.ex6.variables.Type;

public class NonMethodFactory {

	private class Argument{
		
		private Type typeOfArgument;
		private String nameOfArgument;
		
		private Argument(String type, String name) throws IllegalCodeException{
			this.typeOfArgument = Type.findType(type);
			this.nameOfArgument = checkName(name);
		}
	}
	
	
	public static createMethod(String[] blockLines, Members[] outerScope){
		
	}
}
