package com.amazon.ata.kindlepublishingservice.activity;

import com.amazon.ata.kindlepublishingservice.converters.PublishingStatusItemConverter;
import com.amazon.ata.kindlepublishingservice.dao.PublishingStatusDao;
import com.amazon.ata.kindlepublishingservice.dynamodb.models.PublishingStatusItem;
import com.amazon.ata.kindlepublishingservice.exceptions.PublishingStatusNotFoundException;
import com.amazon.ata.kindlepublishingservice.models.requests.GetPublishingStatusRequest;
import com.amazon.ata.kindlepublishingservice.models.response.GetPublishingStatusResponse;

import javax.inject.Inject;
import java.util.List;

public class GetPublishingStatusActivity {
    private PublishingStatusDao publishingStatusDao;

    public GetPublishingStatusActivity() {
    }
    @Inject
    public GetPublishingStatusActivity(PublishingStatusDao publishingStatusDao) {
        this.publishingStatusDao = publishingStatusDao;
    }

    public GetPublishingStatusResponse execute(GetPublishingStatusRequest publishingStatusRequest)
            throws PublishingStatusNotFoundException {

        List<PublishingStatusItem> publishingStatusItems = publishingStatusDao.getPublishingStatus(
                publishingStatusRequest.getPublishingRecordId());

        return GetPublishingStatusResponse.builder()
                .withPublishingStatusHistory(PublishingStatusItemConverter.toPublishingStatusList(publishingStatusItems))
                .build();

    }
}
