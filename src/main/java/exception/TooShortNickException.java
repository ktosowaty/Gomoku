package exception;

import java.util.logging.Logger;

public class TooShortNickException extends Exception {

    private static final Logger LOGGER = Logger.getLogger(TooShortNickException.class.getName());

    public TooShortNickException() {
        super();
    }

    public TooShortNickException(String message) {
        super(message);
    }
}
