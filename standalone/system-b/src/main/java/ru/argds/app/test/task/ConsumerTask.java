package ru.argds.app.test.task;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.argds.app.test.Message;
import ru.argds.app.test.exception.TakeMessageException;
import ru.argds.app.test.service.ConsumerMessageService;

public class ConsumerTask implements Runnable {

    private static final Logger log = LogManager.getLogger(ConsumerTask.class);

    private ConsumerMessageService service;

    public ConsumerTask(ConsumerMessageService service) {
        this.service = service;
    }

    @Override
    public void run() {
        try {
            Message msg = service.take();
            if (msg != null) {
                log.info("Get message from system a: " + msg);
            }
        } catch (TakeMessageException e) {
            log.warn(e);
        }
    }
}
