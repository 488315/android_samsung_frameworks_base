package com.google.android.material.chip;

import android.animation.ValueAnimator;
import android.view.ViewGroup;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class SeslPeoplePicker$$ExternalSyntheticLambda3 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SeslPeoplePicker f$0;
    public final /* synthetic */ float f$1;

    public /* synthetic */ SeslPeoplePicker$$ExternalSyntheticLambda3(SeslPeoplePicker seslPeoplePicker, float f, int i) {
        this.$r8$classId = i;
        this.f$0 = seslPeoplePicker;
        this.f$1 = f;
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        switch (this.$r8$classId) {
            case 0:
                SeslPeoplePicker seslPeoplePicker = this.f$0;
                float f = this.f$1;
                ViewGroup.LayoutParams layoutParams = seslPeoplePicker.mContainer.getLayoutParams();
                layoutParams.height = (int) (((Float) valueAnimator.getAnimatedValue()).floatValue() * f);
                seslPeoplePicker.mContainer.setLayoutParams(layoutParams);
                break;
            default:
                SeslPeoplePicker seslPeoplePicker2 = this.f$0;
                float f2 = this.f$1;
                ViewGroup.LayoutParams layoutParams2 = seslPeoplePicker2.mContainer.getLayoutParams();
                layoutParams2.height = (int) (((Float) valueAnimator.getAnimatedValue()).floatValue() * f2);
                seslPeoplePicker2.mContainer.setLayoutParams(layoutParams2);
                break;
        }
    }
}
