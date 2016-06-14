package oop.ex6.fileprocessing;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import oop.ex6.blockAnalayzer.NonMethodBlock;
import oop.ex6.error.IllegalCodeException;
import oop.ex6.variables.Member;

public class NonMethodBlockFactory {

	private static final int BLOCK_DECLERATION = 0;
	private static final Pattern WORD = Pattern.compile("\\w+");
	private static final Pattern CONDITION = Pattern.compile("\\(.*\\)");
	private static final int ADJUST_INDEX = 1;

	
	
	public static NonMethodBlock createBlock(String[] blockLines, Member[] outerScope) throws IllegalCodeException{
		String blockArguments = new String(blockLines[BLOCK_DECLERATION]);
		Matcher wordMatcher = WORD.matcher(blockArguments);
		wordMatcher.find();
		String type = blockArguments.substring(wordMatcher.start(), wordMatcher.end());
		Matcher conditionMatcher = CONDITION.matcher(blockArguments);
		conditionMatcher.find();
		String condition = blockArguments.substring(conditionMatcher.start() + ADJUST_INDEX, 
				conditionMatcher.end() - ADJUST_INDEX);
		return new NonMethodBlock(type, condition, blockLines, outerScope);
		
	}
}
