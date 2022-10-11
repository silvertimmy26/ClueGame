package clueGame;

public class BadConfigFormatException extends Exception {
	
	public BadConfigFormatException() {
		super("Error with configuration formats");
	}
	
	public BadConfigFormatException(String s) {
		super(s);
	}

}
