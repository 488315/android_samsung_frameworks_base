package com.android.app.viewcapture;

import android.os.HandlerThread;

/* loaded from: classes.dex */
public class SimpleViewCapture extends ViewCapture {
    public SimpleViewCapture(String str) {
        HandlerThread handlerThread = new HandlerThread(str, -2);
        handlerThread.start();
        super(2000, 300, new LooperExecutor(handlerThread.getLooper()));
    }
}
