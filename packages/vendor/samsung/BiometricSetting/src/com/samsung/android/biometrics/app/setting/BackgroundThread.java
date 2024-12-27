package com.samsung.android.biometrics.app.setting;

import android.os.Handler;
import android.os.HandlerThread;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
public final class BackgroundThread extends HandlerThread {
    public static Handler sHandler;
    public static BackgroundThread sInstance;

    public static BackgroundThread get() {
        if (sInstance == null) {
            synchronized (BackgroundThread.class) {
                try {
                    if (sInstance == null) {
                        BackgroundThread backgroundThread = new BackgroundThread("BioSysUi.bg", 0);
                        sInstance = backgroundThread;
                        backgroundThread.start();
                        sHandler = new Handler(sInstance.getLooper());
                    }
                } finally {
                }
            }
        }
        return sInstance;
    }
}
