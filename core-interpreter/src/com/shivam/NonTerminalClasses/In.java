package com.shivam.NonTerminalClasses;

import java.util.Scanner;

import com.shivam.Printer;
import com.shivam.CoreBNFNumberCheck.Check;

/*
 * In class for the <in> statement NT
 * 
 * implements the stmt interface
 */
public class In implements Stmt{
	
	private IdList idList;
	
	public In() {
		
	}

	@Override
	public void parseStmt() throws Exception {
		//check for read
		Check.checkThenSkipToken(10);
		
		//create an idlist that references existing id objects
		idList = new IdList();
		idList.readIdList();
		
		//check for ;
		Check.checkThenSkipToken(12);
	}

	@Override
	public void printStmt(int tabs) throws Exception{
		Printer.print("read ", tabs);
		idList.printIdList();
		Printer.print(";\n");
	}

	//calls the coreReadId method for idList to read in and assign values
	@Override
	public void executeStmt() throws Exception{
		Scanner sc = new Scanner(System.in);
		idList.coreReadId(sc);
	}

}
