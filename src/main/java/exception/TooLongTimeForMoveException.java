package exception;

import java.util.logging.Logger;

public class TooLongTimeForMoveException extends Exception {

    private static final Logger LOGGER = Logger.getLogger(TooLongTimeForMoveException.class.getName());

    public TooLongTimeForMoveException() {
        super();
    }

    public TooLongTimeForMoveException(String message) {
        super(message);
    }
}
