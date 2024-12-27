package com.android.server.appfunctions;

import android.app.appsearch.AppSearchResult;
import android.app.appsearch.GlobalSearchSession;

import java.util.function.Function;

public final /* synthetic */ class FutureGlobalSearchSession$$ExternalSyntheticLambda4
        implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        AppSearchResult appSearchResult = (AppSearchResult) obj;
        if (appSearchResult.isSuccess()) {
            return (GlobalSearchSession) appSearchResult.getResultValue();
        }
        throw new RuntimeException(
                FutureSearchResultsImpl.failedResultToException(appSearchResult));
    }
}
