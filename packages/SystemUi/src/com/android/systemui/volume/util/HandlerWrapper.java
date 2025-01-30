package com.android.systemui.volume.util;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class HandlerWrapper {
    public final Lazy workerThread$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.volume.util.HandlerWrapper$workerThread$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            HandlerThread handlerThread = new HandlerThread("VolumeHandlerWrapper");
            handlerThread.start();
            return handlerThread;
        }
    });
    public final Lazy mainThreadHandler$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.volume.util.HandlerWrapper$mainThreadHandler$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return new Handler(Looper.getMainLooper());
        }
    });
    public final Lazy workerThreadHandler$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.volume.util.HandlerWrapper$workerThreadHandler$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return new Handler(((HandlerThread) HandlerWrapper.this.workerThread$delegate.getValue()).getLooper());
        }
    });

    public final void postDelayed(long j, Runnable runnable) {
        ((Handler) this.mainThreadHandler$delegate.getValue()).postDelayed(runnable, j);
    }

    public final void postInBgThread(Runnable runnable) {
        ((Handler) this.workerThreadHandler$delegate.getValue()).post(runnable);
    }

    public final void remove(Runnable runnable) {
        ((Handler) this.mainThreadHandler$delegate.getValue()).removeCallbacks(runnable);
    }
}
