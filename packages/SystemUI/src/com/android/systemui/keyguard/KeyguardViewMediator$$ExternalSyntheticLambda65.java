package com.android.systemui.keyguard;

import android.content.Intent;
import com.android.systemui.keyguard.KeyguardViewMediator;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class KeyguardViewMediator$$ExternalSyntheticLambda65 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ KeyguardViewMediator$$ExternalSyntheticLambda65(Object obj, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = obj;
        this.f$1 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) this.f$0;
                int i = this.f$1;
                if (keyguardViewMediator.mLockPatternUtils.isSecure(i)) {
                    keyguardViewMediator.mLockPatternUtils.getDevicePolicyManager().reportKeyguardDismissed(i);
                    break;
                }
                break;
            default:
                KeyguardViewMediator.AnonymousClass15 anonymousClass15 = (KeyguardViewMediator.AnonymousClass15) this.f$0;
                int i2 = this.f$1;
                Intent intent = KeyguardViewMediator.USER_PRESENT_INTENT;
                KeyguardViewMediator.this.mHelper.setKeyguardGoingAway(i2);
                break;
        }
    }
}
