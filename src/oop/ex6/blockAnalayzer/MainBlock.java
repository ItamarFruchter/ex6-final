package oop.ex6.blockAnalayzer;

import java.util.LinkedList;

import oop.ex6.general.IllegalCodeException;
import oop.ex6.variables.Member;

public class MainBlock extends Block {
	// The default starting line number.
	private static final int DEFAULT_STARTING_LINE = 1;

	/**
	 * Creates a main block type. Checks all it's content straight away.
	 * 
	 * @param codeLines
	 *            The whole code content.
	 * @throws IllegalCodeException
	 */
	public MainBlock(String[] codeLines) throws IllegalCodeException {
		this.startingLine = DEFAULT_STARTING_LINE;
		this.type = BlockType.MAIN;
		this.content = codeLines;
		this.localMembers = new LinkedList<Member>();
		this.higherScopeMembers = null;
		this.containedBlocks = new LinkedList<Block>();
		this.knownMethods = new LinkedList<MethodBlock>();

		shallowProcessing();
		informAllMethods();
		deepProcessing();
	}

	/*
	 * Informs all the methods and informs them of the existence of the others.
	 */
	private void informAllMethods() throws IllegalCodeException {
		for (MethodBlock method : knownMethods) {
			method.informMethod(knownMethods, localMembers);
		}
	}

	@Override
	protected void handleMethodBlockDecleration(String[] content,
			int blockStartingLine) throws IllegalCodeException {
		MethodBlock newMethod = BlockFactory.createMethodBlock(content,
				localMembers, knownMethods, blockStartingLine);
		knownMethods.add(newMethod);
		containedBlocks.add(newMethod);
	}

	@Override
	protected void handleNonMethodBlockDecleration(String[] content,
			int blockStartingLine) throws IllegalCodeException {
		throw new IllegalBlockDeclerationScopeException();
	}

	@Override
	protected void handleMethodCall(String line) throws IllegalCodeException {
		throw new IllegalMethodCallScopeException();
	}

	@Override
	protected void handleReturnStatement() throws IllegalCodeException {
		throw new ReturnStatementScopeException();
	}
}