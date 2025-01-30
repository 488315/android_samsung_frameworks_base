package com.android.p038wm.shell.freeform;

import android.animation.ValueAnimator;
import android.widget.ImageView;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class FreeformContainerView$$ExternalSyntheticLambda3 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ FreeformContainerView f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ FreeformContainerView$$ExternalSyntheticLambda3(FreeformContainerView freeformContainerView, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = freeformContainerView;
        this.f$1 = obj;
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        switch (this.$r8$classId) {
            case 0:
                FreeformContainerView freeformContainerView = this.f$0;
                ImageView imageView = (ImageView) this.f$1;
                if (!freeformContainerView.mIsAppIconMoving) {
                    imageView.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
                    break;
                }
                break;
            case 1:
                FreeformContainerView freeformContainerView2 = this.f$0;
                ImageView imageView2 = (ImageView) this.f$1;
                if (!freeformContainerView2.mIsAppIconMoving) {
                    float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                    imageView2.setScaleX(floatValue);
                    imageView2.setScaleY(floatValue);
                    break;
                }
                break;
            case 2:
                FreeformContainerView freeformContainerView3 = this.f$0;
                ImageView imageView3 = (ImageView) this.f$1;
                if (!freeformContainerView3.mIsAppIconMoving) {
                    imageView3.setY(((Float) valueAnimator.getAnimatedValue()).floatValue());
                    break;
                }
                break;
            default:
                FreeformContainerView freeformContainerView4 = this.f$0;
                ValueAnimator valueAnimator2 = (ValueAnimator) this.f$1;
                float[] fArr = FreeformContainerView.TAIL_ICON_ALPHA_ARRAY;
                freeformContainerView4.getClass();
                freeformContainerView4.mBackgroundDimView.getBackground().setAlpha(((Integer) valueAnimator2.getAnimatedValue()).intValue());
                break;
        }
    }
}
