package com.shivam.NonTerminalClasses;

import com.shivam.Printer;
import com.shivam.Tokenizer;
import com.shivam.CoreBNFNumberCheck.Check;
/* 
 * If class for the <if> NT statement
 * 
 * implements the stmt interface
 */
public class If implements Stmt{
	
	private Cond cond; 
	private StmtSeq ss1;
	private StmtSeq ss2;
	
	public If() {
		
	}

	@Override
	public void parseStmt() throws Exception{
		//check for if
		Check.checkThenSkipToken(5);
		
		
		//parse the condition
		cond = new Cond();
		cond.parseCond();
		
		//check for then
		Check.checkThenSkipToken(6);
		
		//parse the statement sequence
		ss1 = new StmtSeq();
		ss1.parseStmtSeq();
		
		//check for the else keyword, if so, skip it and parse the next statement sequence
		if(Check.checkToken(7)) {
			Tokenizer.instance().skipToken();
			ss2 = new StmtSeq();
			ss2.parseStmtSeq();
		}
		
		//check for end;
		Check.checkThenSkipToken(3);
		Check.checkThenSkipToken(12);
		
	}

	@Override
	public void printStmt(int tabs) throws Exception {
		Printer.print("if ", tabs);
		cond.printCond(0);
		Printer.print(" then\n");
		ss1.printStmtSeq(tabs+1);
		if(ss2!=null) {
			Printer.print("else\n", tabs);
			ss2.printStmtSeq(tabs+1);
		}
		
		Printer.print("end;\n",tabs);
	}

	
	//execute the first statement sequence if cond is true, otherwise execute the second
	//if it's not null (meaning we are dealing with the 2nd alternative)
	@Override
	public void executeStmt() throws Exception {
		if(cond.evalCond()) {
			ss1.executeStmtSeq();
		}else if(ss2!=null) {
			ss2.executeStmtSeq();
		}
		
	}

}