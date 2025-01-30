package com.android.systemui.util.concurrency;

import android.os.HandlerThread;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ThreadFactoryImpl implements ThreadFactory {
    public final ExecutorImpl buildExecutorOnNewThread(String str) {
        HandlerThread handlerThread = new HandlerThread(str);
        handlerThread.start();
        return new ExecutorImpl(handlerThread.getLooper());
    }
}
