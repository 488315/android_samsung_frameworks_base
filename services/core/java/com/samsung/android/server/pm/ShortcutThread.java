package com.samsung.android.server.pm;

import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.Looper;
import com.android.server.ServiceThread;

/* loaded from: classes2.dex */
public final class ShortcutThread extends ServiceThread {
    public static Handler sHandler;
    public static HandlerExecutor sHandlerExecutor;
    public static ShortcutThread sInstance;
    public static final Object sLock = new Object();

    public ShortcutThread() {
        super("ShortcutService", 0, true);
    }

    public static void ensureThreadLocked() {
        if (sInstance != null) {
            return;
        }
        ShortcutThread shortcutThread = new ShortcutThread();
        sInstance = shortcutThread;
        shortcutThread.start();
        Looper looper = sInstance.getLooper();
        looper.setTraceTag(524288L);
        looper.setSlowLogThresholdMs(100L, 200L);
        sHandler = new Handler(sInstance.getLooper());
        sHandlerExecutor = new HandlerExecutor(sHandler);
    }

    public static ShortcutThread get() {
        ShortcutThread shortcutThread;
        synchronized (sLock) {
            ensureThreadLocked();
            shortcutThread = sInstance;
        }
        return shortcutThread;
    }
}
