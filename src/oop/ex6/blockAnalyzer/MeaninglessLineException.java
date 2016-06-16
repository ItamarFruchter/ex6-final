package oop.ex6.blockAnalyzer;

import oop.ex6.general.IllegalCodeException;

/**
 * Arises whenever there is a line that don't fit any line type.
 */
class MeaninglessLineException extends IllegalCodeException {

	private static final long serialVersionUID = 1L;
	
	private final static String MESSAGE = "The line has no meaning.";
	
	public MeaninglessLineException() {
		this.meaningfulMessage = MESSAGE;
	}


}
