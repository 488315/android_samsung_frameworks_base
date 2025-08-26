package com.android.systemui.volume.util;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;

/* loaded from: classes3.dex */
public final class HandlerWrapper {
    public final Lazy mainThreadHandler$delegate;
    public final Lazy workerThread$delegate;
    public final Lazy workerThreadHandler$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.volume.util.HandlerWrapper$$ExternalSyntheticLambda2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return new Handler(((HandlerThread) this.f$0.workerThread$delegate.getValue()).getLooper());
        }
    });

    public HandlerWrapper() {
        final int i = 0;
        this.workerThread$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.volume.util.HandlerWrapper$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i) {
                    case 0:
                        HandlerThread handlerThread = new HandlerThread("VolumeHandlerWrapper");
                        handlerThread.start();
                        return handlerThread;
                    default:
                        return new Handler(Looper.getMainLooper());
                }
            }
        });
        final int i2 = 1;
        this.mainThreadHandler$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.volume.util.HandlerWrapper$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i2) {
                    case 0:
                        HandlerThread handlerThread = new HandlerThread("VolumeHandlerWrapper");
                        handlerThread.start();
                        return handlerThread;
                    default:
                        return new Handler(Looper.getMainLooper());
                }
            }
        });
    }

    public final void post(Runnable runnable) {
        ((Handler) this.mainThreadHandler$delegate.getValue()).post(runnable);
    }

    public final void postDelayed(Runnable runnable, long j) {
        ((Handler) this.mainThreadHandler$delegate.getValue()).postDelayed(runnable, j);
    }

    public final void postInBgThread(Runnable runnable) {
        ((Handler) this.workerThreadHandler$delegate.getValue()).post(runnable);
    }

    public final void remove(Runnable runnable) {
        ((Handler) this.mainThreadHandler$delegate.getValue()).removeCallbacks(runnable);
    }
}
