package com.samsung.android.biometrics.app.setting;

import android.os.Handler;
import android.os.HandlerThread;

/* loaded from: classes.dex */
public final class BackgroundThread extends HandlerThread {
    private static Handler sHandler;
    private static BackgroundThread sInstance;

    private BackgroundThread() {
        super("BioSysUi.bg", 0);
    }

    public static BackgroundThread get() {
        if (sInstance == null) {
            synchronized (BackgroundThread.class) {
                if (sInstance == null) {
                    BackgroundThread backgroundThread = new BackgroundThread();
                    sInstance = backgroundThread;
                    backgroundThread.start();
                    sHandler = new Handler(sInstance.getLooper());
                }
            }
        }
        return sInstance;
    }

    public static Handler getHandler() {
        return sHandler;
    }

    public static void post(Runnable runnable) {
        sHandler.post(runnable);
    }
}
