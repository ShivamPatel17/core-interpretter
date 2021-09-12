package com.shivam.NonTerminalClasses;

import com.shivam.Printer;
import com.shivam.Tokenizer;
import com.shivam.CoreBNFNumberCheck.Check;

/* 
 * Factor class for the <fac> non-terminal
 */
public class Fac {
	
	private Op op;
	private Fac fac;
	
	public Fac() {
		
	}
	
	public void parseFac() throws Exception{
		//parse <op>
		op = new Op();
		op.parseOp();
		
		//if the next token is * then we are in the 2nd alternative and need to parse <fac>
		if(Check.checkToken(24)) {
			Tokenizer.instance().skipToken();
			fac = new Fac();
			fac.parseFac();
		}
	}
	
	public void printFac() throws Exception {
		op.printOp();
		if(fac!=null) {
			Printer.print(" * ");
			fac.printFac();
		}
	}

	//return the value of this <fac>
	public int getVal() throws Exception {
		int opVal = op.getVal();
		//if fac != null, then we need to multiply the opVal with facVal
		if(fac!=null) {
			opVal*=fac.getVal();
		}
		return opVal;
	}
}
