package exceptions;

public class NoSuchFileManagerException extends RuntimeException {
    public NoSuchFileManagerException(String message) {
        super(message);
    }
}
