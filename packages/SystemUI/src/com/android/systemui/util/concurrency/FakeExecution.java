package com.android.systemui.util.concurrency;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class FakeExecution implements Execution {
    public static final int $stable = 8;
    private boolean simulateMainThread = true;

    @Override // com.android.systemui.util.concurrency.Execution
    public void assertIsMainThread() {
        if (!this.simulateMainThread) {
            throw new IllegalStateException("should be called from the main thread");
        }
    }

    public final boolean getSimulateMainThread() {
        return this.simulateMainThread;
    }

    @Override // com.android.systemui.util.concurrency.Execution
    public boolean isMainThread() {
        return this.simulateMainThread;
    }

    public final void setSimulateMainThread(boolean z) {
        this.simulateMainThread = z;
    }
}
