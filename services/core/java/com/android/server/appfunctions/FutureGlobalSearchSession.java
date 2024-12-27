package com.android.server.appfunctions;

import android.app.appsearch.AppSearchManager;

import com.android.internal.infra.AndroidFuture;

import java.io.Closeable;
import java.util.concurrent.Executor;

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
