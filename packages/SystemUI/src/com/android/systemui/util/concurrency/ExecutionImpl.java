package com.android.systemui.util.concurrency;

import android.os.Looper;
import androidx.core.provider.FontProvider$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ExecutionImpl implements Execution {
    public static final int $stable = 8;
    private final Looper mainLooper = Looper.getMainLooper();

    @Override // com.android.systemui.util.concurrency.Execution
    public void assertIsMainThread() {
        if (!this.mainLooper.isCurrentThread()) {
            throw new IllegalStateException(FontProvider$$ExternalSyntheticOutline0.m("should be called from the main thread. Main thread name=", this.mainLooper.getThread().getName(), " Thread.currentThread()=", Thread.currentThread().getName()));
        }
    }

    @Override // com.android.systemui.util.concurrency.Execution
    public boolean isMainThread() {
        return this.mainLooper.isCurrentThread();
    }
}
