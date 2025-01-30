package com.android.systemui.keyguard;

import android.animation.ValueAnimator;
import android.view.IRemoteAnimationRunner;
import com.android.systemui.keyguard.SafeUIKeyguardViewMediator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class SafeUIKeyguardViewMediator$7$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ IRemoteAnimationRunner.Stub f$0;

    public /* synthetic */ SafeUIKeyguardViewMediator$7$$ExternalSyntheticLambda1(IRemoteAnimationRunner.Stub stub, int i) {
        this.$r8$classId = i;
        this.f$0 = stub;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ValueAnimator valueAnimator = ((SafeUIKeyguardViewMediator.C15157) this.f$0).mOccludeByDreamAnimator;
                if (valueAnimator != null) {
                    valueAnimator.cancel();
                    break;
                }
                break;
            default:
                SafeUIKeyguardViewMediator.C15168 c15168 = (SafeUIKeyguardViewMediator.C15168) this.f$0;
                int i = SafeUIKeyguardViewMediator.C15168.$r8$clinit;
                c15168.getClass();
                break;
        }
    }
}
