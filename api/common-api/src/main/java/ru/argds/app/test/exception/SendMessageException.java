package ru.argds.app.test.exception;

public class SendMessageException extends Exception {

    public SendMessageException(String message) {
        super(message);
    }

    public SendMessageException(Throwable cause) {
        super(cause);
    }

    public SendMessageException(String message, Throwable cause) {
        super(message, cause);
    }
}
