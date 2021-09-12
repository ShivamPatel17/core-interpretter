package com.shivam.NonTerminalClasses;

import com.shivam.Tokenizer;

/*
 * Declaration Sequence class for the <decl seq> non-terminal
 */
public class DeclSeq {
	
	private Decl decl;
	private DeclSeq ds;
	
	public DeclSeq() {
		
	}
	
	public void parseDeclSeq() throws Exception {
		//parse the first <decl>
		decl = new Decl();
		decl.parseDecl();
		
		//if there's a comma, parse the next <declaration sequence>
		//i.e we are in the 2nd alternative
		if(Tokenizer.instance().getToken() == 4) {
			ds = new DeclSeq();
			ds.parseDeclSeq();
		}
	}
	
	public void printDeclSeq(int tabs) {
		decl.printDecl(tabs);
		if(ds!=null)
			ds.printDeclSeq(tabs);
	}
	
}
