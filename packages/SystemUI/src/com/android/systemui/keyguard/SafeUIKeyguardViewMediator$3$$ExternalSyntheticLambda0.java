package com.android.systemui.keyguard;

import android.animation.ValueAnimator;
import com.android.systemui.keyguard.SafeUIKeyguardViewMediator;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class SafeUIKeyguardViewMediator$3$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ SafeUIKeyguardViewMediator$3$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                ((SafeUIKeyguardViewMediator.AnonymousClass3) obj).this$0.dismiss(null, null);
                break;
            case 1:
                ValueAnimator valueAnimator = ((SafeUIKeyguardViewMediator.AnonymousClass7) obj).mOccludeByDreamAnimator;
                if (valueAnimator != null) {
                    valueAnimator.cancel();
                    break;
                }
                break;
            default:
                int i2 = SafeUIKeyguardViewMediator.AnonymousClass8.$r8$clinit;
                ((SafeUIKeyguardViewMediator.AnonymousClass8) obj).getClass();
                break;
        }
    }
}
