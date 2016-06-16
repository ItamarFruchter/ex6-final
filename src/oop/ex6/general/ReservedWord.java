package oop.ex6.general;

/**
 * Represents all the reserved words in S-java.
 */
public enum ReservedWord {
	INT("int"), DOUBLE("double"), BOOLEAN("boolean"), CHAR("char"), STRING(
			"String"), VOID("void"), FINAL("final"), IF("if"), WHILE(
					"while"), TRUE("true"), FALSE("false"), RETURN("return");

	// This reserved word.
	private final String word;

	// Constructor.
	private ReservedWord(String word) {
		this.word = word;
	}

	/**
	 * Checks if the given string is a reserved word in S-java.
	 * 
	 * @param word
	 *            The string to check.
	 * @return true iff the given string is a reserved word in S-java.
	 */
	public static boolean isReservedWord(String word) {
		for (ReservedWord reservedWord : ReservedWord.values()) {
			if ((reservedWord.word).equals(word)) {
				return true;
			}
		}
		return false;
	}
}
