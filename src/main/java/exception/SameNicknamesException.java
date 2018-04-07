package exception;

import java.util.logging.Logger;

public class SameNicknamesException extends Exception {

    private static final Logger LOGGER = Logger.getLogger(SameNicknamesException.class.getName());

    public SameNicknamesException() {
        super();
    }

    public SameNicknamesException(String message) {
        super(message);
    }
}
