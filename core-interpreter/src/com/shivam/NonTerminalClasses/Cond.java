package com.shivam.NonTerminalClasses;

import com.shivam.Printer;
import com.shivam.Tokenizer;
import com.shivam.CoreBNFNumberCheck.Check;

import Errors.CoreRuntimeException;
import Errors.ParseException;
import Errors.TokenException;

/* 
 * Condition class for the <cond> non-terminal
 */
public class Cond {

	private Comp comp;
	private Cond cond1;
	private Cond cond2;
	private int altNo;
	
	public Cond() {
		
	}
	
	public void parseCond() throws Exception {
		int tokenNum = Tokenizer.instance().getToken();
		
		//based on the token num, decide which alternative in the production we're dealing with
		
		
		// ( is for the <comp> alternative
		if(tokenNum==20) {
			altNo = 1;
			comp = new Comp();
			comp.parseComp();
		}
		// ! is for !<cond>
		else if(tokenNum==15) {
			altNo = 2;
			Tokenizer.instance().skipToken();
			cond1 = new Cond();
			cond1.parseCond();
		}
		// [ is for either the <cond> && <cond> 
		// or it is for the <cond> || <cond>
		else if(tokenNum==16){
			Tokenizer.instance().skipToken();
			cond1 = new Cond();
			cond1.parseCond();
			
			//get the next token. Expecting either && or ||
			int comparisonToken = Tokenizer.instance().getToken();
			
			//make sure the comparisonToken is 18 or 19
			if(comparisonToken == 18 || comparisonToken == 19) {
				//18 is for && and 19 is for ||
				//thus altNo 3 is for the 3rd alternative using && while altNo 4 is for the 4th alternative using ||
				altNo = comparisonToken==19 ? 4 : 3;
				Tokenizer.instance().skipToken();
				cond2 = new Cond();
				cond2.parseCond();
				Check.checkThenSkipToken(17);
			}else {
				int [] expectedTokens = {18, 19};
				throw new TokenException(expectedTokens, comparisonToken);
			}
			
		}else {
			int [] expectedTokens = {15, 16, 20};
			throw new TokenException(expectedTokens, tokenNum);
		}
		
	}
	
	public void printCond(int tabs) throws Exception {
		if(altNo==1) {
			comp.printComp();
		}else if(altNo==2) {
			Printer.print("!", tabs);
			cond1.printCond(0);
		}else if(altNo==3) {
			Printer.print("[");
			cond1.printCond(tabs);
			Printer.print(" && ");
			cond2.printCond(tabs);
			Printer.print("]");
		}else if(altNo==4) {
			Printer.print("[");
			cond1.printCond(tabs);
			Printer.print(" || ");
			cond2.printCond(tabs);
			Printer.print("]");
		}
	}
	
	//evaluate condition based on the altNo
	public boolean evalCond() throws Exception {
		if(altNo == 1){
			return comp.evalComp();
		}else if(altNo == 2) {
			return !cond1.evalCond();
		}else if(altNo == 3) {
			return cond1.evalCond() && cond2.evalCond();
		}else if(altNo == 4) {
			return cond1.evalCond() || cond2.evalCond();
		}else {
			throw new CoreRuntimeException("Error in evalCond()");
		}
	}
}
