package com.android.server.appfunctions;

import android.app.appsearch.AppSearchResult;
import android.app.appsearch.SearchResults;

import com.android.internal.infra.AndroidFuture;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
