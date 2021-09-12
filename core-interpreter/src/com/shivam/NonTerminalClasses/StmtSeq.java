package com.shivam.NonTerminalClasses;

import com.shivam.Printer;
import com.shivam.Tokenizer;

import Errors.ParseException;

/*
 * Statement Sequence class for the <stmt seq> non terminal
 */
public class StmtSeq {
	
	private Stmt stmt;
	private StmtSeq ss;
	
	public StmtSeq() {
		
	}
	
	public void parseStmtSeq() throws ParseException, Exception {
		//a statment can be an if, out, in, loop, or assign. Based on the tokenNu, we can know for sure and initialzed 
		//our statement object with one of the implementing classes
		int tokenNum = Tokenizer.instance().getToken();
		if(!isStmtTokenNum(tokenNum)) throw new ParseException("Expecting Token 33 or 5 or 8 or 10 or 11 but received token "+tokenNum);
		else if(tokenNum==32){
			stmt = new Assign();
		}
		else if(tokenNum==5) {
			stmt = new If();
		}
		else if(tokenNum==8) {
			stmt = new Loop();
		}
		else if(tokenNum==10) {
			stmt = new In();
		}
		else if(tokenNum==11) {
			stmt = new Out();
		}
		else{
			throw new ParseException("Parse error in parseStmtSeq()");
		}
		//will call the implementing class's appropriate parseStmt
		stmt.parseStmt();
		
		
		//if there's another statement token, parse the next statement sequence
		if(isStmtTokenNum(Tokenizer.instance().getToken())) {
			ss = new StmtSeq();
			ss.parseStmtSeq();
		}
		
	}
	
	public void printStmtSeq(int tabs) throws Exception {
		if(stmt!=null) {
			stmt.printStmt(tabs);
		}
		
		if(ss!=null) {
			ss.printStmtSeq(tabs);
		}
	}
	
	//just call the implementing calls execute method
	public void executeStmtSeq() throws Exception {
		stmt.executeStmt();
		if(ss!=null) {
			ss.executeStmtSeq();
		}
	}
	
	public boolean isStmtTokenNum(int num) {
		//num == <id> | if | while | read | write
		return(num==32 || num==5 || num ==8 || num==10 || num==11);
	}
	
	
}
