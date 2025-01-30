package com.android.server.biometrics.sensors.fingerprint;

import android.os.Handler;
import com.android.server.ServiceThread;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;

/* loaded from: classes.dex */
public final class SemFpMainThread extends ServiceThread implements Executor {
    public static SemFpMainThread sInstance;

    public SemFpMainThread() {
        super("biometrics.fp", -2, true);
    }

    public static SemFpMainThread get() {
        if (sInstance == null) {
            synchronized (SemFpMainThread.class) {
                if (sInstance == null) {
                    SemFpMainThread semFpMainThread = new SemFpMainThread();
                    sInstance = semFpMainThread;
                    semFpMainThread.start();
                }
            }
        }
        return sInstance;
    }

    @Override // java.util.concurrent.Executor
    public void execute(Runnable runnable) {
        if (post(runnable)) {
            return;
        }
        throw new RejectedExecutionException(getThreadHandler() + " is shutting down");
    }

    public Handler getHandler() {
        return getThreadHandler();
    }

    public boolean post(Runnable runnable) {
        return getThreadHandler().post(runnable);
    }
}
