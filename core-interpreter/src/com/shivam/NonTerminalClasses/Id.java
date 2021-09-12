package com.shivam.NonTerminalClasses;

import java.util.HashMap;
import java.util.Map;

import com.shivam.Tokenizer;
import com.shivam.CoreBNFNumberCheck.Check;

import Errors.CoreRuntimeException;
import Errors.ParseException;

/*
 * Id class for the <id> non-terminal
 * 
 * this class contains a static reference for all existing Ids
 */
public class Id{

	//map of all Ids. Since ids are unique by their name, that's the key for this map
	static Map<String, Id> eids = new HashMap<String, Id>();
	
	//private variables for <id> objects
	private String name;
	private int val = -1;
	private boolean initialized = false;
	
	
	public Id() {
		
	}
	//this method parses an id and will only be called by idLists in the <decl> NT
	public void parseId() throws Exception{
		//ensure the next Token is an Id token
		Check.ensureTokenNum(32);
		String id = Tokenizer.instance().idName();
		
		//if the id already exists, throw an exception
		if(eids.containsKey(id)) {
			throw new ParseException(id+" has already been declared");
		}
		//otherwise, set the name and add the id to the static eids map
		name = id;
		eids.put(name, this);
		Tokenizer.instance().skipToken();

	}
	
	/*
	 * this method returns a reference to an existing id
	 * will be used be idList when reading or writing <id>s
	 */
	public static Id getId(String name) throws Exception{
		if(!eids.containsKey(name)) {
			throw new ParseException(name+" has not been declared");
		}
		
		return eids.get(name);
	}
	
	//prints the name of an id object
	public void printId() {
		System.out.print(name);
	}
	
	//sets the val of an id object and sets initialized to true
	public void setIdVal(int val) {
		this.initialized = true;
		this.val = val;
	}
	//gets the value for an id object
	public int getVal() throws Exception{
		if(!initialized) throw new CoreRuntimeException(name+ " has not been initialized (get val)");
		return val;		
	}
	//used by idList when there's an <out> NT which will print the value of an Id
	public void coreWrite() throws CoreRuntimeException {
		if(!initialized) throw new CoreRuntimeException(name+ " has not been initialized (core print)");
		System.out.println(name+"="+val);
	}
	
	//returns the name of an id object
	public String getName() {
		return name; 
	}
	
}
