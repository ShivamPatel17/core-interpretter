package com.shivam.NonTerminalClasses;

import com.shivam.Printer;
import com.shivam.CoreBNFNumberCheck.Check;

import Errors.CoreRuntimeException;

/* 
 * Out class for the <out> non terminal
 * 
 * implements the stmt interface
 */
public class Out implements Stmt {
	
	private IdList idList; 
	
	public Out() {
		
	}

	@Override
	public void parseStmt() throws Exception{
		//check for write
		Check.checkThenSkipToken(11);
		
		//make an idList with references to existing id objects
		idList = new IdList();
		idList.readIdList();
		
		//check for ;
		Check.checkThenSkipToken(12);
		
	}

	@Override
	public void printStmt(int tabs) throws Exception{
		Printer.print("write ", tabs);
		idList.printIdList();
		Printer.print(";\n");
		
	}

	//call idList.coreWriteId to print according to project guidelines
	@Override
	public void executeStmt() throws Exception {
		idList.coreWriteId();		
	}

}
