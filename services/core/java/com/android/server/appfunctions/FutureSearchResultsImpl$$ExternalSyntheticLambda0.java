package com.android.server.appfunctions;

import android.app.appsearch.AppSearchResult;

import java.util.List;
import java.util.function.Function;

public final /* synthetic */ class FutureSearchResultsImpl$$ExternalSyntheticLambda0
        implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        AppSearchResult appSearchResult = (AppSearchResult) obj;
        if (appSearchResult.isSuccess()) {
            return (List) appSearchResult.getResultValue();
        }
        throw new RuntimeException(
                FutureSearchResultsImpl.failedResultToException(appSearchResult));
    }
}
