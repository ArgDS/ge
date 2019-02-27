package ru.argds.app.test.service;

import ru.argds.app.test.Message;
import ru.argds.app.test.exception.SendMessageException;

public interface MessageService {
    void send(Message msg) throws SendMessageException;
}
