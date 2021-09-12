package com.shivam.NonTerminalClasses;

import com.shivam.Printer;
import com.shivam.CoreBNFNumberCheck.Check;

/* 
 * Declaration class for the <decl> non-terminal
 */
public class Decl {
	
	private IdList idList;
	
	public Decl() {
		
	}
	
	public void parseDecl() throws Exception {
		//check for int
		Check.checkThenSkipToken(4);
		
		//parse idlist
		idList = new IdList();
		idList.parseIdList();
		
		//check for ;
		Check.checkThenSkipToken(12);
	}
	
	public void printDecl(int tabs) {
		Printer.print("int ", tabs);
		idList.printIdList();
		Printer.print(";\n");
	}
}
