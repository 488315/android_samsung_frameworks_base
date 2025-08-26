package com.android.systemui.keyguard;

/* loaded from: classes2.dex */
public final /* synthetic */ class KeyguardViewMediator$$ExternalSyntheticLambda64 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ KeyguardViewMediatorHelperImpl f$0;

    public /* synthetic */ KeyguardViewMediator$$ExternalSyntheticLambda64(KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl, int i) {
        this.$r8$classId = i;
        this.f$0 = keyguardViewMediatorHelperImpl;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.f$0;
        switch (i) {
            case 0:
                keyguardViewMediatorHelperImpl.removeShowMsgOnCoverOpened();
                break;
            default:
                keyguardViewMediatorHelperImpl.doKeyguardPendingIntent = null;
                break;
        }
    }
}
