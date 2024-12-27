package com.android.systemui.statusbar.policy;

import androidx.core.animation.Animator;
import androidx.core.animation.ValueAnimator;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class RemoteInputView$$ExternalSyntheticLambda2 implements Animator.AnimatorUpdateListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ RemoteInputView f$0;
    public final /* synthetic */ ValueAnimator f$1;

    public /* synthetic */ RemoteInputView$$ExternalSyntheticLambda2(RemoteInputView remoteInputView, ValueAnimator valueAnimator, int i) {
        this.$r8$classId = i;
        this.f$0 = remoteInputView;
        this.f$1 = valueAnimator;
    }

    @Override // androidx.core.animation.Animator.AnimatorUpdateListener
    public final void onAnimationUpdate(Animator animator) {
        ValueAnimator valueAnimator = this.f$1;
        RemoteInputView remoteInputView = this.f$0;
        switch (this.$r8$classId) {
            case 0:
                Object obj = RemoteInputView.VIEW_TAG;
                remoteInputView.getClass();
                remoteInputView.setFocusAnimationScaleY(((Float) valueAnimator.getAnimatedValue()).floatValue());
                break;
            default:
                Object obj2 = RemoteInputView.VIEW_TAG;
                remoteInputView.getClass();
                remoteInputView.setFocusAnimationScaleY(((Float) valueAnimator.getAnimatedValue()).floatValue());
                break;
        }
    }
}
