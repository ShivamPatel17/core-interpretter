package com.shivam.NonTerminalClasses;

import com.shivam.Tokenizer;

/*
 * Expression class for the <exp> non-terminal
 */
public class Exp {
	
	private Fac fac;
	private Exp exp;
	private char sign;
	
	public Exp() {
		sign='.';
	}
	
	public void parseExp() throws Exception {
		//parse <fac>
		fac = new Fac();
		fac.parseFac();
		
		//if the next token is a + or a -, parse the expression
		//set the value of sign accordingly
		if(Tokenizer.instance().getToken()==22) {
			Tokenizer.instance().skipToken();
			sign = '+';
			exp = new Exp();
			exp.parseExp();
		}else if(Tokenizer.instance().getToken()==23) {
			Tokenizer.instance().skipToken();
			sign = '-';
			exp = new Exp();
			exp.parseExp();
		}
	}
	
	public void printExp() throws Exception {
		fac.printFac();
		if(sign!='.') {
			System.out.print(" "+sign+" ");
			exp.printExp();
		}
	}

	//get the value of this expression
	public int getVal() throws Exception {
		int facVal = fac.getVal();
		
		//if sign is + or -, add or subtract to facVal accordingly
		if(sign=='+') {
			facVal+=exp.getVal();
		}else if(sign=='-') {
			facVal-=exp.getVal();
		}
		return facVal;
	}
}
