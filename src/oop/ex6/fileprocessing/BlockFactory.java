package oop.ex6.fileprocessing;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import oop.ex6.blockAnalayzer.MethodBlock;
import oop.ex6.blockAnalayzer.NonMethodBlock;
import oop.ex6.error.IllegalCodeException;
import oop.ex6.variables.Member;

/**
 * this class can generate block objects
 */
public class BlockFactory {

	private static final int BLOCK_DECLERATION = 0;
	private static final Pattern WORD = Pattern.compile("\\w+");
	private static final Pattern IN_BRACKETS = Pattern.compile("\\(.*\\)");
	private static final int ADJUST_INDEX_1 = 1;
	private static final int ADJUST_INDEX_2 = 2;

	private BlockFactory() {}
	
	/**
	 * 
	 * @param blockLines
	 * @param outerScope
	 * @return
	 * @throws IllegalCodeException
	 */
	public static MethodBlock createMethodBlock(String[] blockLines, Member[] outerScope)
			throws IllegalCodeException {
		String blockDecleration = new String(blockLines[BLOCK_DECLERATION]);
		String type = getType(blockDecleration);
		String name = getName(blockDecleration);
		String arguments = getInBrackets(blockDecleration);
		String[] content = getContent(blockLines);
		return new MethodBlock(type, name, arguments, content, outerScope);
	}

	public static NonMethodBlock createNonMethodBlock(String[] blockLines, Member[] outerScope)
			throws IllegalCodeException {
		String blockDecleration = new String(blockLines[BLOCK_DECLERATION]);
		String type = getType(blockDecleration);
		String condition = getInBrackets(blockDecleration);
		String[] content = getContent(blockLines);
		return new NonMethodBlock(type, condition, content, outerScope);
	}

	private static String getType(String blockDecleration){
		Matcher wordMatcher = WORD.matcher(blockDecleration);
		wordMatcher.find();
		return blockDecleration.substring(wordMatcher.start(), wordMatcher.end());
	}
	
	private static String getName(String blockDecleration){
		Matcher wordMatcher = WORD.matcher(blockDecleration);
		wordMatcher.find();
		wordMatcher.find();
		return blockDecleration.substring(wordMatcher.start(), wordMatcher.end());
	}
	
	private static String getInBrackets (String blockDecleration){
		Matcher conditionMatcher = IN_BRACKETS.matcher(blockDecleration);
		conditionMatcher.find();
		return blockDecleration.substring(conditionMatcher.start() + ADJUST_INDEX_1,
				conditionMatcher.end() - ADJUST_INDEX_1);
	}
	
	private static String[] getContent (String[] blockLines){
		// adjusts the array to its right size
		String[] content = new String[blockLines.length - ADJUST_INDEX_2];
		for (int i = 1; i <= content.length; i++){
			content[i - ADJUST_INDEX_1] = blockLines[i];
		}
		return content;
	}
}
