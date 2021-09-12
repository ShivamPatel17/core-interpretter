package com.shivam.NonTerminalClasses;

import com.shivam.Tokenizer;

import Errors.TokenException;

public class Repeat {
	
	private StmtSeq SS;
	private Cond cond;
	
	//constructor
	public Repeat() {
		
	}
	
	//parse method
	public void parse() throws Exception {
		//check for the repeat token, let's assume its token number 34
		if(Tokenizer.instance().getToken() != 34) throw new Exception("Expecting token 34");
		Tokenizer.instance().skipToken();
		
		//now we parse the statement sequence using the recursive descent approach
		SS = new StmtSeq();
		SS.parseStmtSeq();
		
		//check for the until token, let's assume its token number 35
		if(Tokenizer.instance().getToken() != 35) throw new Exception("Expecting token 35");
		Tokenizer.instance().skipToken();
		
		//finally lets parse Condition using recursive descent again
		cond = new Cond();
		cond.parseCond();
		
		//check for Semi Colon
		if(Tokenizer.instance().getToken() != 12) throw new Exception("Expecting token 12");
		Tokenizer.instance().skipToken();
				
	}
	
	//print method
	public void print(int tabs) throws Exception{
		System.out.print("repeat ");
		SS.printStmtSeq(tabs);
		System.out.print(" until ");
		cond.printCond(tabs);
		System.out.println(";");
	}
}
