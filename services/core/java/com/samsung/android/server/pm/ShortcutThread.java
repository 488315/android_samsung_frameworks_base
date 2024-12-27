package com.samsung.android.server.pm;

import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.Looper;
import com.android.server.ServiceThread;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class ShortcutThread extends ServiceThread {
    public static Handler sHandler;
    public static ShortcutThread sInstance;
    public static final Object sLock = new Object();

    public static void ensureThreadLocked() {
        if (sInstance != null) {
            return;
        }
        ShortcutThread shortcutThread = new ShortcutThread(0, "ShortcutService", true);
        sInstance = shortcutThread;
        shortcutThread.start();
        Looper looper = sInstance.getLooper();
        looper.setTraceTag(524288L);
        looper.setSlowLogThresholdMs(100L, 200L);
        sHandler = new Handler(sInstance.getLooper());
        new HandlerExecutor(sHandler);
    }
}
