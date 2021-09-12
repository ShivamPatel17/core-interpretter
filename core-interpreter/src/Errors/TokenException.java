package Errors;


/*
 * Class for token errors during parsing
 */
@SuppressWarnings("serial")
public class TokenException extends Exception {
	
	//used for token errors where a specific token is expected
	public TokenException(int tokenExpected, int tokenReceived) {
		super("Expecting token "+tokenExpected+ "but received token"+tokenReceived);
	}
	//used for a Token exception when there's multiple tokens that can be valid
	public TokenException(int [] tokens, int tokenReceived) {
		super(multiTokenErrorString(tokens, tokenReceived));
	}
	
	//helper method to create a message for token errors where multiple tokens could be valid
	private static String multiTokenErrorString(int tokens[], int tokenReceived) {
		StringBuilder sb  = new StringBuilder("Expecting tokens ");
		for(int i = 0; i < tokens.length-1; i++) {
			sb.append(tokens[i]);
			sb.append(", ");
		}
		sb.append(tokens[tokens.length-1]);
		sb.append(" but received ");
		sb.append(tokenReceived);
		return sb.toString();
	}
}
