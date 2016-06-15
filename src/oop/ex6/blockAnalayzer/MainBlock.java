package oop.ex6.blockAnalayzer;

import java.nio.channels.IllegalBlockingModeException;
import java.util.LinkedList;

import oop.ex6.error.IllegalCodeException;
import oop.ex6.variables.Member;

public class MainBlock extends Block {
	// The default starting line number.
	private static final int DEFAULT_STARTING_LINE = 0;

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
			method.informMethod(knownMethods);
		}
	}

	@Override
	protected void handleMethodBlockDecleration(String[] content) throws IllegalCodeException {
		MethodBlock newMethod = BlockFactory.createMethodBlock(content, localMembers);
		knownMethods.add(newMethod);
		containedBlocks.add(newMethod);
	}

	@Override
	protected void handleNonMethodBlockDecleration(String[] content)
			throws IllegalCodeException {
		throw new IllegalBlockDeclerationScope(lineNumber)
	}

	@Override
	protected void handleMethodCall(String line) {
		
	}

	@Override
	protected void handleReturnStatement() throws IllegalCodeException {
		super.handleReturnStatement();
	}
}