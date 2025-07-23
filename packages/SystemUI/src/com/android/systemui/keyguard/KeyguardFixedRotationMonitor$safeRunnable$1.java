package com.android.systemui.keyguard;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
