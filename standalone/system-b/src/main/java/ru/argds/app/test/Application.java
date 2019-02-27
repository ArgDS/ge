package ru.argds.app.test;

import ru.argds.app.test.exception.DirectoryDataNotAvailableException;
import ru.argds.app.test.service.ConsumerMessageService;
import ru.argds.app.test.service.FileConsumerMessageServiceImpl;
import ru.argds.app.test.task.ConsumerTask;

import java.util.concurrent.*;

public class Application implements Runnable {

    private static final long TIME_PROCESS = 1000;

    private String pathDir;
    private ConsumerMessageService consumerMessageService;
    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

    public static void main(String[] args) throws InterruptedException, DirectoryDataNotAvailableException {
        String pathDir = args[0];
        Application app = new Application(pathDir);
        Thread appThread = new Thread(app);
        appThread.start();
        appThread.join();
        /*app.shutdown();*/

    }

    public Application(String pathDir) throws DirectoryDataNotAvailableException {
        this.pathDir = pathDir;
        consumerMessageService = new FileConsumerMessageServiceImpl(this.pathDir);
        executorService.scheduleAtFixedRate(new ConsumerTask(consumerMessageService), 100, TIME_PROCESS, TimeUnit.MILLISECONDS);
    }

    @Override
    public void run() {

    }

    public void shutdown(){
        consumerMessageService.shutdown();
    }
}
