package com.android.server.appfunctions;

import android.app.appsearch.AppSearchResult;
import android.app.appsearch.AppSearchSession;
import android.app.appsearch.SetSchemaResponse;

import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class FutureAppSearchSessionImpl$$ExternalSyntheticLambda8
        implements Function {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        AppSearchResult appSearchResult = (AppSearchResult) obj;
        switch (this.$r8$classId) {
            case 0:
                if (appSearchResult.isSuccess()) {
                    return (SetSchemaResponse) appSearchResult.getResultValue();
                }
                throw new RuntimeException(
                        FutureSearchResultsImpl.failedResultToException(appSearchResult));
            default:
                if (appSearchResult.isSuccess()) {
                    return (AppSearchSession) appSearchResult.getResultValue();
                }
                throw new RuntimeException(
                        FutureSearchResultsImpl.failedResultToException(appSearchResult));
        }
    }
}
