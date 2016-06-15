package oop.ex6.blockAnalayzer;

import java.util.LinkedList;

import oop.ex6.error.IllegalCodeException;

public class MainBlock extends Block {
	// The default starting line number.
	private static final int DEFAULT_STARTING_LINE = 0;

	/**
	 * Creates a main block type. Checks all it's content straight away.
	 * 
	 * @param codeLines
	 *            The whole code content.
	 */
	public MainBlock(String[] codeLines) {
		this.startingLine = DEFAULT_STARTING_LINE;
		this.type = BlockType.MAIN;
		this.content = codeLines;
		this.higherScopeMembers = null;
		this.containedBlocks = new LinkedList<Block>();
		this.knownMethods = new LinkedList<MethodBlock>();

	}

	@Override
	protected void shallowProcessing() throws IllegalCodeException {
		// Find all members.
		// declare all the methods.
	}

	@Override
	public void deepProcessing() throws IllegalCodeException {
		// declare all the methods.
	}

	@Override
	protected void handleMethodBlockDecleration(String[] content)
			throws IllegalCodeException {
		// TODO Auto-generated method stub
		super.handleMethodBlockDecleration(content);
	}

	@Override
	protected void handleNonMethodBlockDecleration(String[] content)
			throws IllegalCodeException {
		// TODO Auto-generated method stub
		super.handleNonMethodBlockDecleration(content);
	}

	@Override
	protected void handleMethodCall(String line) {
		// TODO Auto-generated method stub
		super.handleMethodCall(line);
	}

	@Override
	protected void handleReturnStatement() throws IllegalCodeException {
		super.handleReturnStatement();
	}
}