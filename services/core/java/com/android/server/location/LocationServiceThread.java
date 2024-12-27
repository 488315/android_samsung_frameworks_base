package com.android.server.location;

import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.Looper;
import com.android.server.ServiceThread;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class LocationServiceThread extends ServiceThread {
    public static Handler sHandler;
    public static HandlerExecutor sHandlerExecutor;
    public static LocationServiceThread sInstance;

    public static synchronized void ensureThreadLocked() {
        synchronized (LocationServiceThread.class) {
            if (sInstance == null) {
                LocationServiceThread locationServiceThread = new LocationServiceThread(0, "LocationProviderManagerThread", true);
                sInstance = locationServiceThread;
                locationServiceThread.start();
                Looper looper = sInstance.getLooper();
                looper.setTraceTag(524288L);
                looper.setSlowLogThresholdMs(100L, 200L);
                sHandler = new Handler(sInstance.getLooper());
                sHandlerExecutor = new HandlerExecutor(sHandler);
            }
        }
    }

    public static Executor getExecutor() {
        HandlerExecutor handlerExecutor;
        synchronized (LocationServiceThread.class) {
            ensureThreadLocked();
            handlerExecutor = sHandlerExecutor;
        }
        return handlerExecutor;
    }

    public static Handler getHandler() {
        Handler handler;
        synchronized (LocationServiceThread.class) {
            ensureThreadLocked();
            handler = sHandler;
        }
        return handler;
    }
}
