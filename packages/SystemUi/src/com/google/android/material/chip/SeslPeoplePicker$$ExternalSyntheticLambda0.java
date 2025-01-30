package com.google.android.material.chip;

import android.animation.ValueAnimator;
import android.content.Context;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import com.android.systemui.R;
import com.google.android.material.chip.SeslPeoplePicker.C42574;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class SeslPeoplePicker$$ExternalSyntheticLambda0 {
    public final /* synthetic */ SeslPeoplePicker f$0;

    public /* synthetic */ SeslPeoplePicker$$ExternalSyntheticLambda0(SeslPeoplePicker seslPeoplePicker) {
        this.f$0 = seslPeoplePicker;
    }

    public final void onClick() {
        final int i;
        final SeslPeoplePicker seslPeoplePicker = this.f$0;
        int internalHeight = seslPeoplePicker.mChipGroup.getInternalHeight(seslPeoplePicker.getWidth());
        int paddingBottom = seslPeoplePicker.mChipGroup.getPaddingBottom() + seslPeoplePicker.mChipGroup.getPaddingTop() + seslPeoplePicker.mChipGroup.getChildAt(0).getHeight();
        if (seslPeoplePicker.mContainer.mExpanded) {
            i = paddingBottom;
        } else {
            i = internalHeight;
            internalHeight = paddingBottom;
        }
        final float f = internalHeight - i;
        Context context = seslPeoplePicker.getContext();
        final int integer = context.getResources().getInteger(R.integer.sesl_chip_default_anim_duration);
        final int integer2 = context.getResources().getInteger(R.integer.sesl_people_picker_alpha_duration);
        final int integer3 = context.getResources().getInteger(R.integer.sesl_people_picker_alpha_delay);
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        ofFloat.setDuration(integer);
        ofFloat.setInterpolator(AnimationUtils.loadInterpolator(context, R.interpolator.sesl_chip_default_interpolator));
        ofFloat.addListener(seslPeoplePicker.new C42574());
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.chip.SeslPeoplePicker$$ExternalSyntheticLambda4
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                SeslPeoplePicker seslPeoplePicker2 = SeslPeoplePicker.this;
                int i2 = i;
                float f2 = f;
                int i3 = integer;
                int i4 = integer3;
                int i5 = integer2;
                ViewGroup.LayoutParams layoutParams = seslPeoplePicker2.mContainer.getLayoutParams();
                layoutParams.height = i2 + ((int) (((Float) valueAnimator.getAnimatedValue()).floatValue() * f2));
                seslPeoplePicker2.mContainer.setLayoutParams(layoutParams);
                float floatValue = (((Float) valueAnimator.getAnimatedValue()).floatValue() * (i3 - i4)) / i5;
                if (floatValue > 0.0f) {
                    seslPeoplePicker2.mChipGroup.setAlpha(Math.min(floatValue, 1.0f));
                }
            }
        });
        ofFloat.start();
    }
}
