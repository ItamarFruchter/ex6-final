package oop.ex6.blockAnalayzer;

public class MainBlock extends Block {

	public MainBlock(String[] codeLines) {
		this.type = BlockType.MAIN;
		this.content = codeLines;
		this.higherScopeMembers = null;
		
	}

}
