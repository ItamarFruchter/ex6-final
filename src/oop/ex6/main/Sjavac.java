package oop.ex6.main;

import java.io.File;
import java.io.IOException;
import oop.ex6.blockAnalayzer.MainBlock;
import oop.ex6.error.IllegalCodeException;
import oop.ex6.fileprocessing.FileProcessor;

/**
 * the class that holds the main method. determines whether the give code file
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
	}
}
