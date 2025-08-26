package com.android.systemui.keyguard;

/* loaded from: classes2.dex */
public final class KeyguardFixedRotationMonitor$safeRunnable$1 implements Runnable {
    public final /* synthetic */ KeyguardFixedRotationMonitor this$0;

    public KeyguardFixedRotationMonitor$safeRunnable$1(KeyguardFixedRotationMonitor keyguardFixedRotationMonitor) {
        this.this$0 = keyguardFixedRotationMonitor;
    }

    @Override // java.lang.Runnable
    public final void run() {
        KeyguardFixedRotationMonitor keyguardFixedRotationMonitor = this.this$0;
        KeyguardViewMediatorHelperImpl$showForegroundImmediatelyIfNeeded$1$1 keyguardViewMediatorHelperImpl$showForegroundImmediatelyIfNeeded$1$1 = keyguardFixedRotationMonitor.pendingRunnable;
        if (keyguardViewMediatorHelperImpl$showForegroundImmediatelyIfNeeded$1$1 != null) {
            keyguardViewMediatorHelperImpl$showForegroundImmediatelyIfNeeded$1$1.run();
            keyguardFixedRotationMonitor.setPendingRunnable(null);
        }
    }
}
