package com.shivam.NonTerminalClasses;

/* 
 * Statement interface for <stmt> non terminals. We don't need a whole class for Stmt because
 * a statement is either an <assign>, <if>, <out>, <in> or <loop>
 * 
 * so a statment class would just have a reference to one of these 5 classes who all need the same funcitons, namely 
 * the parse, print, and execute statements. So instead, we can take advance of the IS-A relationship with this interface
 */
interface Stmt{
	
	public void parseStmt() throws Exception;
	
	public void printStmt(int tabs) throws Exception;
	
	public void executeStmt() throws Exception;

}
