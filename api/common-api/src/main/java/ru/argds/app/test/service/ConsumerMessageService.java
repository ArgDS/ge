package ru.argds.app.test.service;

import ru.argds.app.test.Message;
import ru.argds.app.test.exception.TakeMessageException;

public interface ConsumerMessageService {

    Message take() throws TakeMessageException;
    void shutdown();
}
