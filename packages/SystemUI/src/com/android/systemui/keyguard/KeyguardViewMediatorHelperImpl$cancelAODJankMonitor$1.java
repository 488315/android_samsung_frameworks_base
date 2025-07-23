package com.android.systemui.keyguard;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class KeyguardViewMediatorHelperImpl$cancelAODJankMonitor$1 implements Runnable {
    public final /* synthetic */ KeyguardViewMediatorHelperImpl this$0;

    public KeyguardViewMediatorHelperImpl$cancelAODJankMonitor$1(KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl) {
        this.this$0 = keyguardViewMediatorHelperImpl;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.this$0.interactionJankMonitor.cancel(23);
    }
}
