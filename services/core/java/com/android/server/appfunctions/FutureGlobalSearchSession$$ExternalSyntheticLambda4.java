package com.android.server.appfunctions;

import android.app.appsearch.AppSearchResult;
import android.app.appsearch.GlobalSearchSession;
import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class FutureGlobalSearchSession$$ExternalSyntheticLambda4 implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        AppSearchResult appSearchResult = (AppSearchResult) obj;
        if (appSearchResult.isSuccess()) {
            return (GlobalSearchSession) appSearchResult.getResultValue();
        }
        throw new RuntimeException(FutureSearchResultsImpl.failedResultToException(appSearchResult));
    }
}
