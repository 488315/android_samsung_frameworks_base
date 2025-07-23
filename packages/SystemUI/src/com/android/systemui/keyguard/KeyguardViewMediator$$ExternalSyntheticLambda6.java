package com.android.systemui.keyguard;

import android.animation.ValueAnimator;
import android.app.trust.TrustManager;
import com.android.systemui.keyguard.KeyguardViewMediator;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class KeyguardViewMediator$$ExternalSyntheticLambda6 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ KeyguardViewMediator$$ExternalSyntheticLambda6(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                ((TrustManager) obj).reportKeyguardShowingChanged();
                break;
            case 1:
                ValueAnimator valueAnimator = ((KeyguardViewMediator.AnonymousClass10) obj).mUnoccludeAnimator;
                if (valueAnimator != null) {
                    valueAnimator.cancel();
                    break;
                }
                break;
            default:
                ValueAnimator valueAnimator2 = ((KeyguardViewMediator.AnonymousClass9) obj).mOccludeByDreamAnimator;
                if (valueAnimator2 != null) {
                    valueAnimator2.cancel();
                    break;
                }
                break;
        }
    }
}
