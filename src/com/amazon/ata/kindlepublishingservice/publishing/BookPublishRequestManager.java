package com.amazon.ata.kindlepublishingservice.publishing;

import com.amazon.ata.kindlepublishingservice.dao.CatalogDao;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.Queue;

public class BookPublishRequestManager {

//    private CatalogDao catalogDao;
    private Queue<BookPublishRequest> bookQueue;

    // had a CatalogDao in here but I dont need it
    @Inject
    public BookPublishRequestManager(Queue<BookPublishRequest> bookQueue) {
//        this.catalogDao = catalogDao;
        this.bookQueue = bookQueue;

    }


    //         replaced return of Queue<BookPublishRequest> with void
    public void addBookPublishRequest(BookPublishRequest bookPublishRequest) {

        bookQueue = new LinkedList<>();
        bookQueue.add(bookPublishRequest);
//        return bookQueue;
    }
    public BookPublishRequest getBookPublishRequestToProcess() {
        if (bookQueue.isEmpty()) {
            return null;
        }

       return bookQueue.remove();
    }


}
