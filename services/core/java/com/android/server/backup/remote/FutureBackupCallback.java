package com.android.server.backup.remote;

import android.app.backup.IBackupCallback;

import java.util.concurrent.CompletableFuture;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class FutureBackupCallback extends IBackupCallback.Stub {
    public final CompletableFuture mFuture;

    public FutureBackupCallback(CompletableFuture completableFuture) {
        this.mFuture = completableFuture;
    }

    public final void operationComplete(long j) {
        this.mFuture.complete(new RemoteResult(0, j));
    }
}
