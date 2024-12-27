package com.android.server.location.nsflp;

import android.os.Handler;
import android.os.HandlerExecutor;

import com.android.server.ServiceThread;

public final class NSLocationThread extends ServiceThread {
    public static Handler sHandler;
    public static HandlerExecutor sHandlerExecutor;
    public static NSLocationThread sInstance;

    public static synchronized void ensureThreadLocked() {
        synchronized (NSLocationThread.class) {
            if (sInstance == null) {
                NSLocationThread nSLocationThread =
                        new NSLocationThread(0, "NSLocationThread", true);
                sInstance = nSLocationThread;
                nSLocationThread.start();
                sHandler = new Handler(sInstance.getLooper());
                sHandlerExecutor = new HandlerExecutor(sHandler);
            }
        }
    }
}
