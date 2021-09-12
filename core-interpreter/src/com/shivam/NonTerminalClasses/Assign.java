package com.shivam.NonTerminalClasses;

import com.shivam.Printer;
import com.shivam.Tokenizer;
import com.shivam.CoreBNFNumberCheck.Check;


/*
 *  Assign class for the <assign> non-terminal 
 *  implements the Stmt interface
 */
public class Assign implements Stmt{
	
	private Id id; 
	private Exp exp;
	
	public Assign() {
		
	}

	public void parseStmt() throws Exception {
		//check for id
		Check.ensureTokenNum(32);
		//get the id
		id = Id.getId(Tokenizer.instance().idName());
		Tokenizer.instance().skipToken();
		
		//skip the =
		Check.checkThenSkipToken(14);
		
		//parse expression
		exp = new Exp();
		exp.parseExp();
		
		//skip the ;
		Check.checkThenSkipToken(12);
	}

	@Override
	public void printStmt(int tabs) throws Exception {
		Printer.print("", tabs);
		id.printId();
		Printer.print(" = ");
		exp.printExp();
		Printer.print(";\n");
	}

	@Override
	public void executeStmt() throws Exception {
		//asign the value of expression to the id
		id.setIdVal(exp.getVal());
	}
	
}
