package com.android.app.viewcapture;

import android.os.HandlerThread;
import android.view.Choreographer;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SimpleViewCapture extends ViewCapture {
    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public SimpleViewCapture(String str) {
        super(2000, 300, r0, new LooperExecutor(r1.getLooper()));
        LooperExecutor looperExecutor = ViewCapture.MAIN_EXECUTOR;
        looperExecutor.getClass();
        CallableC06231 callableC06231 = new Callable() { // from class: com.android.app.viewcapture.SimpleViewCapture.1
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return Choreographer.getInstance();
            }
        };
        callableC06231.getClass();
        FutureTask futureTask = new FutureTask(callableC06231);
        looperExecutor.execute(futureTask);
        Choreographer choreographer = (Choreographer) futureTask.get();
        HandlerThread handlerThread = new HandlerThread(str, -2);
        handlerThread.start();
    }
}
