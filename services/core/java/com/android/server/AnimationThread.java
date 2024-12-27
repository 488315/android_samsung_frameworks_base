package com.android.server;

import android.os.Handler;

public final class AnimationThread extends ServiceThread {
    public static Handler sHandler;
    public static AnimationThread sInstance;

    public static void dispose() {
        synchronized (AnimationThread.class) {
            try {
                if (sInstance == null) {
                    return;
                }
                getHandler().runWithScissors(new AnimationThread$$ExternalSyntheticLambda0(), 0L);
                sInstance = null;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static void ensureThreadLocked() {
        if (sInstance == null) {
            AnimationThread animationThread = new AnimationThread(-4, "android.anim", false);
            sInstance = animationThread;
            animationThread.start();
            sInstance.getLooper().setTraceTag(32L);
            sHandler = ServiceThread.makeSharedHandler(sInstance.getLooper());
        }
    }

    public static AnimationThread get() {
        AnimationThread animationThread;
        synchronized (AnimationThread.class) {
            ensureThreadLocked();
            animationThread = sInstance;
        }
        return animationThread;
    }

    public static Handler getHandler() {
        Handler handler;
        synchronized (AnimationThread.class) {
            ensureThreadLocked();
            handler = sHandler;
        }
        return handler;
    }
}
