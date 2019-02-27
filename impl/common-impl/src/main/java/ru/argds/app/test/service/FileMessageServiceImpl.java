package ru.argds.app.test.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.argds.app.test.Message;
import ru.argds.app.test.exception.DirectoryDataNotAvailableException;
import ru.argds.app.test.exception.SendMessageException;
import ru.argds.app.test.utils.FileMessageUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileMessageServiceImpl implements MessageService {

    private static final Logger log = LogManager.getLogger(FileMessageServiceImpl.class);

    private String dirData;
    private Path pathDirData;

    public FileMessageServiceImpl(String dirData) throws DirectoryDataNotAvailableException {
        this.dirData = dirData;
        pathDirData = Paths.get(dirData).toAbsolutePath();
        File dir = pathDirData.toFile();
        if (dir.isDirectory() && dir.canRead() && dir.canWrite()){
            log.info("Directory for data checked");
        }else{
            throw new DirectoryDataNotAvailableException();
        }
    }

    @Override
    public void send(Message msg) throws SendMessageException {
        String fileName = FileMessageUtils.generateFileName(msg);
        Path fullPath = Paths.get(pathDirData.toAbsolutePath().toString(), fileName);
        File msgFile = fullPath.toFile();
        if (msgFile.exists()){
            throw new SendMessageException(msgFile.getAbsolutePath() + " file exists");
        }
        try(FileWriter out = new FileWriter(msgFile)) {
            out.append(msg.getText());
        } catch (IOException e) {
            throw new SendMessageException("Failed open or write data to file", e);
        }
    }
}
