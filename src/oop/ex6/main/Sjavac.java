package oop.ex6.main;

import java.io.File;
import java.io.IOException;

import oop.ex6.blockAnalyzer.MainBlock;

/**
 * the class that holds the main method. determines whether the given code file
 * is compilable 
 */
public class Sjavac {

	private static final int FILE_LOCATION = 0, 
			LEGAL_CODE = 0,
			ILLEGAL_CODE = 1,
			IO_EXCEPTION = 2;
	
	private static final String INFORMATIVE_IO_MESSAGE = "problem in accessing the file.";

	/**
	 * @param args - array of size 1 - contains the path to the file
	 */
	public static void main(String[] args) {
		boolean isLegal = true;
		try {
			File codeFile = new File(args[FILE_LOCATION]);
			String[] codeLines = FileProcessor.readFile(codeFile);
			new MainBlock(codeLines);
		} catch (IOException e) {
			System.out.println(IO_EXCEPTION);
			System.err.println(INFORMATIVE_IO_MESSAGE);
			isLegal = false;
		} catch (Exception e){
			System.out.println(ILLEGAL_CODE);
			System.err.println(e.getMessage());
			isLegal = false;
		}
		if (isLegal){
			System.out.println(LEGAL_CODE);
		}
	}
}
