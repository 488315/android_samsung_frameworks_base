package com.android.wm.shell.common.split;

import android.animation.ValueAnimator;
import com.android.wm.shell.common.split.DividerResizeLayout;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class DividerResizeLayout$$ExternalSyntheticLambda3 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ DividerResizeLayout$$ExternalSyntheticLambda3(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        switch (this.$r8$classId) {
            case 0:
                DividerResizeLayout dividerResizeLayout = (DividerResizeLayout) this.f$0;
                boolean z = DividerResizeLayout.DEBUG;
                dividerResizeLayout.getClass();
                dividerResizeLayout.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
                break;
            default:
                DividerResizeLayout.DividerResizeTarget dividerResizeTarget = (DividerResizeLayout.DividerResizeTarget) this.f$0;
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
