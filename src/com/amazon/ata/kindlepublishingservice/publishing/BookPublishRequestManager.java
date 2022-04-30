package com.amazon.ata.kindlepublishingservice.publishing;


import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
@Singleton
public class BookPublishRequestManager {
    private final Queue<BookPublishRequest> bookQueue;

    @Inject
    public BookPublishRequestManager(ConcurrentLinkedQueue<BookPublishRequest> bookQueue) {
        this.bookQueue = bookQueue;

    }

    public void addBookPublishRequest(final BookPublishRequest bookPublishRequest) {
        bookQueue.add(bookPublishRequest);
    }
    public BookPublishRequest getBookPublishRequestToProcess() {
//        if (bookQueue.isEmpty()) {
//            return null;
//        }
        return bookQueue.poll();
    }
}
