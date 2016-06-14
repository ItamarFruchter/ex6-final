package oop.ex6.blockAnalayzer;

import java.util.LinkedList;
import oop.ex6.variables.Member;

/**
 * Represents any block in S-java. 
 */
public abstract class Block {
	/** The known local members this block (scope wise). */
	 protected LinkedList<Member> localMembers;
	 /** The known local members this block (scope wise). */
	 protected Member[] HigherScopeMembers;
	 /** The content of this block. */
	 protected String[] content;
	 
	 /**
	  * Checks the content of the block (it's code lines).
	  */
	 public abstract void checkContent(MethodBlock[] knownMethods);
}