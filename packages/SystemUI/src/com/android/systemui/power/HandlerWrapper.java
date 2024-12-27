package com.android.systemui.power;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
