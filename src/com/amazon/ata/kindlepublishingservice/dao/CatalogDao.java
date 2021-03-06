package com.amazon.ata.kindlepublishingservice.dao;

import com.amazon.ata.kindlepublishingservice.dynamodb.models.CatalogItemVersion;
import com.amazon.ata.kindlepublishingservice.exceptions.BookNotFoundException;
import com.amazon.ata.kindlepublishingservice.publishing.KindleFormattedBook;
import com.amazon.ata.kindlepublishingservice.utils.KindlePublishingUtils;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;

import javax.inject.Inject;
import java.util.List;

public class CatalogDao {

    private final DynamoDBMapper dynamoDbMapper;

    /**
     * Instantiates a new CatalogDao object.
     *
     * @param dynamoDbMapper The {@link DynamoDBMapper} used to interact with the catalog table.
     */
    @Inject
    public CatalogDao(DynamoDBMapper dynamoDbMapper) {
        this.dynamoDbMapper = dynamoDbMapper;
    }

    /**
     * Returns the latest version of the book from the catalog corresponding to the specified book id.
     * Throws a BookNotFoundException if the latest version is not active or no version is found.
     *
     * @param bookId Id associated with the book.
     * @return The corresponding CatalogItem from the catalog table.
     */
    public CatalogItemVersion getBookFromCatalog(String bookId) {
        CatalogItemVersion book = getLatestVersionOfBook(bookId);

        // Removed a check in the if statement to see if the book is inactive
        // check threw an exception if the book was inactive
        // had to remove for Mastery Task 2 Tests to pass
        if (book == null || book.isInactive()) {
            throw new BookNotFoundException(String.format("No book found for id: %s", bookId));
        }

        return book;
    }

    // Returns null if no version exists for the provided bookId
    private CatalogItemVersion getLatestVersionOfBook(String bookId) {
        CatalogItemVersion book = new CatalogItemVersion();
        book.setBookId(bookId);

        DynamoDBQueryExpression<CatalogItemVersion> queryExpression = new DynamoDBQueryExpression()
                .withHashKeyValues(book)
                .withScanIndexForward(false)
                .withLimit(1);

        List<CatalogItemVersion> results = dynamoDbMapper.query(CatalogItemVersion.class, queryExpression);
        if (results.isEmpty()) {
            return null;
        }
        return results.get(0);
    }

    public CatalogItemVersion removeBookFromCatalog(String bookId) {
        CatalogItemVersion inactiveVersion = getBookFromCatalog(bookId);

        inactiveVersion.setInactive(true);
        dynamoDbMapper.save(inactiveVersion);
        return inactiveVersion;
    }

    public void validateBookExists(String bookId) {
        CatalogItemVersion book = getBookFromCatalog(bookId);

        if (book == null) {
            throw new BookNotFoundException(String.format("no book found for id: %s", bookId));
        }

    }

    public CatalogItemVersion createOrUpdateBook(KindleFormattedBook kindleFormattedBook) {
        if (kindleFormattedBook.getBookId() == null) {
            CatalogItemVersion catalogItemVersion = new CatalogItemVersion();
            catalogItemVersion.setBookId(KindlePublishingUtils.generateBookId());
            catalogItemVersion.setVersion(1);
            catalogItemVersion.setTitle(kindleFormattedBook.getTitle());
            catalogItemVersion.setAuthor(kindleFormattedBook.getAuthor());
            catalogItemVersion.setText(kindleFormattedBook.getText());
            catalogItemVersion.setGenre(kindleFormattedBook.getGenre());
            dynamoDbMapper.save(catalogItemVersion);
            return catalogItemVersion;
        } else {

            if (getLatestVersionOfBook(kindleFormattedBook.getBookId()) == null) {
                throw new BookNotFoundException(String.format("Book not found for id: %s", kindleFormattedBook.getBookId()));
            }
            CatalogItemVersion versionUpdate = getLatestVersionOfBook(kindleFormattedBook.getBookId());
            removeBookFromCatalog(versionUpdate.getBookId());

            versionUpdate.setBookId(versionUpdate.getBookId());
            versionUpdate.setVersion(versionUpdate.getVersion() + 1);
            versionUpdate.setAuthor(versionUpdate.getAuthor());
            versionUpdate.setGenre(versionUpdate.getGenre());
            versionUpdate.setInactive(false);
            versionUpdate.setText(versionUpdate.getText());
            versionUpdate.setTitle(versionUpdate.getTitle());
            dynamoDbMapper.save(versionUpdate);

            return versionUpdate;

        }
    }
}