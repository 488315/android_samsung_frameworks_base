package com.android.wm.shell.common.split;

import android.animation.ValueAnimator;
import com.android.wm.shell.common.split.DividerResizeLayout;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class DividerResizeLayout$$ExternalSyntheticLambda3 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ DividerResizeLayout$$ExternalSyntheticLambda3(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                DividerResizeLayout dividerResizeLayout = (DividerResizeLayout) obj;
                float[] fArr = DividerResizeLayout.BLUR_PRESET;
                dividerResizeLayout.getClass();
                dividerResizeLayout.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
                break;
            default:
                DividerResizeLayout.DividerResizeTarget dividerResizeTarget = (DividerResizeLayout.DividerResizeTarget) obj;
                dividerResizeTarget.getClass();
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                dividerResizeTarget.mBlurView.setAlpha(floatValue);
                if (DividerResizeController.USE_GUIDE_VIEW_EFFECTS) {
                    DividerResizeLayout.this.mGuideBarView.setAlpha(floatValue);
                    break;
                }
                break;
        }
    }
}
