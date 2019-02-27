package ru.argds.app.test.processor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.argds.app.test.FileMessage;
import ru.argds.app.test.utils.FileMessageUtils;

import java.nio.file.*;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

public class WatchFileSystemProcessor implements Runnable {

    private static final Logger log = LogManager.getLogger(WatchFileSystemProcessor.class);

    private Queue<FileMessage> queue;
    private WatchService watchService;
    private Path dataDir;
    private volatile boolean running = true;

    public WatchFileSystemProcessor(Queue<FileMessage> queue, WatchService watchService, Path dataDir) {
        this.queue = queue;
        this.watchService = watchService;
        this.dataDir = dataDir;
    }

    public void stop(){
        running = false;
    }

    @Override
    public void run() {
        WatchKey watchKey = null;
        while (running){
            try {
                watchKey = watchService.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            List<FileMessage> fileMessages = watchKey.pollEvents().stream().filter(event -> {
                    WatchEvent.Kind<?> kind = event.kind();
                    return kind == StandardWatchEventKinds.ENTRY_CREATE;
                }).map(event -> {
                    Path path = ((WatchEvent<Path>)event).context();
                    return Paths.get(dataDir.toString(), path.toString());
                }).map(FileMessageUtils::parseFileName)
                .collect(Collectors.toList());
            log.debug(fileMessages);
            watchKey.reset();
            queue.addAll(fileMessages);


        }
    }
}
