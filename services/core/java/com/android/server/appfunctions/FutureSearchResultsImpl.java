package com.android.server.appfunctions;

import android.app.appsearch.AppSearchResult;
import android.app.appsearch.SearchResults;

import com.android.internal.infra.AndroidFuture;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.Executor;

public final class FutureSearchResultsImpl implements Closeable {
    public final Executor mExecutor;
    public final SearchResults mSearchResults;

    public FutureSearchResultsImpl(SearchResults searchResults, Executor executor) {
        this.mSearchResults = searchResults;
        this.mExecutor = executor;
    }

    public static Exception failedResultToException(AppSearchResult appSearchResult) {
        int resultCode = appSearchResult.getResultCode();
        return resultCode != 3
                ? resultCode != 4
                        ? resultCode != 8
                                ? new IllegalStateException(appSearchResult.getErrorMessage())
                                : new SecurityException(appSearchResult.getErrorMessage())
                        : new IOException(appSearchResult.getErrorMessage())
                : new IllegalArgumentException(appSearchResult.getErrorMessage());
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
        this.mSearchResults.close();
    }

    public final AndroidFuture getNextPage() {
        AndroidFuture androidFuture = new AndroidFuture();
        this.mSearchResults.getNextPage(
                this.mExecutor,
                new FutureAppSearchSessionImpl$$ExternalSyntheticLambda7(androidFuture));
        return androidFuture.thenApply(new FutureSearchResultsImpl$$ExternalSyntheticLambda0());
    }
}
