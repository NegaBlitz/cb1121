package exceptions;

public class InvalidUserInputException extends Exception{
    public InvalidUserInputException(String errorMessage) {
        super(errorMessage);
    }
}
