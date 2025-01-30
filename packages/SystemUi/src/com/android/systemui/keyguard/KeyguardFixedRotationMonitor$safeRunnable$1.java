package com.android.systemui.keyguard;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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
