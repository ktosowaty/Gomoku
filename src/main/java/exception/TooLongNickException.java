package exception;

import java.util.logging.Logger;

public class TooLongNickException extends Exception {

    private static final Logger LOGGER = Logger.getLogger(TooLongNickException.class.getName());

    public TooLongNickException() {
        super();
    }

    public TooLongNickException(String message) {
        super(message);
    }
}
