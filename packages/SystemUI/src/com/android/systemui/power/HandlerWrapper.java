package com.android.systemui.power;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

public final class HandlerWrapper {
    public final Handler mWorker;
    public final HandlerThread mWorkerThread;

    public HandlerWrapper() {
        new Handler(Looper.getMainLooper());
        HandlerThread handlerThread = new HandlerThread("PowerUIHandlerWrapper");
        this.mWorkerThread = handlerThread;
        handlerThread.start();
        this.mWorker = new Handler(handlerThread.getLooper());
    }
}
