package com.android.server.backup.remote;

import android.os.Handler;
import android.os.Looper;

import com.android.internal.util.Preconditions;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class RemoteCall {
    public final RemoteCallable mCallable;
    public final CompletableFuture mFuture;
    public final long mTimeoutMs;

    public RemoteCall(boolean z, RemoteCallable remoteCallable, long j) {
        this.mCallable = remoteCallable;
        this.mTimeoutMs = j;
        CompletableFuture completableFuture = new CompletableFuture();
        this.mFuture = completableFuture;
        if (z) {
            completableFuture.complete(RemoteResult.FAILED_CANCELLED);
        }
    }

    public final RemoteResult call() {
        Preconditions.checkState(
                !Looper.getMainLooper().isCurrentThread(), "Can't call call() on main thread");
        if (!this.mFuture.isDone()) {
            long j = this.mTimeoutMs;
            if (j == 0) {
                this.mFuture.complete(RemoteResult.FAILED_TIMED_OUT);
            } else {
                Handler.getMain()
                        .postDelayed(
                                new Runnable() { // from class:
                                                 // com.android.server.backup.remote.RemoteCall$$ExternalSyntheticLambda0
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        RemoteCall.this.mFuture.complete(
                                                RemoteResult.FAILED_TIMED_OUT);
                                    }
                                },
                                j);
                this.mCallable.call(new FutureBackupCallback(this.mFuture));
            }
        }
        try {
            return (RemoteResult) this.mFuture.get();
        } catch (InterruptedException unused) {
            return RemoteResult.FAILED_THREAD_INTERRUPTED;
        } catch (ExecutionException unused2) {
            throw new IllegalStateException("Future unexpectedly completed with an exception");
        }
    }
}
