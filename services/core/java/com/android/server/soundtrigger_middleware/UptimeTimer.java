package com.android.server.soundtrigger_middleware;

import android.os.Handler;
import android.os.HandlerThread;

public final class UptimeTimer {
    public final Handler mHandler;
    public final HandlerThread mHandlerThread;

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
