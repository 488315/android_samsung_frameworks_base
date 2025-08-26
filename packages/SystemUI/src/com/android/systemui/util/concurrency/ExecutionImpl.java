package com.android.systemui.util.concurrency;

import android.os.Looper;
import androidx.vectordrawable.graphics.drawable.AnimatorInflaterCompat$$ExternalSyntheticOutline0;

/* loaded from: classes3.dex */
public final class ExecutionImpl implements Execution {
    public static final int $stable = 8;
    private final Looper mainLooper = Looper.getMainLooper();

    @Override // com.android.systemui.util.concurrency.Execution
    public void assertIsMainThread() {
        if (!this.mainLooper.isCurrentThread()) {
            throw new IllegalStateException(AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("should be called from the main thread. Main thread name=", this.mainLooper.getThread().getName(), " Thread.currentThread()=", Thread.currentThread().getName()));
        }
    }

    @Override // com.android.systemui.util.concurrency.Execution
    public boolean isMainThread() {
        return this.mainLooper.isCurrentThread();
    }
}
