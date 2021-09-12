package com.shivam.NonTerminalClasses;

import java.util.Scanner;

import com.shivam.Printer;
import com.shivam.Tokenizer;
import com.shivam.CoreBNFNumberCheck.Check;

import Errors.CoreRuntimeException;

/* 
 * ID list class for the <id list> non terminal
 */
public class IdList {
	
	private Id id;
	private IdList idList;
	
	public IdList() {
		
	}
	
	//will only be called in the context of a <decl seq>
	public void parseIdList() throws Exception {
		//parse an id. The id class will handle the context free requirement: double declaration
		id = new Id();
		id.parseId();
		//if the next token is a , (comma) create an parse a new idlist
		if(Check.checkToken(13)){
			Tokenizer.instance().skipToken();
			idList = new IdList();
			idList.parseIdList();
		}
	}
	
	//used in the context of <out> and <in> to set this idList's id variable to existing id objects
	public void readIdList() throws Exception {
		Check.ensureTokenNum(32);
		id = Id.getId(Tokenizer.instance().idName());
		Tokenizer.instance().skipToken();
		if(Check.checkToken(13)) {
			Tokenizer.instance().skipToken();
			idList = new IdList();
			idList.readIdList();
		}
	}
	
	public void printIdList() {
		id.printId();
		if(idList!=null) {
			System.out.print(", ");
			idList.printIdList();
		}
		
	}
	
	//used for print the <out> output
	public void coreWriteId() throws CoreRuntimeException {
		id.coreWrite();
		if(idList!=null) {
			idList.coreWriteId();
		}
	}
	
	//used for reading in and assigning values for the <in> NT
	public void coreReadId(Scanner sc) throws Exception{
		Printer.print("Enter integer for "+id.getName()+" ");

		int val = sc.nextInt();
		
		id.setIdVal(val);
		//Printer.print("\n");
		if(idList!=null) {
			idList.coreReadId(sc);
		}
	}
	
}
