package com.android.server.appfunctions;

import android.app.appsearch.AppSearchBatchResult;
import android.app.appsearch.AppSearchManager;
import android.app.appsearch.BatchResultCallback;

import com.android.internal.infra.AndroidFuture;

import java.util.Objects;
import java.util.concurrent.Executor;

public final class FutureAppSearchSessionImpl implements FutureAppSearchSession {
    public final Executor mExecutor;
    public final AndroidFuture mSettableSessionFuture;

    public final class BatchResultCallbackAdapter implements BatchResultCallback {
        public final AndroidFuture mFuture;

        public BatchResultCallbackAdapter(AndroidFuture androidFuture) {
            this.mFuture = androidFuture;
        }

        @Override // android.app.appsearch.BatchResultCallback
        public final void onResult(AppSearchBatchResult appSearchBatchResult) {
            this.mFuture.complete(appSearchBatchResult);
        }

        @Override // android.app.appsearch.BatchResultCallback
        public final void onSystemError(Throwable th) {
            this.mFuture.completeExceptionally(th);
        }
    }

    public FutureAppSearchSessionImpl(
            AppSearchManager appSearchManager,
            Executor executor,
            AppSearchManager.SearchContext searchContext) {
        Objects.requireNonNull(appSearchManager);
        Objects.requireNonNull(executor);
        Objects.requireNonNull(searchContext);
        this.mExecutor = executor;
        AndroidFuture androidFuture = new AndroidFuture();
        this.mSettableSessionFuture = androidFuture;
        appSearchManager.createSearchSession(
                searchContext,
                executor,
                new FutureAppSearchSessionImpl$$ExternalSyntheticLambda7(androidFuture));
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
        getSessionAsync().whenComplete(new FutureAppSearchSessionImpl$$ExternalSyntheticLambda2());
    }

    public final AndroidFuture getSessionAsync() {
        return this.mSettableSessionFuture.thenApply(
                new FutureAppSearchSessionImpl$$ExternalSyntheticLambda8(1));
    }
}
