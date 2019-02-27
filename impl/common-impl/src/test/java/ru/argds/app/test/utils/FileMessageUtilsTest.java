package ru.argds.app.test.utils;

import org.junit.jupiter.api.Test;
import ru.argds.app.test.FileMessage;
import ru.argds.app.test.Message;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class FileMessageUtilsTest {

    @Test
    public void test(){
        Integer priority = Integer.MAX_VALUE;
        Long order = Long.MAX_VALUE;
        Message message = new Message();
        message.setOrder(order);
        message.setPriority(priority);

        String fileName = FileMessageUtils.generateFileName(message);
        assertTrue(fileName.startsWith(priority.toString()));
        assertTrue(fileName.startsWith(order.toString(), (priority.toString() + "-").length()));
        FileMessage parsedMessage = FileMessageUtils.parseFileName(Paths.get(fileName));
        assertEquals(message.getOrder(),parsedMessage.getOrder());
        assertEquals(message.getPriority(), parsedMessage.getPriority());
    }
}
