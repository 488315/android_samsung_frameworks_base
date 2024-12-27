package com.android.server.pm.utils;

import android.os.Handler;

import com.android.server.pm.PackageInstallerService$$ExternalSyntheticLambda7;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class RequestThrottle {
    public final Supplier mBlock;
    public final Handler mHandler;
    public final AtomicInteger mLastRequest = new AtomicInteger(0);
    public final AtomicInteger mLastCommitted = new AtomicInteger(-1);
    public final AtomicInteger mCurrentRetry = new AtomicInteger(0);
    public final int mMaxAttempts = 5;
    public final int mFirstDelay = 1000;
    public final int mBackoffBase = 2;
    public final RequestThrottle$$ExternalSyntheticLambda0 mRunnable =
            new Runnable() { // from class:
                             // com.android.server.pm.utils.RequestThrottle$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    RequestThrottle.this.runInternal();
                }
            };

    /* JADX WARN: Type inference failed for: r4v4, types: [com.android.server.pm.utils.RequestThrottle$$ExternalSyntheticLambda0] */
    public RequestThrottle(
            Handler handler,
            PackageInstallerService$$ExternalSyntheticLambda7
                    packageInstallerService$$ExternalSyntheticLambda7) {
        this.mHandler = handler;
        this.mBlock = packageInstallerService$$ExternalSyntheticLambda7;
    }

    public final boolean runInternal() {
        int i = this.mLastRequest.get();
        if (i == this.mLastCommitted.get()) {
            return true;
        }
        if (((Boolean) this.mBlock.get()).booleanValue()) {
            this.mCurrentRetry.set(0);
            this.mLastCommitted.set(i);
            return true;
        }
        int andIncrement = this.mCurrentRetry.getAndIncrement();
        if (andIncrement < this.mMaxAttempts) {
            this.mHandler.postDelayed(
                    this.mRunnable,
                    (long) (Math.pow(this.mBackoffBase, andIncrement) * this.mFirstDelay));
        } else {
            this.mCurrentRetry.set(0);
        }
        return false;
    }

    public final void schedule() {
        this.mLastRequest.incrementAndGet();
        this.mHandler.post(this.mRunnable);
    }
}
