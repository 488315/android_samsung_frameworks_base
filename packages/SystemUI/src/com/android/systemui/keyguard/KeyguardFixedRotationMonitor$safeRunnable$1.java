package com.android.systemui.keyguard;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
