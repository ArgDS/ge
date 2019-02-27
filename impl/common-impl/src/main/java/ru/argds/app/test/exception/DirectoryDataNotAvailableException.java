package ru.argds.app.test.exception;

public class DirectoryDataNotAvailableException extends Exception {

    public DirectoryDataNotAvailableException() {
    }

    public DirectoryDataNotAvailableException(Throwable cause) {
        super(cause);
    }

    public DirectoryDataNotAvailableException(String message) {
        super(message);
    }

    public DirectoryDataNotAvailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
