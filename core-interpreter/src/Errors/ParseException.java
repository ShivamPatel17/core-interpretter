package Errors;

/*
 * class for context-sensitive errors such as double declarations an assigning a value 
 * to an undeclared <id>
 */
@SuppressWarnings("serial")
public class ParseException extends Exception {
	public ParseException(String msg) {
		super(msg);
	}
}
