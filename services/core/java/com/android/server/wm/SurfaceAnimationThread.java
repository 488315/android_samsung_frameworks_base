package com.android.server.wm;

import android.os.Handler;
import com.android.server.ServiceThread;

/* loaded from: classes3.dex */
public final class SurfaceAnimationThread extends ServiceThread {
    public static Handler sHandler;
    public static SurfaceAnimationThread sInstance;

    public SurfaceAnimationThread() {
        super("android.anim.lf", -4, false);
    }

    public static void ensureThreadLocked() {
        if (sInstance == null) {
            SurfaceAnimationThread surfaceAnimationThread = new SurfaceAnimationThread();
            sInstance = surfaceAnimationThread;
            surfaceAnimationThread.start();
            sInstance.getLooper().setTraceTag(32L);
            sHandler = ServiceThread.makeSharedHandler(sInstance.getLooper());
        }
    }

    public static SurfaceAnimationThread get() {
        SurfaceAnimationThread surfaceAnimationThread;
        synchronized (SurfaceAnimationThread.class) {
            ensureThreadLocked();
            surfaceAnimationThread = sInstance;
        }
        return surfaceAnimationThread;
    }

    public static Handler getHandler() {
        Handler handler;
        synchronized (SurfaceAnimationThread.class) {
            ensureThreadLocked();
            handler = sHandler;
        }
        return handler;
    }

    public static void dispose() {
        synchronized (SurfaceAnimationThread.class) {
            if (sInstance == null) {
                return;
            }
            getHandler().runWithScissors(new Runnable() { // from class: com.android.server.wm.SurfaceAnimationThread$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    SurfaceAnimationThread.lambda$dispose$0();
                }
            }, 0L);
            sInstance = null;
        }
    }

    public static /* synthetic */ void lambda$dispose$0() {
        sInstance.quit();
    }
}
