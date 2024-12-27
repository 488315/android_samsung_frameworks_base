package com.android.server.appfunctions;

import android.app.appsearch.AppSearchManager;

import com.android.internal.infra.AndroidFuture;

import java.io.Closeable;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class FutureGlobalSearchSession implements Closeable {
    public final Executor mExecutor;
    public final AndroidFuture mSettableSessionFuture;

    public FutureGlobalSearchSession(AppSearchManager appSearchManager, Executor executor) {
        this.mExecutor = executor;
        AndroidFuture androidFuture = new AndroidFuture();
        this.mSettableSessionFuture = androidFuture;
        appSearchManager.createGlobalSearchSession(
                executor, new FutureAppSearchSessionImpl$$ExternalSyntheticLambda7(androidFuture));
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
        this.mSettableSessionFuture
                .thenApply(new FutureGlobalSearchSession$$ExternalSyntheticLambda4())
                .whenComplete(new FutureGlobalSearchSession$$ExternalSyntheticLambda1());
    }
}
