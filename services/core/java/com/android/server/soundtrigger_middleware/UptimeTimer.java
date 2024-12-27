package com.android.server.soundtrigger_middleware;

import android.os.Handler;
import android.os.HandlerThread;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class UptimeTimer {
    public final Handler mHandler;
    public final HandlerThread mHandlerThread;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class TaskImpl {
        public final Handler mHandler;
        public final Object mToken;

        public TaskImpl(Handler handler, Object obj) {
            this.mHandler = handler;
            this.mToken = obj;
        }
    }

    public UptimeTimer() {
        HandlerThread handlerThread = new HandlerThread("SoundTriggerHalWatchdog");
        this.mHandlerThread = handlerThread;
        handlerThread.start();
        this.mHandler = new Handler(handlerThread.getLooper());
    }
}
