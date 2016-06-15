package oop.ex6.blockAnalayzer;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import oop.ex6.error.IllegalCodeException;
import oop.ex6.variables.Member;

/**
 * this class can generate block objects
 */
public class BlockFactory {

	private static final int BLOCK_DECELERATION = 0;
	private static final Pattern WORD = Pattern.compile("\\w+");
	private static final Pattern IN_BRACKETS = Pattern.compile("\\(.*\\)");
	private static final int ADJUST_INDEX_1 = 1;
	private static final int ADJUST_INDEX_2 = 2;

	/**
	 * we don't want instances of this class
	 */
	private BlockFactory() {
	}

	/**
	 * @param blockLines
	 *            the lines of the code in an array, line by line
	 * @param outerScope
	 *            the members from outer scopes
	 * @return a method block object
	 * @throws IllegalCodeException
	 */
	public static MethodBlock createMethodBlock(String[] blockLines, LinkedList<Member> outerScope)
			throws IllegalCodeException {
		String blockDecleration = new String(blockLines[BLOCK_DECELERATION]);
		String type = getType(blockDecleration);
		String name = getName(blockDecleration);
		String arguments = getInBrackets(blockDecleration);
		String[] content = getContent(blockLines);
		return new MethodBlock(type, name, arguments, content, outerScope);
	}

	/**
	 * @param blockLines
	 *            the lines of the code in an array, line by line
	 * @param outerScope
	 *            the members from outer scopes
	 * @return a non-method block object
	 * @throws IllegalCodeException
	 */
	public static NonMethodBlock createNonMethodBlock(String[] blockLines,
			LinkedList<Member> outerScope, LinkedList<MethodBlock> codeMethods)
			throws IllegalCodeException {
		String blockDecleration = new String(blockLines[BLOCK_DECELERATION]);
		String type = getType(blockDecleration);
		String condition = getInBrackets(blockDecleration);
		String[] content = getContent(blockLines);
		return new NonMethodBlock(type, condition, content, outerScope, codeMethods);
	}

	public static MainBlock createMainBlock(String[] codeLines) {
		return new MainBlock(codeLines);
	}

	/*
	 * returns the type of the block
	 */
	private static String getType(String blockDecleration) {
		Matcher wordMatcher = WORD.matcher(blockDecleration);
		wordMatcher.find();
		return blockDecleration.substring(wordMatcher.start(), wordMatcher.end());
	}

	/*
	 * if it is a method block, return the name of the method
	 */
	private static String getName(String blockDecleration) {
		Matcher wordMatcher = WORD.matcher(blockDecleration);
		wordMatcher.find();
		wordMatcher.find();
		return blockDecleration.substring(wordMatcher.start(), wordMatcher.end());
	}

	/*
	 * return the string that inside the brackets
	 */
	private static String getInBrackets(String blockDecleration) {
		Matcher conditionMatcher = IN_BRACKETS.matcher(blockDecleration);
		conditionMatcher.find();
		return blockDecleration.substring(conditionMatcher.start() + ADJUST_INDEX_1,
				conditionMatcher.end() - ADJUST_INDEX_1);
	}

	/*
	 * returns the content of the block line by line (without the deceleration
	 * and closing line)
	 */
	private static String[] getContent(String[] blockLines) {
		// adjusts the array to its right size
		String[] content = new String[blockLines.length - ADJUST_INDEX_2];
		for (int i = 1; i <= content.length; i++) {
			content[i - ADJUST_INDEX_1] = blockLines[i];
		}
		return content;
	}
}
