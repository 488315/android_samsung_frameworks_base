package com.android.server.wm;

import android.os.Handler;
import com.android.server.ServiceThread;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class SurfaceAnimationThread extends ServiceThread {
    public static Handler sHandler;
    public static SurfaceAnimationThread sInstance;

    public static void dispose() {
        synchronized (SurfaceAnimationThread.class) {
            try {
                if (sInstance == null) {
                    return;
                }
                getHandler().runWithScissors(new SurfaceAnimationThread$$ExternalSyntheticLambda0(), 0L);
                sInstance = null;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static void ensureThreadLocked() {
        if (sInstance == null) {
            SurfaceAnimationThread surfaceAnimationThread = new SurfaceAnimationThread(-4, "android.anim.lf", false);
            sInstance = surfaceAnimationThread;
            surfaceAnimationThread.start();
            sInstance.getLooper().setTraceTag(32L);
            sHandler = ServiceThread.makeSharedHandler(sInstance.getLooper());
        }
    }

    public static Handler getHandler() {
        Handler handler;
        synchronized (SurfaceAnimationThread.class) {
            ensureThreadLocked();
            handler = sHandler;
        }
        return handler;
    }
}
