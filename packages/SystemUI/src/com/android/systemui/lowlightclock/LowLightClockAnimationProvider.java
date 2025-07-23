package com.android.systemui.lowlightclock;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.util.Property;
import android.view.View;
import com.android.app.animation.Interpolators;
import com.android.dream.lowlight.util.TruncatedInterpolator;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class LowLightClockAnimationProvider {
    public final long mAlphaAnimationDurationMillis;
    public final long mAlphaAnimationInStartDelayMillis;
    public final TruncatedInterpolator mTranslationOutInterpolator;
    public final long mYTranslationAnimationInDurationMillis;
    public final int mYTranslationAnimationInStartOffset;

    public LowLightClockAnimationProvider(int i, long j, long j2, long j3) {
        this.mYTranslationAnimationInStartOffset = i;
        this.mYTranslationAnimationInDurationMillis = j;
        this.mAlphaAnimationInStartDelayMillis = j2;
        this.mAlphaAnimationDurationMillis = j3;
        this.mTranslationOutInterpolator = new TruncatedInterpolator(Interpolators.EMPHASIZED, j, j3);
    }

    public final Animator provideAnimationOut(View... viewArr) {
        AnimatorSet animatorSet = new AnimatorSet();
        for (View view : viewArr) {
            if (view != null) {
                ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, (Property<View, Float>) View.ALPHA, 0.0f);
                long j = this.mAlphaAnimationDurationMillis;
                ofFloat.setDuration(j);
                ofFloat.setInterpolator(Interpolators.LINEAR);
                ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(view, (Property<View, Float>) View.TRANSLATION_Y, this.mYTranslationAnimationInStartOffset);
                ofFloat2.setDuration(j);
                ofFloat2.setInterpolator(this.mTranslationOutInterpolator);
                animatorSet.playTogether(ofFloat, ofFloat2);
            }
        }
        return animatorSet;
    }
}
