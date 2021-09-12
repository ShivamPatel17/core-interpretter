package Errors;

/*
 * class for throwing Runtime expection
 * 
 * namely trying to access uninitialized variables
 */
@SuppressWarnings("serial")
public class CoreRuntimeException extends Exception {
	
	public CoreRuntimeException(String msg) {
		super(msg);
	}
}
