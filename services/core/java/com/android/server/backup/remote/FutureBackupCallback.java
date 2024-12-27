package com.android.server.backup.remote;

import android.app.backup.IBackupCallback;

import java.util.concurrent.CompletableFuture;

public final class FutureBackupCallback extends IBackupCallback.Stub {
    public final CompletableFuture mFuture;

    public FutureBackupCallback(CompletableFuture completableFuture) {
        this.mFuture = completableFuture;
    }

    public final void operationComplete(long j) {
        this.mFuture.complete(new RemoteResult(0, j));
    }
}
