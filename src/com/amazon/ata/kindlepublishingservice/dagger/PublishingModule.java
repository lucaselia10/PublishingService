package com.amazon.ata.kindlepublishingservice.dagger;

import com.amazon.ata.kindlepublishingservice.publishing.BookPublishRequestManager;
import com.amazon.ata.kindlepublishingservice.publishing.BookPublishTask;
import com.amazon.ata.kindlepublishingservice.publishing.BookPublisher;
import dagger.Module;
import dagger.Provides;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import javax.inject.Singleton;

@Module
public class PublishingModule {

    @Provides
    @Singleton
    public BookPublisher provideBookPublisher(ScheduledExecutorService scheduledExecutorService, BookPublishTask publishTask) {
        return new BookPublisher(scheduledExecutorService, publishTask);
    }

    @Provides
    @Singleton
    public ScheduledExecutorService provideBookPublisherScheduler() {

        return Executors.newScheduledThreadPool(4);
    }

    @Provides
    @Singleton
    public BookPublishRequestManager provideBookPublishRequestManager() {
        return new BookPublishRequestManager(new ConcurrentLinkedQueue<>());
    }
}
