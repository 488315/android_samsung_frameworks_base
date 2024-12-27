package com.android.systemui.keyguard;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
