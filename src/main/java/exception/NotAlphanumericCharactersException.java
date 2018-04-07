package exception;

import java.util.logging.Logger;

public class NotAlphanumericCharactersException extends Exception {

    private static final Logger LOGGER = Logger.getLogger(NotAlphanumericCharactersException.class.getName());

    public NotAlphanumericCharactersException() {
        super();
    }

    public NotAlphanumericCharactersException(String message) {
        super(message);
    }
}
