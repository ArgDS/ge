package ru.argds.app.test.service;

import ru.argds.app.test.FileMessage;
import ru.argds.app.test.Message;
import ru.argds.app.test.exception.DirectoryDataNotAvailableException;
import ru.argds.app.test.exception.TakeMessageException;
import ru.argds.app.test.processor.WatchFileSystemProcessor;

import java.io.*;
import java.nio.file.*;
import java.util.Objects;
import java.util.TreeSet;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;


public class FileConsumerMessageServiceImpl implements ConsumerMessageService {

    private Path pathDataDir;
    private FileSystem fs;
    private WatchService watchService;
    private PriorityBlockingQueue<FileMessage> queue = new PriorityBlockingQueue<>();
    private WatchFileSystemProcessor watchFileSystemProcessor;

    public FileConsumerMessageServiceImpl(String pathDataDir) throws DirectoryDataNotAvailableException {
        this.pathDataDir = Paths.get(pathDataDir).toAbsolutePath();
        fs = FileSystems.getDefault();
        try {
            watchService = fs.newWatchService();
            WatchKey wKey = this.pathDataDir.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
            wKey.reset();
        } catch (IOException e) {
           throw new DirectoryDataNotAvailableException("Failed create watch service for directory with data", e);
        }
        watchFileSystemProcessor = new WatchFileSystemProcessor(queue, watchService, this.pathDataDir);
        Thread watchThread = new Thread(watchFileSystemProcessor);
        watchThread.start();

    }

    @Override
    public Message take() throws TakeMessageException {
        try {
            FileMessage fileMessage = queue.poll(500, TimeUnit.MILLISECONDS);
            if (fileMessage == null){
                return null;
            }
            return load(fileMessage);
        }catch (Exception e){
            throw new TakeMessageException(e);
        }
    }

    @Override
    public void shutdown(){
        watchFileSystemProcessor.stop();
        try {
            watchService.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Message load(FileMessage fileMessage) throws IOException {
        Message msg = new Message();
        Path filePath = Paths.get(fileMessage.getPathFileName());
        File file = filePath.toFile();
        msg.setPriority(fileMessage.getPriority());
        msg.setOrder(fileMessage.getOrder());
        StringBuilder text = new StringBuilder();
        try(BufferedReader fileReader = new BufferedReader(new FileReader(file))){
            while(fileReader.ready()){
                text.append(fileReader.readLine());
            }
        } catch (Exception e) {
            throw e;
        }
        msg.setText(text.toString());
        return msg;
    }
}
