package oop.ex6.fileprocessing;

import oop.ex6.error.IllegalCodeException;

public class MeaninglessLineException extends IllegalCodeException {

	private static final long serialVersionUID = 1L;
	
	private final static String MESSAGE = "The line has no meaning.";
	
	public MeaninglessLineException() {
		this.meaningfulMessage = MESSAGE;
	}


}
