package com.android.systemui.keyguard;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
