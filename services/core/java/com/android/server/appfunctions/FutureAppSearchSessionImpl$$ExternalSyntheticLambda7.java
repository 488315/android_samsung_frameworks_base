package com.android.server.appfunctions;

import android.app.appsearch.AppSearchResult;

import com.android.internal.infra.AndroidFuture;

import java.util.function.Consumer;

public final /* synthetic */ class FutureAppSearchSessionImpl$$ExternalSyntheticLambda7
        implements Consumer {
    public final /* synthetic */ AndroidFuture f$0;

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        this.f$0.complete((AppSearchResult) obj);
    }
}
