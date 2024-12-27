package com.android.systemui.util;

import android.os.Looper;

public class Assert {
    private static final Looper sMainLooper = Looper.getMainLooper();
    private static Thread sTestThread = null;

    public static void isCurrentThread(Looper looper) {
        if (looper.isCurrentThread()) {
            return;
        }
        Thread thread = sTestThread;
        if (thread == null || thread != Thread.currentThread()) {
            throw new IllegalStateException("Called on wrong thread thread. wanted " + looper.getThread().getName() + " but instead got Thread.currentThread()=" + Thread.currentThread().getName());
        }
    }

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

    public static void runWithCurrentThreadAsMainThread(Runnable runnable) {
        if (sMainLooper.isCurrentThread()) {
            runnable.run();
            return;
        }
        Thread currentThread = Thread.currentThread();
        Thread thread = sTestThread;
        if (thread == currentThread) {
            runnable.run();
            return;
        }
        if (thread == null) {
            sTestThread = currentThread;
            runnable.run();
            sTestThread = null;
        } else {
            throw new AssertionError("Can't run with current thread (" + currentThread + ") as main thread; test thread is already set to " + thread);
        }
    }

    public static void setTestThread(Thread thread) {
        sTestThread = thread;
    }

    public static void setTestableLooper(Looper looper) {
        setTestThread(looper == null ? null : looper.getThread());
    }
}
