package oop.ex6.fileprocessing;

import java.util.regex.Matcher;

/**
 * this class analyze what's the type of each line
 */
public class LineAnalyzer {
	
	private LineAnalyzer(){}
	
	/**
	 * @param line - the line we want to check
	 * @return the type of line
	 * @throws MeaninglessLineException
	 */
	public static LineType analyzeLine(String line) throws MeaninglessLineException{
		for (LineType lineType : LineType.values()){
			Matcher lineMatcher = lineType.getPattern().matcher(line);
			if (lineMatcher.matches()){
				return lineType;
			}
		}
		throw new MeaninglessLineException();
	}
}
