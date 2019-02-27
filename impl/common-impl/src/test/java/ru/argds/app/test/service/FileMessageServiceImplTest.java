package ru.argds.app.test.service;

import org.junit.jupiter.api.Test;
import ru.argds.app.test.Message;
import ru.argds.app.test.exception.DirectoryDataNotAvailableException;
import ru.argds.app.test.exception.SendMessageException;
import ru.argds.app.test.utils.FileMessageUtils;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;


class FileMessageServiceImplTest {


    @Test
    public void send() throws DirectoryDataNotAvailableException, SendMessageException {
        String dirDate = "/data";
        MessageService messageService = new FileMessageServiceImpl(dirDate);
        Message msg = new Message();
        msg.setPriority(2);
        msg.setOrder(System.currentTimeMillis());
        msg.setText("tututu");
        String fileName = FileMessageUtils.generateFileName(msg);
        Path pathMsgFile = Paths.get(Paths.get(dirDate).toAbsolutePath().toString(), fileName);
        messageService.send(msg);
        assertTrue(pathMsgFile.toFile().exists());
    }
}