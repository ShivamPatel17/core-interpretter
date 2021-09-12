package com.shivam.NonTerminalClasses;

import com.shivam.Printer;
import com.shivam.Tokenizer;
import com.shivam.CoreBNFNumberCheck.Check;

/*
 * Program class for the <prog> non-terminal
 */
public class Program {
	
	private DeclSeq ds;
	private StmtSeq ss;
	
	public Program(String path) {
		//initialze the tokenizer. This will be the only time it's initialized
		Tokenizer.initializeTokenizer(path);
	}
	
	public void parseProgram() throws Exception {
		//check for prog
		Check.checkThenSkipToken(1);
		
		//parse the declaration sequence
		ds = new DeclSeq();
		ds.parseDeclSeq();
		
		//check for begin
		Check.checkThenSkipToken(2);
		
		//parse the statement sequence
		ss = new StmtSeq();
		ss.parseStmtSeq();
		
		//check for end
		Check.checkThenSkipToken(3);
	}
	
	public void printProgram() throws Exception {
		Printer.print("program\n");
		ds.printDeclSeq(1);
		Printer.print("begin\n");
		ss.printStmtSeq(1);
		Printer.print("end\n");
	}
	
	public void executeProgram() throws Exception {
		//just execute statement sequence. Declaration sequence doesn't need to be executed
		ss.executeStmtSeq();
	}
	
}
