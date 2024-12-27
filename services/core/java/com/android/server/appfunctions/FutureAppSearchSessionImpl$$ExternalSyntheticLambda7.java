package com.android.server.appfunctions;

import android.app.appsearch.AppSearchResult;

import com.android.internal.infra.AndroidFuture;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class FutureAppSearchSessionImpl$$ExternalSyntheticLambda7
        implements Consumer {
    public final /* synthetic */ AndroidFuture f$0;

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        this.f$0.complete((AppSearchResult) obj);
    }
}
