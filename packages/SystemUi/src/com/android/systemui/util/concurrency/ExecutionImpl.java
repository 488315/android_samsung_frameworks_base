package com.android.systemui.util.concurrency;

import android.os.Looper;
import androidx.core.provider.FontProvider$$ExternalSyntheticOutline0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ExecutionImpl implements Execution {
    public final Looper mainLooper = Looper.getMainLooper();

    public final void assertIsMainThread() {
        Looper looper = this.mainLooper;
        if (!looper.isCurrentThread()) {
            throw new IllegalStateException(FontProvider$$ExternalSyntheticOutline0.m32m("should be called from the main thread. Main thread name=", looper.getThread().getName(), " Thread.currentThread()=", Thread.currentThread().getName()));
        }
    }
}
