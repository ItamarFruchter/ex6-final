package oop.ex6.error;

public class IllegalCodeException extends Exception {
	private static final long serialVersionUID = 1L;

	protected String meaningfulMessage;
	
	public String getMessage(){
		return meaningfulMessage;
	}
}
