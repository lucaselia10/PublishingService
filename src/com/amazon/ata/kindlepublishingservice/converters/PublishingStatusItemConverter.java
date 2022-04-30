package com.amazon.ata.kindlepublishingservice.converters;

import com.amazon.ata.kindlepublishingservice.dynamodb.models.PublishingStatusItem;
import com.amazon.ata.kindlepublishingservice.models.PublishingStatusRecord;

import java.util.ArrayList;
import java.util.List;

public class PublishingStatusItemConverter {

    private PublishingStatusItemConverter() {}


    // Used to return List<PublishingStatusRecord> and take in List<PublishingStatusItem>
    public static PublishingStatusRecord toPublishingStatus(PublishingStatusItem statusItem) {

        return PublishingStatusRecord.builder()
                .withBookId(statusItem.getBookId())
                .withStatus(String.valueOf(statusItem.getStatus()))
                .withStatusMessage(statusItem.getStatusMessage())
                .build();
    }

    public static List<PublishingStatusRecord> toPublishingStatusList(List<PublishingStatusItem> statusItems) {

        List<PublishingStatusRecord> publishingStatusList = new ArrayList<>();

        for (PublishingStatusItem item : statusItems) {
            publishingStatusList.add(toPublishingStatus(item));
        }

        return publishingStatusList;
    }
}
