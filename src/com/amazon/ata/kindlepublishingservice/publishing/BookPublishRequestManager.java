package com.amazon.ata.kindlepublishingservice.publishing;



import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
@Singleton
public class BookPublishRequestManager {


    private final Queue<BookPublishRequest> bookQueue;

    @Inject
    public BookPublishRequestManager(Queue<BookPublishRequest> bookQueue) {
        this.bookQueue = new ConcurrentLinkedQueue<>(bookQueue);

    }


    //         replaced return of Queue<BookPublishRequest> with void
    public void addBookPublishRequest(final BookPublishRequest bookPublishRequest) {
        bookQueue.add(bookPublishRequest);
//        return bookQueue;
    }
    public BookPublishRequest getBookPublishRequestToProcess() {
        if (bookQueue.isEmpty()) {
            return null;
        }

       return bookQueue.remove();
    }

    public Queue<BookPublishRequest> getBookQueue() {
        return bookQueue;
    }
}
