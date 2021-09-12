package com.shivam.CoreBNFNumberCheck;

import com.shivam.Tokenizer;

import Errors.TokenException;


/*
 * Helper class for common procedures used for parseing
 */
public class Check {
	/* 
	 * checks for a token number and skips if its found
	 * 
	 * used primarily for keyword checking. i.e program, begin, end, ;, etc
	 */
	public static void checkThenSkipToken(int tokenNum) throws Exception{
		if(Tokenizer.instance().getToken() != tokenNum) throw new TokenException(tokenNum, Tokenizer.instance().getToken());
		Tokenizer.instance().skipToken();
	}
	
	/*
	 * Checks if the next token is the same as the tokenNum parameter
	 * 
	 * used primarily for checking which alternative a non-terminal is producing
	 */
	public static boolean checkToken(int tokenNum) throws Exception {
		if(Tokenizer.instance().getToken() == tokenNum) return true;
		else return false;
	}
	
	/* Checks to ensure the next token is the same as the tokenNum parameter
	 * 
	 * used primarily for ensuring the next token is an Id or an int
	 */
	public static void ensureTokenNum(int tokenNum) throws Exception {
		if(Tokenizer.instance().getToken() != tokenNum) throw new TokenException(tokenNum, Tokenizer.instance().getToken());
	}
}
