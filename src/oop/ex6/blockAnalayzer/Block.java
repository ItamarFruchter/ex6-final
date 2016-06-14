package oop.ex6.blockAnalayzer;

import java.util.LinkedList;
import oop.ex6.variables.Member;

/**
 * Represents any block in S-java. 
 */
public abstract class Block {
	/**
	 * The known local members this block (scope wise).
	 */
	 protected LinkedList<Member> localMembers;
	 /**
	  * The known local members this block (scope wise).
	  */
	 protected LinkedList<Member> globalMembers;
	 
	 /**
	 * Checks the condition: for an if/while checks whether the string
	 * represents a legal condition, and for a method the correct types.
	 * 
	 * @param condition
	 *            The condition to check.
	 * @return true iff the given string represents a legal condition.
	 */
	 protected abstract boolean checkCondition(String condition);
}