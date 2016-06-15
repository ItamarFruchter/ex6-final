package oop.ex6.main;

import java.io.File;
import java.io.IOException;

import oop.ex6.blockAnalayzer.MainBlock;
import oop.ex6.error.IllegalCodeException;
import oop.ex6.fileprocessing.FileProcessor;

public class Sjavac {

	private static final int FILE_LOCATION = 0, 
			LEGAL_CODE = 0,
			ILLEGAL_CODE = 1,
			IO_EXCEPTION = 2;
	
	private static final String INFORMATIVE_IO_MESSAGE = "problem in accessing the file.";

	public static void main(String[] args) {
		try {
			File codeFile = new File(args[FILE_LOCATION]);
			String[] codeLines = FileProcessor.readFile(codeFile);
			new MainBlock(codeLines);
		} catch (IOException e) {
			System.out.println(IO_EXCEPTION);
			System.err.println(INFORMATIVE_IO_MESSAGE);
		} catch (IllegalCodeException e){
			System.out.println(ILLEGAL_CODE);
			System.err.println(e.getMessage());
		}
		System.out.println(LEGAL_CODE);

		/**
		 * String line = "boolean a, b ,c , d = true, e, f = 5;"; try { Member[]
		 * members = MemberFactory.createMembers(line); for (Member member :
		 * members){ System.out.println(member.name);
		 * System.out.println(member.hasValue); } } catch (IllegalCodeException
		 * e) { System.out.println("ERROR"); }
		 * 
		 * String Memb = "\\s*\\w+\\s*"; String Var =
		 * "\\s*((-?\\d+(.\\d+)?)|true|false)\\s*"; Pattern p =
		 * Pattern.compile("("+Memb+"|"+Var+")(((\\|\\|)|(\\&\\&))("+Memb+"|"+
		 * Var+"))*"); Matcher m = p.matcher(" a || && 0.2");
		 * System.out.println(m.matches()); System.out.println("\'");
		 */
	}

}
