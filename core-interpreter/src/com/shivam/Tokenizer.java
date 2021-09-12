package com.shivam;



import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/* Tokenizer Class
 * @author Shivam Patel
 *
 * the purpose of this class is to tokenize a program written in the language Core
 *
 * the 4 public methods are getToken(), skipToken(), idName(), intVal()
 *
 * see the class Main to create a tokenizer and call its methods
 */
public class Tokenizer {
	// reads the input file (a Core program)
	Scanner scanner;
	// used for getting token numbers according to the interpreterProjectPart1.pdf
	HashMap<String, Integer> tokenNums;
	// the following two sets contain all the unique whitespace and special
	// characters, respectively
	Set<Character> whitespace;
	Set<Character> specials;
	// this queue is used to print all previous tokens if/when getToken() encounters
	// an illegal token
	Queue<String> queue;
	// used to store the current like of characters. Populated with the scanner
	StringBuilder sb;
	// used to keep track of how long the current token is. Also used for
	// determining if getToken() has been
	// called on the current token already
	int currTokenLength = 0;
	// index of the cursor on the current line
	int cursor = 0;
	// flag that keeps track of whether whitespace or a special character is
	// required
	boolean whiteSpaceOrSpecialRequired = false;
	static Tokenizer _instance;
	

	
	public static Tokenizer instance() {
		if(_instance!=null) {
			return _instance;
		}
		return new Tokenizer("");
	}
	
	public static void initializeTokenizer(String path) {
		_instance = new Tokenizer(path);
	}
	/*
	 * Constructor
	 *
	 * pass in a valid input path to a file
	 */
	private Tokenizer(String inputPath) {
		try {
			File file = new File(inputPath);
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			System.out.println("input path not valid: " + inputPath);
			e.printStackTrace();
		}

		/*
		 * initialize all data structures
		 */
		tokenNums = new HashMap<String, Integer>();
		whitespace = new HashSet<Character>();
		specials = new HashSet<Character>();
		queue = new LinkedList<String>();
		sb = new StringBuilder();
		loadDataStructures();
	}

	/*
	 * for now, i think get Token should just find the length of the current token
	 * from the current cursor pos; then at the end, once it's computed the length,
	 * it should call the helper method to determine the int value of what to return
	 * 1-30 is fixed value 31 starts with digit 32 starts with UC Letter 33 is EOF
	 * and get token will handle that 0 is error
	 */
	public int getToken() throws Exception {
		if (currTokenLength >= 1) {
			// currTokenLength>=1 that means we have already got the length of the current
			// token and thus don't need
			// to recalculate or add to the queue
			return tokenId();
		}
		// if we are at the end of a line, clear the current sting by making a new
		// StringBuilder
		if (cursor == sb.length()) {
			sb = new StringBuilder();
		}
		// populate sb with the next non empty line
		if (sb.length() == 0) {
			while (scanner.hasNextLine() && sb.length() == 0) {
				sb = new StringBuilder(scanner.nextLine());
			}
			// if this is true, then we've hit EOF
			if (!scanner.hasNextLine() && sb.length() == 0)
				return 33;
			// since we've read a new line, this counts as seeing a whitespace
			whiteSpaceOrSpecialRequired = false;
			cursor = 0;

		}
		// sb should always have length > 0 by this point
		char firstChar = sb.charAt(cursor);
		currTokenLength = 1;
		// this will be the number returned by a call to this method
		int tokenId = -1;
		if (isLet(firstChar)) {

			// must be an Identifier
			if (isUC(firstChar)) {
				int idLen = 1;
				while (currTokenLength < 8 && cursor + currTokenLength < sb.length()
						&& isUC(sb.charAt(cursor + currTokenLength))) {
					currTokenLength++;
				}
				while (currTokenLength < 8 && cursor + currTokenLength < sb.length()
						&& isDig(sb.charAt(cursor + currTokenLength))) {
					currTokenLength++;
				}

			}
			// must be reserved word
			else if (isLC(firstChar)) {
				while (cursor + currTokenLength < sb.length() && isLC(sb.charAt(cursor + currTokenLength))) {
					currTokenLength++;
				}
			} else {
				// major error, not sure how we'd get to this point
				return -1;
			}
		}
		// must be a int
		else if (isDig(firstChar)) {
			while (currTokenLength < 8 && cursor + currTokenLength < sb.length()
					&& isDig(sb.charAt(cursor + currTokenLength))) {
				currTokenLength++;
			}
		} // this shoudl just skip over the white space on this call and make another call
			// when the cursor is at
			// the start of a non whitespace character
		else if (isWhiteSpace(firstChar)) {
			while (cursor + currTokenLength < sb.length() && isWhiteSpace(sb.charAt(cursor + currTokenLength))) {
				currTokenLength++;
			}
			cursor += currTokenLength;
			currTokenLength = 0;
			// whiteSpaceOrSpecialRequired needs to be false before and after this recursive
			// call to getToken()
			// because of the case when whitespace is true before the current getToken()
			// call and the recursive
			// getToken() call also sets whitespace to true. If the next token is a reserve
			// word for example, we need
			// to see whitespaceORspecial on the NEXT token, but this method checks for
			// whitespaceORspecial at the end
			// of the method but before whiteSpaceOrSpecial is supposed to be updated. Thus
			// if the recursive call
			// sets the boolean to true, but the current token doesn't have to be a special
			// character, this will
			// throw an incorrect error. This probably isn't the best implemenation for
			// requiring proper whitespace
			// or speical characters, but it's a pretty clean approach outside of this weird
			// situation.
			whiteSpaceOrSpecialRequired = false;
			tokenId = getToken();
			whiteSpaceOrSpecialRequired = false;
		}
		// handles special characters that are 2 characters long
		else if (isSpecial(firstChar)) {
			if (firstChar == '&') {
				if (cursor + 1 < sb.length() && sb.charAt(cursor + 1) == '&') {
					currTokenLength = 2;
				} else {
					// needs to be an & followed immediately by &
					return -1;
				}
			} else if (firstChar == '|') {
				if (cursor + 1 < sb.length() && sb.charAt(cursor + 1) == '|') {
					currTokenLength = 2;
				} else {
					// needs to be an | followed immediately by |
					return -1;
				}
			} else if (firstChar == '!') {
				if (cursor + 1 < sb.length() && sb.charAt(cursor + 1) == '=') {
					currTokenLength = 2;
				}
			} else if (firstChar == '=') {
				if (cursor + 1 < sb.length() && sb.charAt(cursor + 1) == '=') {
					currTokenLength = 2;
				}
			} else if (firstChar == '<' || firstChar == '>') {
				if (cursor + 1 < sb.length() && sb.charAt(cursor + 1) == '=') {
					currTokenLength = 2;
				}
			} else {
				// this is redundent, but just included this for the sake of clarity
				// if the token is a special token and doesn't hit any of the above conditionals
				// then the token is just a single special token
				// currTokenLength is 1 by default so nothing would've needed to happen
				currTokenLength = 1;
			}
		}
		// totally invalid character. Print all the nums from Q as error message
		else {
			while (!queue.isEmpty()) {
				System.out.print(queue.poll() + " ");
			}
			System.out.println("\nInvalid token found");
		}

		// no token should be length -1
		if (currTokenLength == -1) {
			System.out.println("Token error");
			return -1;
		}
		tokenId = tokenId();

		// token id [12,30] is a special token
		if (whiteSpaceOrSpecialRequired && !(tokenId >= 12 && tokenId <= 30)) {
			while (!queue.isEmpty()) {
				System.out.print(queue.poll() + " ");
			}
			throw new Exception("Expecting whitespace");
		}
		// set this variable to be true if it's not a special character
		whiteSpaceOrSpecialRequired = !(tokenId <= 30 && tokenId >= 12) ? true : false;
		queue.offer(Integer.toString(tokenId));
		return tokenId;

	}

	/*
	 * moves the cursor past the current token
	 */
	public void skipToken() throws Exception {
		int tokenVal = -1;
		// call get token so that the the current token gets added to the queue for
		// printing later when an error shows
		if (currTokenLength == 0) {
			tokenVal = getToken();
		}
		// moves cursor based on currTokenLength
		// getToken will handle the case where the cursor is at the end of the line
		cursor += currTokenLength;

		// allows the user to continuously call skipToken() at EOF without throwing an
		// error
		// the caller of tokenizer is responsible for handling when to stop
		if (tokenVal != 33) {
			currTokenLength = 0;
		}
	}

	/*
	 * returns the integer value of a token, if it's an integer (token id 31)
	 */
	public int intVal() {
		return Integer.parseInt(sb.subSequence(cursor, cursor + currTokenLength).toString());
	}

	/* returns the id name of a token */
	public String idName() {
		return sb.subSequence(cursor, cursor + currTokenLength).toString();
	}

	/*
	 * returns the appropriate token number based on the current cursor and
	 * currTokenLength values used by getToken()
	 */
	private int tokenId() {
		String token = sb.substring(cursor, cursor + currTokenLength);
		if (tokenNums.containsKey(token)) {
			return tokenNums.get(token);
		}
		char first = token.charAt(0);
		if (isDig(first)) {
			return 31;
		} else if (isLet(first) && isUC(first)) {
			return 32;
		}
		return -1;
	}

	/*
	 * Loads all the data structures appropriately
	 *
	 * tokenNums gets populated with tokens and their respective numbers as stated
	 * from the interpreterProjectPart1.pdf
	 *
	 * whitespace contains unique whitespace characters
	 *
	 * specials contains all unique special characters in the Core language
	 */
	private void loadDataStructures() {
		tokenNums.put("program", 1);
		tokenNums.put("begin", 2);
		tokenNums.put("end", 3);
		tokenNums.put("int", 4);
		tokenNums.put("if", 5);
		tokenNums.put("then", 6);
		tokenNums.put("else", 7);
		tokenNums.put("while", 8);
		tokenNums.put("loop", 9);
		tokenNums.put("read", 10);
		tokenNums.put("write", 11);
		tokenNums.put(";", 12);
		tokenNums.put(",", 13);
		tokenNums.put("=", 14);
		tokenNums.put("!", 15);
		tokenNums.put("[", 16);
		tokenNums.put("]", 17);
		tokenNums.put("&&", 18);
		tokenNums.put("||", 19);
		tokenNums.put("(", 20);
		tokenNums.put(")", 21);
		tokenNums.put("+", 22);
		tokenNums.put("-", 23);
		tokenNums.put("*", 24);
		tokenNums.put("!=", 25);
		tokenNums.put("==", 26);
		tokenNums.put("<", 27);
		tokenNums.put(">", 28);
		tokenNums.put("<=", 29);
		tokenNums.put(">=", 30);
		whitespace.add(' ');
		whitespace.add('\n');
		whitespace.add('\t');
		whitespace.add('\r');
		specials.add(';');
		specials.add(',');
		specials.add('=');
		specials.add('!');
		specials.add('[');
		specials.add(']');
		specials.add('&');
		specials.add('|');
		specials.add('(');
		specials.add(')');
		specials.add('+');
		specials.add('-');
		specials.add('*');
		specials.add('<');
		specials.add('>');
	}

	/*
	 * All of the methods below are just for convenience they're also all pretty
	 * self explanatory and only 1 line
	 *
	 * I created them so i would have to type less characters and makes my other
	 * methods easier to read
	 */
	private boolean isSpecial(char c) {
		return specials.contains(c);
	}

	private boolean isWhiteSpace(char c) {
		return whitespace.contains(c);
	}

	private boolean isLet(char c) {
		return Character.isLetter(c);
	}

	private boolean isDig(char c) {
		return Character.isDigit(c);
	}

	private boolean isUC(char c) {
		return Character.isUpperCase(c);
	}

	private boolean isLC(char c) {
		return Character.isLowerCase(c);
	}

}
