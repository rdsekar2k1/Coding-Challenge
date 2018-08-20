package exception;

/**
 * User Defined Global Exception 
 *
 */
public class ZIPCodeException extends Exception {

	private static final long serialVersionUID = 1L;

	public ZIPCodeException(String message){
		super(message);
	}
}