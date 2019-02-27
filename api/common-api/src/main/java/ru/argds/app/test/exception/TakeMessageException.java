package ru.argds.app.test.exception;

public class TakeMessageException extends Exception {

    public TakeMessageException() {
    }

    public TakeMessageException(String message) {
        super(message);
    }

    public TakeMessageException(String message, Throwable cause) {
        super(message, cause);
    }

    public TakeMessageException(Throwable cause) {
        super(cause);
    }
}
