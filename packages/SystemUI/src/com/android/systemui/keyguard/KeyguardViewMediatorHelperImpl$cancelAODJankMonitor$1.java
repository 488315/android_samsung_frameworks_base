package com.android.systemui.keyguard;

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
