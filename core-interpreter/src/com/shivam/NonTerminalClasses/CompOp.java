package com.shivam.NonTerminalClasses;

import com.shivam.Printer;
import com.shivam.Tokenizer;

import Errors.TokenException;


/*
 * Comp op class for the <comp op> non-terminal
 */
public class CompOp {
	
	private int altNo;

	public CompOp() {
		
	}
	
	public void parseCompOp() throws Exception {
		//make sure the token is a valid terminal symbol for the <comp op> NT
		int tokenNum = Tokenizer.instance().getToken();
		if((!(tokenNum>=25 && tokenNum<=30))) {
			int [] expectedTokens= {25, 26, 27, 28, 29, 30};
			throw new TokenException(expectedTokens, tokenNum);
		}
		Tokenizer.instance().skipToken();
		
		//shift the range of the tokens from 25-30 to 1-6
		altNo = tokenNum-24;
	}
	
	public void printCompOp() {
		switch(altNo) {
			case 1:
				Printer.print(" != ");
				break;
			case 2:
				Printer.print(" == ");
				break;
			case 3:
				Printer.print(" < ");
				break;
			case 4:
				Printer.print(" > ");
				break;
			case 5:
				Printer.print(" <= ");
				break;
			case 6:
				Printer.print(" >= ");
				break;
		}
		
	}

	
	//returns the altNo which represents a terminal symbol for comparison
	public int getAltNo() {
		return altNo;
	}
}
