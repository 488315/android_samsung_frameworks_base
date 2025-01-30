package com.android.systemui.keyguard;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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
