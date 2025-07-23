package com.android.systemui.power;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class HandlerWrapper {
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
