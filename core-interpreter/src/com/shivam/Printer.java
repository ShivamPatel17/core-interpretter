package com.shivam;


/*
 * Helper class used for pretty printing a Core program
 */
public class Printer {
	/*
	 * prints tabs before printing the string
	 */
	public static void print(String s, int tabs) {
		while(tabs-->0)
			System.out.print('\t');
		System.out.print(s);
	}
	
	/*
	 * Just calls System.out.print, but keeps the code in other 
	 * classes pretty clean and convenient for adding tabs since 
	 * this is an overloaded method
	 */
	public static void print(String s) {
		System.out.print(s);
	}
}
