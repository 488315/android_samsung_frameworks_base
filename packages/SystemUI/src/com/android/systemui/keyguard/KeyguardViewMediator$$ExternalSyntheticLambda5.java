package com.android.systemui.keyguard;

import android.animation.ValueAnimator;
import android.app.trust.TrustManager;
import com.android.systemui.keyguard.KeyguardViewMediator;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class KeyguardViewMediator$$ExternalSyntheticLambda5 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ KeyguardViewMediator$$ExternalSyntheticLambda5(Object obj, int i) {
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
                ValueAnimator valueAnimator = ((KeyguardViewMediator.AnonymousClass8) obj).mOccludeByDreamAnimator;
                if (valueAnimator != null) {
                    valueAnimator.cancel();
                    break;
                }
                break;
            default:
                ValueAnimator valueAnimator2 = ((KeyguardViewMediator.AnonymousClass9) obj).mUnoccludeAnimator;
                if (valueAnimator2 != null) {
                    valueAnimator2.cancel();
                    break;
                }
                break;
        }
    }
}
