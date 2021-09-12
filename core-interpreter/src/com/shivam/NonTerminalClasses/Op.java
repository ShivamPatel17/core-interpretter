package com.shivam.NonTerminalClasses;

import com.shivam.Printer;
import com.shivam.Tokenizer;
import com.shivam.CoreBNFNumberCheck.Check;

import Errors.ParseException;
import Errors.TokenException;

/*
 * Op class for the <op> non terminal
 */
public class Op {
	
	private Id id;
	private int val;
	private Exp exp;
	
	
	public Op() {
		
	}
	
	public void parseOp() throws Exception {
		int tokenNum = Tokenizer.instance().getToken();
		//check if the next token is an id, int, or another expression
		int[] expectedTokens = {20, 31, 32};
		if(!(tokenNum==31 || tokenNum==32 || tokenNum ==20)) throw new TokenException(expectedTokens, tokenNum);
		
		//if its an id, find the existing id object and set it to this.id
		if(tokenNum==32) {
			id = Id.getId(Tokenizer.instance().idName());
			Tokenizer.instance().skipToken();
		}
		//if it is an int, just get the value of it from the tokenizer
		else if(tokenNum==31) {
			val = Tokenizer.instance().intVal();
			Tokenizer.instance().skipToken();
		}
		//if it's an ( then parse the expression
		else if(tokenNum==20) {
			Tokenizer.instance().skipToken();
			exp = new Exp();
			exp.parseExp();
			Check.checkThenSkipToken(21);
		}
		
	}
	
	public void printOp() throws Exception {
		if(id!=null) {
			id.printId();
		}
		else if(exp!=null) {
			Printer.print("(");
			exp.printExp();
			Printer.print(")");
		}
		else{
			System.out.print(val);
		}
	}
	
	//get the value based on the  alternative that we are in
	public int getVal() throws Exception {
		int val;
		if(id!=null) val = id.getVal();
		else if(exp!=null) val = exp.getVal();
		else val = this.val;
		return val;
	}
}
