package ru.argds.app.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.argds.app.test.exception.DirectoryDataNotAvailableException;
import ru.argds.app.test.exception.SendMessageException;
import ru.argds.app.test.service.FileMessageServiceImpl;
import ru.argds.app.test.service.MessageService;

import java.util.Random;

public class Application implements Runnable{

    private static final Logger log = LogManager.getLogger(Application.class);

    private static final int MAX_PRIORITY = 50;

    private Random random = new Random(System.currentTimeMillis());
    private String pathDataDir;
    private MessageService messageService;

    public static void main(String[] args){
        try{
            String pathDir = args[0];
            Application app = new Application(pathDir);
            Thread appThread = new Thread(app);
            appThread.start();
            appThread.join();
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public Application(String pathDataDir) throws DirectoryDataNotAvailableException {
        this.pathDataDir = pathDataDir;
        messageService = new FileMessageServiceImpl(this.pathDataDir);
    }

    @Override
    public void run() {
        while(true){
            try {
                Thread.sleep(random.nextInt(1000));
                Message msg = generateMessage();
                log.info("Send message: " + msg);
                messageService.send(msg);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (SendMessageException e) {
                e.printStackTrace();
            }
        }
    }

    private Message generateMessage(){
        Message msg = new Message();
        msg.setOrder(System.nanoTime());
        msg.setPriority(random.nextInt(MAX_PRIORITY));
        msg.setText(Long.valueOf(random.nextLong()).toString());
        return msg;
    }
}
