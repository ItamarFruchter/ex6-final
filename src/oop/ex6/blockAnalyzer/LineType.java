package oop.ex6.blockAnalyzer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import oop.ex6.general.IllegalCodeException;

/**
 * holds all the possible line types in S-java and their regEx patterns.
 */
enum LineType {
	CLOSING_BLOCK("\\s*\\}\\s*"), 
	DECLERATION("\\s*\\w+(\\s+\\w+){1,2}(\\s*=\\s*([-\\w\"\\.']+|'.')\\s*)?((\\s*,\\s*\\w+)\\s*"
					+ "(=\\s*([-\\w\"\\.']+|'.')\\s*)?)*?\\s*;\\s*"), 
	ASSIGNMENT("\\s*\\w+\\s*=\\s*([-\\w\"\\.']+|'.')\\s*;\\s*"),
	// such as if/while blocks
	NON_METHOD_BLOCK("\\s*\\w+\\s*\\(\\s*([-\\w\"\\.']+|'.')\\s*(\\s*((\\|\\|)|(\\&\\&))\\s*([-\\w\"\\.']+|'.')\\s*)*\\)\\s*\\{\\s*"),
	METHOD_DECLERATION("\\s*\\w+\\s+\\w+\\s*\\(\\s*(\\w+\\s*\\w+(\\s*,\\s*\\w+\\s*\\w+)*)?\\s*\\)\\s*\\{\\s*"), 
	METHOD_CALLING("\\s*\\w+\\s*\\((\\s*([-\\w\"\\.']+|'.')(\\s*,\\s*([-\\w\"\\.']+|'.'))*)?\\s*\\)\\s*;\\s*"), 
	IGNORABLE_LINE("\\s*//.*||\\s*"), 
	RETURN_STATEMENT("\\s*return\\s*;\\s*");

	/*
	 * the pattern that represents the line type
	 */
	private Pattern pattern;

	/*
	 * constructs the different line type
	 */
	private LineType(String regEx) {
		this.pattern = Pattern.compile(regEx);
	}

	/**
	 * returns the pattern of the line type.
	 */
	Pattern getPattern() {
		return pattern;
	}

	/**
	 * Finds the lineType fitting for this line, if any is known.
	 * 
	 * @param line
	 *            The line to fit.
	 * @return A lineType if any was
	 * @throws IllegalCodeException
	 */
	public static LineType fitType(String line) throws IllegalCodeException {
		for (LineType lineType : LineType.values()) {
			Matcher lineMatcher = lineType.getPattern().matcher(line);
			if (lineMatcher.matches()) {
				return lineType;
			}
		}
		throw new MeaninglessLineException();
	}
}