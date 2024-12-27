package com.android.systemui.statusbar;

import android.animation.ValueAnimator;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class ViewTransformationHelper$$ExternalSyntheticLambda0 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ViewTransformationHelper f$0;
    public final /* synthetic */ TransformableView f$1;

    public /* synthetic */ ViewTransformationHelper$$ExternalSyntheticLambda0(ViewTransformationHelper viewTransformationHelper, TransformableView transformableView, int i) {
        this.$r8$classId = i;
        this.f$0 = viewTransformationHelper;
        this.f$1 = transformableView;
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        switch (this.$r8$classId) {
            case 0:
                ViewTransformationHelper viewTransformationHelper = this.f$0;
                TransformableView transformableView = this.f$1;
                viewTransformationHelper.getClass();
                viewTransformationHelper.transformTo(valueAnimator.getAnimatedFraction(), transformableView);
                break;
            default:
                ViewTransformationHelper viewTransformationHelper2 = this.f$0;
                TransformableView transformableView2 = this.f$1;
                viewTransformationHelper2.getClass();
                viewTransformationHelper2.transformFrom(valueAnimator.getAnimatedFraction(), transformableView2);
                break;
        }
    }
}
