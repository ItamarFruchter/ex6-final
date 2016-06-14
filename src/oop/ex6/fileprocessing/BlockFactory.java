package oop.ex6.fileprocessing;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import oop.ex6.blockAnalayzer.MethodBlock;
import oop.ex6.blockAnalayzer.NonMethodBlock;
import oop.ex6.error.IllegalCodeException;
import oop.ex6.variables.Member;

public class BlockFactory {

	private static final int BLOCK_DECLERATION = 0;
	private static final Pattern WORD = Pattern.compile("\\w+");
	private static final Pattern IN_BRACKETS = Pattern.compile("\\(.*\\)");
	private static final int ADJUST_INDEX = 1;

	public static MethodBlock createMethodBlock(String[] blockLines, Member[] outerScope)
			throws IllegalCodeException {

	}

	public static NonMethodBlock createNonMethodBlock(String[] blockLines, Member[] outerScope)
			throws IllegalCodeException {

	}

	public static NonMethodBlock createBlock(String[] blockLines, Member[] outerScope)
			throws IllegalCodeException {
		String blockDecleration = new String(blockLines[BLOCK_DECLERATION]);
		Matcher wordMatcher = WORD.matcher(blockDecleration);
		wordMatcher.find();
		String type = blockDecleration.substring(wordMatcher.start(), wordMatcher.end());
		Matcher conditionMatcher = IN_BRACKETS.matcher(blockDecleration);
		conditionMatcher.find();
		String condition = blockDecleration.substring(conditionMatcher.start() + ADJUST_INDEX,
				conditionMatcher.end() - ADJUST_INDEX);
		return new NonMethodBlock(type, condition, blockLines, outerScope);

	}
	
	private static String findType(String[] blockLines){
		String blockDecleration = new String(blockLines[BLOCK_DECLERATION]);
		Matcher wordMatcher = WORD.matcher(blockDecleration);
		wordMatcher.find();
		String type = blockDecleration.substring(wordMatcher.start(), wordMatcher.end());
	}
}
