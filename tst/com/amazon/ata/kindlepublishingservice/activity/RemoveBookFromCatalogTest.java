package com.amazon.ata.kindlepublishingservice.activity;


import com.amazon.ata.kindlepublishingservice.dao.CatalogDao;
import com.amazon.ata.kindlepublishingservice.dynamodb.models.CatalogItemVersion;
import com.amazon.ata.kindlepublishingservice.models.Book;
import com.amazon.ata.kindlepublishingservice.models.requests.RemoveBookFromCatalogRequest;
import com.amazon.ata.kindlepublishingservice.models.response.RemoveBookFromCatalogResponse;
import com.amazon.ata.recommendationsservice.types.BookGenre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.inject.Inject;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertTrue;


public class RemoveBookFromCatalogTest {

    private static String bookId = "12345";

    @Mock
    private CatalogDao catalogDao;

    @InjectMocks
    private RemoveBookFromCatalogActivity activity;

    @BeforeEach
    public void setup(){
        initMocks(this);
    }

    @Test
    public void execute_bookExists_changeBookToInactive() {
        // GIVEN

        RemoveBookFromCatalogRequest request = RemoveBookFromCatalogRequest.builder()
                .withBookId(bookId)
                .build();

        CatalogItemVersion catalogItem = new CatalogItemVersion();
        catalogItem.setInactive(false);
        catalogItem.setBookId(bookId);
        catalogItem.setVersion(1);
        when(catalogDao.removeBookFromCatalog(bookId)).thenReturn(catalogItem);

        // WHEN
        RemoveBookFromCatalogResponse response = activity.execute(request);

        // THEN
        assertTrue(catalogItem.isInactive());
    }


}
