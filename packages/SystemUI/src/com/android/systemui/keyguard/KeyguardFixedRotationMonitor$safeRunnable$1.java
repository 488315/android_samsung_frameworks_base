package com.android.systemui.keyguard;

public final class KeyguardFixedRotationMonitor$safeRunnable$1 implements Runnable {
    public final /* synthetic */ KeyguardFixedRotationMonitor this$0;

    public KeyguardFixedRotationMonitor$safeRunnable$1(KeyguardFixedRotationMonitor keyguardFixedRotationMonitor) {
        this.this$0 = keyguardFixedRotationMonitor;
    }

    @Override // java.lang.Runnable
    public final void run() {
        KeyguardFixedRotationMonitor keyguardFixedRotationMonitor = this.this$0;
        Runnable runnable = keyguardFixedRotationMonitor.pendingRunnable;
        if (runnable != null) {
            runnable.run();
            keyguardFixedRotationMonitor.setPendingRunnable(null);
        }
    }
}
