package ru.argds.app.test.utils;

import ru.argds.app.test.FileMessage;
import ru.argds.app.test.Message;

import java.lang.management.ManagementFactory;
import java.nio.file.Path;

public class FileMessageUtils {
    private static final String FILE_PATTERN = "%d-%d-%s.qmsg";
    private static String PID;
    static{
        PID = ManagementFactory.getRuntimeMXBean().getName();
    }


    public static String generateFileName(Message msg){
        String fileName = String.format(FILE_PATTERN, msg.getPriority(), msg.getOrder(), PID);
        return fileName;
    }

    public static FileMessage parseFileName(Path pathFileName){
        String fileName = pathFileName.getFileName().toString();
        String[] params = fileName.split("\\-");
        FileMessage msg = FileMessage.createInstance(Integer.valueOf(params[0]), Long.valueOf(params[1]), pathFileName.toString());
        return msg;
    }
}
