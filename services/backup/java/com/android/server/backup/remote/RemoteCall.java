package com.android.server.backup.remote;

import android.os.Handler;
import android.os.Looper;
import com.android.internal.util.Preconditions;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/* loaded from: classes.dex */
public class RemoteCall {
  public final RemoteCallable mCallable;
  public final CompletableFuture mFuture;
  public final long mTimeoutMs;

  public static RemoteResult execute(RemoteCallable remoteCallable, long j) {
    return new RemoteCall(remoteCallable, j).call();
  }

  public RemoteCall(RemoteCallable remoteCallable, long j) {
    this(false, remoteCallable, j);
  }

  public RemoteCall(boolean z, RemoteCallable remoteCallable, long j) {
    this.mCallable = remoteCallable;
    this.mTimeoutMs = j;
    this.mFuture = new CompletableFuture();
    if (z) {
      cancel();
    }
  }

  public RemoteResult call() {
    Preconditions.checkState(
        !Looper.getMainLooper().isCurrentThread(), "Can't call call() on main thread");
    if (!this.mFuture.isDone()) {
      if (this.mTimeoutMs == 0) {
        timeOut();
      } else {
        Handler.getMain()
            .postDelayed(
                new Runnable() { // from class:
                                 // com.android.server.backup.remote.RemoteCall$$ExternalSyntheticLambda0
                  @Override // java.lang.Runnable
                  public final void run() {
                    RemoteCall.this.timeOut();
                  }
                },
                this.mTimeoutMs);
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

  public void cancel() {
    this.mFuture.complete(RemoteResult.FAILED_CANCELLED);
  }

  public final void timeOut() {
    this.mFuture.complete(RemoteResult.FAILED_TIMED_OUT);
  }
}
