package com.android.server;

import android.os.Handler;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class DisplayThread extends ServiceThread {
    public static Handler sHandler;
    public static DisplayThread sInstance;

    public static void dispose() {
        synchronized (DisplayThread.class) {
            try {
                if (sInstance == null) {
                    return;
                }
                getHandler().runWithScissors(new DisplayThread$$ExternalSyntheticLambda0(), 0L);
                sInstance = null;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static void ensureThreadLocked() {
        if (sInstance == null) {
            DisplayThread displayThread = new DisplayThread(-3, "android.display", false);
            sInstance = displayThread;
            displayThread.start();
            sInstance.getLooper().setTraceTag(524288L);
            sHandler = ServiceThread.makeSharedHandler(sInstance.getLooper());
        }
    }

    public static DisplayThread get() {
        DisplayThread displayThread;
        synchronized (DisplayThread.class) {
            ensureThreadLocked();
            displayThread = sInstance;
        }
        return displayThread;
    }

    public static Handler getHandler() {
        Handler handler;
        synchronized (DisplayThread.class) {
            ensureThreadLocked();
            handler = sHandler;
        }
        return handler;
    }
}
