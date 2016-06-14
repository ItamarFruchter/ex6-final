package oop.ex6.blockAnalayzer;

import java.util.LinkedList;

import oop.ex6.error.IllegalCodeException;
import oop.ex6.variables.Member;
import oop.ex6.variables.MemberFactory;
import oop.ex6.variables.NonValidValueException;

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
	public abstract void checkContent(MethodBlock[] knownMethods)
			throws IllegalCodeException;

	protected void handleDecleration(String line) throws IllegalCodeException {
		try {
			Member[] newMembers = MemberFactory.createMembers(line);
			for (Member newMember : newMembers) {
				localMembers.add(newMember);
			}
		} catch (NonValidValueException e) {
			for (Member knownMember : localMembers) {
				if (knownMember.name.equals(e.name)) {
					if ()
				}
			}
		}
	}
}