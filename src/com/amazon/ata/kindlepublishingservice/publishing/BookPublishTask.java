package com.amazon.ata.kindlepublishingservice.publishing;

import com.amazon.ata.kindlepublishingservice.dao.CatalogDao;
import com.amazon.ata.kindlepublishingservice.dao.PublishingStatusDao;
import com.amazon.ata.kindlepublishingservice.dynamodb.models.CatalogItemVersion;
import com.amazon.ata.kindlepublishingservice.enums.PublishingRecordStatus;
import com.amazon.ata.kindlepublishingservice.exceptions.BookNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class BookPublishTask implements Runnable {

    private static final Logger LOGGER = LogManager.getLogger(BookPublisher.class);

    private BookPublishRequestManager bookPublishRequestManager;
    private PublishingStatusDao publishingStatusDao;
    private CatalogDao catalogDao;

    @Inject
    public BookPublishTask(BookPublishRequestManager bookPublishRequestManager, PublishingStatusDao publishingStatusDao, CatalogDao catalogDao){
        this.bookPublishRequestManager = bookPublishRequestManager;
        this.publishingStatusDao = publishingStatusDao;
        this.catalogDao = catalogDao;
    }

    public void run() {
        LOGGER.info(">>BookPublishTask Task run()");
        BookPublishRequest request = bookPublishRequestManager.getBookPublishRequestToProcess();
        LOGGER.info("BookPublishRequest from QUEUE");

        if (request == null) {
            LOGGER.info("BookPublishRequest QUEUE IS NULL");
            return;
        }

        publishingStatusDao.setPublishingStatus(
                request.getPublishingRecordId(),
                PublishingRecordStatus.IN_PROGRESS,
                request.getBookId());
        LOGGER.info(">>>>>>PublishingStatusDao IN_PROGRESS");

        try {
            KindleFormattedBook kindleFormattedBook = KindleFormatConverter.format(request);
            LOGGER.info(">>>>>>KINDLE FORMAT CONVERTER");

            CatalogItemVersion book = catalogDao.createOrUpdateBook(kindleFormattedBook);
            LOGGER.info("CALL TO UPDATE CATALOG DAO");
            publishingStatusDao.setPublishingStatus(
                    request.getPublishingRecordId(),
                    PublishingRecordStatus.SUCCESSFUL,
                    book.getBookId());
            LOGGER.info(">>>>>>>>>>>CatalogDao SUCCESSFUL");

        } catch (Exception e) {
            publishingStatusDao.setPublishingStatus(
                    request.getPublishingRecordId(),
                    PublishingRecordStatus.FAILED,
                    request.getBookId());
            LOGGER.info(">>>>>>>>>CatalogDao FAILED");
            e.getMessage();
        }
    }
}
