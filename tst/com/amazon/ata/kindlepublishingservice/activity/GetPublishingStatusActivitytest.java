package com.amazon.ata.kindlepublishingservice.activity;

import com.amazon.ata.kindlepublishingservice.dao.PublishingStatusDao;
import com.amazon.ata.kindlepublishingservice.dynamodb.models.PublishingStatusItem;
import com.amazon.ata.kindlepublishingservice.enums.PublishingRecordStatus;
import com.amazon.ata.kindlepublishingservice.models.PublishingStatusRecord;
import com.amazon.ata.kindlepublishingservice.models.requests.GetPublishingStatusRequest;
import com.amazon.ata.kindlepublishingservice.models.response.GetPublishingStatusResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;


public class GetPublishingStatusActivitytest {

    private static String publishingRecordId = "12345";
    private static PublishingRecordStatus status = PublishingRecordStatus.QUEUED;
    private static String statusMessage = "This is a message";
    private static String bookId = "Book.123";

    @Mock
    private PublishingStatusDao publishingStatusDao;

    @InjectMocks
    private GetPublishingStatusActivity publishingStatusActivity;

    @BeforeEach
    public void setup() {initMocks(this);}



    @Test
    public void execute_singleStatus_returnsSingleStatus() {
        // GIVEN
        GetPublishingStatusRequest request = GetPublishingStatusRequest.builder()
                .withPublishingRecordId(publishingRecordId)
                .build();

        PublishingStatusItem item = new PublishingStatusItem();
        item.setPublishingRecordId(publishingRecordId);
        item.setStatus(status);
        item.setStatusMessage(statusMessage);
        item.setBookId(bookId);

        List<PublishingStatusItem> publishingStatusItems = new LinkedList<>();
        publishingStatusItems.add(item);

        when(publishingStatusDao.getPublishingStatus(publishingRecordId)).thenReturn(publishingStatusItems);

        // WHEN
        GetPublishingStatusResponse response = publishingStatusActivity.execute(request);

        // THEN
        assertNotNull(response, "Expected response to return a not null response");
        assertEquals(publishingStatusItems.get(0).getStatus().toString(), response.getPublishingStatusHistory().get(0).getStatus());
        assertEquals(publishingStatusItems.get(0).getStatusMessage(), response.getPublishingStatusHistory().get(0).getStatusMessage());
        assertEquals(publishingStatusItems.get(0).getBookId(), response.getPublishingStatusHistory().get(0).getBookId());

    }
}