package com.shivam;

import com.shivam.NonTerminalClasses.*;

public class main {

	/* main method
	 * 
	 * parses, prints, and executes a program
	 */
	public static void main(String[] args) throws Exception {

		if(args.length<1) throw new Exception("Need to pass in the path to the file");
		
		//creates a program and then parses it
		Program program = new Program(args[0]);
		program.parseProgram();
		
		//prints the program
		program.printProgram();
		
		System.out.println("--------------------------program output-----------------------------------");
		
		
		//executes the program
		program.executeProgram();
		
		
		System.out.println("--------------------------End of Program-----------------------------------");
	}

}
