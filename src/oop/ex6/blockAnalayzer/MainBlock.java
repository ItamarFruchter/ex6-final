package oop.ex6.blockAnalayzer;

import java.util.LinkedList;

public class MainBlock extends Block {

	public MainBlock(String[] codeLines) {
		this.type = BlockType.MAIN;
		this.content = codeLines;
		this.higherScopeMembers = null;
		this.containedBlocks = new LinkedList<Block>();
		this.knownMethods = new LinkedList<MethodBlock>();
		
	}
	
	

}
