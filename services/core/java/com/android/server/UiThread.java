package com.android.server;

import android.os.Handler;
import android.os.Looper;
import android.os.Process;

import com.samsung.android.rune.CoreRune;

import java.util.Objects;

public final class UiThread extends ServiceThread {
    public static Handler sHandler;
    public static UiThread sInstance;

    public static void dispose() {
        synchronized (UiThread.class) {
            try {
                if (sInstance == null) {
                    return;
                }
                Handler handler = getHandler();
                final UiThread uiThread = sInstance;
                Objects.requireNonNull(uiThread);
                handler.runWithScissors(
                        new Runnable() { // from class:
                                         // com.android.server.UiThread$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                UiThread.this.quit();
                            }
                        },
                        0L);
                sInstance = null;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static void ensureThreadLocked() {
        if (sInstance == null) {
            UiThread uiThread = new UiThread(-2, "android.ui", false);
            sInstance = uiThread;
            uiThread.start();
            Looper looper = sInstance.getLooper();
            looper.setTraceTag(524288L);
            looper.setSlowLogThresholdMs(100L, 200L);
            sHandler = ServiceThread.makeSharedHandler(sInstance.getLooper());
        }
    }

    public static UiThread get() {
        UiThread uiThread;
        synchronized (UiThread.class) {
            ensureThreadLocked();
            uiThread = sInstance;
        }
        return uiThread;
    }

    public static Handler getHandler() {
        Handler handler;
        synchronized (UiThread.class) {
            ensureThreadLocked();
            handler = sHandler;
        }
        return handler;
    }

    @Override // com.android.server.ServiceThread, android.os.HandlerThread, java.lang.Thread,
              // java.lang.Runnable
    public final void run() {
        Process.setThreadGroup(Process.myTid(), CoreRune.SYSPERF_BOOST_OPT ? 10 : 5);
        super.run();
    }
}
