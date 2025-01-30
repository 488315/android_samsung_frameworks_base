package com.android.systemui.keyguard;

import android.animation.ValueAnimator;
import android.view.IRemoteAnimationRunner;
import com.android.systemui.keyguard.KeyguardViewMediator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class KeyguardViewMediator$7$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ IRemoteAnimationRunner.Stub f$0;

    public /* synthetic */ KeyguardViewMediator$7$$ExternalSyntheticLambda0(IRemoteAnimationRunner.Stub stub, int i) {
        this.$r8$classId = i;
        this.f$0 = stub;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ValueAnimator valueAnimator = ((KeyguardViewMediator.C14887) this.f$0).mOccludeByDreamAnimator;
                if (valueAnimator != null) {
                    valueAnimator.cancel();
                    break;
                }
                break;
            default:
                ValueAnimator valueAnimator2 = ((KeyguardViewMediator.C14898) this.f$0).mUnoccludeAnimator;
                if (valueAnimator2 != null) {
                    valueAnimator2.cancel();
                    break;
                }
                break;
        }
    }
}
