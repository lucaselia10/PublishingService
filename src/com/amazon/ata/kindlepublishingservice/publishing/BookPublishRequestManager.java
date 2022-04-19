package com.amazon.ata.kindlepublishingservice.publishing;

import com.amazon.ata.kindlepublishingservice.dao.CatalogDao;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.Queue;

public class BookPublishRequestManager {

    private CatalogDao catalogDao;

    @Inject
    public BookPublishRequestManager(CatalogDao catalogDao) {
        this.catalogDao = catalogDao;
    }

    public Queue<BookPublishRequest> addBookPublishRequest(BookPublishRequest bookPublishRequest) {

        Queue<BookPublishRequest> bookQueue = new LinkedList<>();
        bookQueue.add(bookPublishRequest);
        return bookQueue;
    }
    public BookPublishRequest getBookPublishRequestToProcess(Queue<BookPublishRequest> bookPublishRequestQueue) {
        if (bookPublishRequestQueue.isEmpty()) {
            return null;
        }

       return bookPublishRequestQueue.remove();
    }


}
