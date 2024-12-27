package com.android.systemui.util.concurrency;

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
