package com.android.server.ondeviceintelligence.callbacks;

import android.app.ondeviceintelligence.IDownloadCallback;
import android.os.Handler;
import android.os.PersistableBundle;
import com.android.internal.infra.AndroidFuture;
import java.util.concurrent.TimeoutException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class ListenableDownloadCallback extends IDownloadCallback.Stub implements Runnable {
    public final IDownloadCallback callback;
    public final AndroidFuture future;
    public final Handler handler;
    public final long idleTimeoutMs;

    public ListenableDownloadCallback(IDownloadCallback iDownloadCallback, Handler handler, AndroidFuture androidFuture, long j) {
        this.callback = iDownloadCallback;
        this.handler = handler;
        this.future = androidFuture;
        this.idleTimeoutMs = j;
        handler.postDelayed(this, j);
    }

    public final void onDownloadCompleted(PersistableBundle persistableBundle) {
        this.callback.onDownloadCompleted(persistableBundle);
        this.handler.removeCallbacks(this);
        this.future.complete((Object) null);
    }

    public final void onDownloadFailed(int i, String str, PersistableBundle persistableBundle) {
        this.callback.onDownloadFailed(i, str, persistableBundle);
        this.handler.removeCallbacks(this);
        this.future.completeExceptionally(new TimeoutException());
    }

    public final void onDownloadProgress(long j) {
        this.callback.onDownloadProgress(j);
        this.handler.removeCallbacks(this);
        this.handler.postDelayed(this, this.idleTimeoutMs);
    }

    public final void onDownloadStarted(long j) {
        this.callback.onDownloadStarted(j);
        this.handler.removeCallbacks(this);
        this.handler.postDelayed(this, this.idleTimeoutMs);
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.future.completeExceptionally(new TimeoutException());
    }
}
