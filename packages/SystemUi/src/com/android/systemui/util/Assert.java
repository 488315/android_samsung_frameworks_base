package com.android.systemui.util;

import android.os.Looper;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class Assert {
    public static final Looper sMainLooper = Looper.getMainLooper();
    public static Thread sTestThread = null;

    public static void isMainThread() {
        Looper looper = sMainLooper;
        if (looper.isCurrentThread()) {
            return;
        }
        Thread thread = sTestThread;
        if (thread == null || thread != Thread.currentThread()) {
            throw new IllegalStateException("should be called from the main thread. sMainLooper.threadName=" + looper.getThread().getName() + " Thread.currentThread()=" + Thread.currentThread().getName());
        }
    }

    public static void isNotMainThread() {
        if (sMainLooper.isCurrentThread()) {
            Thread thread = sTestThread;
            if (thread == null || thread == Thread.currentThread()) {
                throw new IllegalStateException("should not be called from the main thread.");
            }
        }
    }

    public static void setTestThread(Thread thread) {
        sTestThread = thread;
    }

    public static void setTestableLooper(Looper looper) {
        setTestThread(looper == null ? null : looper.getThread());
    }
}
