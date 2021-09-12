package com.shivam.NonTerminalClasses;

import com.shivam.Printer;
import com.shivam.CoreBNFNumberCheck.Check;

import Errors.CoreRuntimeException;
import Errors.ParseException;


/*
 * Compare class for the <comp> non-terminal
 */
public class Comp {
	
	private Op op1;
	private CompOp compOp;
	private Op op2;
	
	public Comp() {
		
	}
	
	public void parseComp() throws Exception {
		//check for (
		Check.checkThenSkipToken(20);
		
		//parse the first op
		op1 = new Op();
		op1.parseOp();
		
		//parse the compOp symbol
		compOp = new CompOp();
		compOp.parseCompOp();
		
		//parse the second op
		op2 = new Op();
		op2.parseOp();
		
		//check for )
		Check.checkThenSkipToken(21);
		
	}
	
	public void printComp() throws Exception {
		Printer.print("(");
		op1.printOp();
		compOp.printCompOp();
		op2.printOp();
		Printer.print(")");
	}
	
	/*
	 * returns the value of the <comp> as a boolean
	 */
	public boolean evalComp() throws Exception {
		
		//get the two values
		int val1 = op1.getVal();
		int val2 = op2.getVal();
		
		//perform the comparison based on the <comp op> symboll
		int compop = compOp.getAltNo();
		switch(compop) {
			case 1:
				return val1 != val2;
			case 2:
				return val1 == val2;
			case 3:
				return  val1 < val2;
			case 4: 
				return val1 > val2;
			case 5:
				return val1 <= val2;
			case 6: 
				return val1 >= val2;
		}
		throw new CoreRuntimeException("evalComp() could not be evaluated");
	}
}
