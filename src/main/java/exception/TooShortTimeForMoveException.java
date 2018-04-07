package exception;

import java.util.logging.Logger;

public class TooShortTimeForMoveException extends Exception {

    private static final Logger LOGGER = Logger.getLogger(TooShortTimeForMoveException.class.getName());

    public TooShortTimeForMoveException() {
        super();
    }

    public TooShortTimeForMoveException(String message) {
        super(message);
    }
}
