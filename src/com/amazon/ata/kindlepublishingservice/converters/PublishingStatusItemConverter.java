package com.amazon.ata.kindlepublishingservice.converters;

import com.amazon.ata.kindlepublishingservice.dynamodb.models.PublishingStatusItem;
import com.amazon.ata.kindlepublishingservice.models.PublishingStatusRecord;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PublishingStatusItemConverter {

    private PublishingStatusItemConverter() {}

    public static List<PublishingStatusRecord> convert(List<PublishingStatusItem> publishingStatusItems) {
        List<PublishingStatusRecord> publishingStatusRecordList = new LinkedList<>();

        for (PublishingStatusItem item : publishingStatusItems) {
            PublishingStatusRecord record = new PublishingStatusRecord();
            record.setStatus(String.valueOf(item.getStatus()));
            record.setStatusMessage(item.getStatusMessage());
            record.setBookId(item.getBookId());

            publishingStatusRecordList.add(record);
        }
        return publishingStatusRecordList;
    }
}
