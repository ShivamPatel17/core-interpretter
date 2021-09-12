package com.shivam.NonTerminalClasses;

import com.shivam.Printer;
import com.shivam.CoreBNFNumberCheck.Check;

/*
 * Loop class for the <loop> NT statement 
 * 
 * implements the stmt interface
 */
public class Loop implements Stmt{
	
	private Cond cond;
	private StmtSeq ss;
	
	public Loop() {
		
	}

	@Override
	public void parseStmt() throws Exception {
		//check for while
		Check.checkThenSkipToken(8);
		//parse the condition
		cond = new Cond();
		cond.parseCond();
		
		//check for loop
		Check.checkThenSkipToken(9);
		//parse the statement sequence <stmt seq>
		ss = new StmtSeq();
		ss.parseStmtSeq();
		
		//check for end;
		Check.checkThenSkipToken(3);
		Check.checkThenSkipToken(12);
	}

	@Override
	public void printStmt(int tabs) throws Exception{
		Printer.print("while ", tabs);
		cond.printCond(0);
		Printer.print(" loop\n");
		ss.printStmtSeq(tabs+1);
		Printer.print("end;\n", tabs);
	}

	//executes the statement sequence in a loop while cond is true
	@Override
	public void executeStmt() throws Exception {
		while(cond.evalCond()) {
			ss.executeStmtSeq();
		}
	}

}
