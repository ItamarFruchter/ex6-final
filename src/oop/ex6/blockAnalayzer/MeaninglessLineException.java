package oop.ex6.blockAnalayzer;

import oop.ex6.general.IllegalCodeException;

class MeaninglessLineException extends IllegalCodeException {

	private static final long serialVersionUID = 1L;
	
	private final static String MESSAGE = "The line has no meaning.";
	
	public MeaninglessLineException() {
		this.meaningfulMessage = MESSAGE;
	}


}
