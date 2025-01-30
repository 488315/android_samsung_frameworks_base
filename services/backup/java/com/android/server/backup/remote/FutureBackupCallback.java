package com.android.server.backup.remote;

import android.app.backup.IBackupCallback;
import java.util.concurrent.CompletableFuture;

/* loaded from: classes.dex */
public class FutureBackupCallback extends IBackupCallback.Stub {
    public final CompletableFuture mFuture;

    public FutureBackupCallback(CompletableFuture completableFuture) {
        this.mFuture = completableFuture;
    }

    public void operationComplete(long j) {
        this.mFuture.complete(RemoteResult.m5of(j));
    }
}
