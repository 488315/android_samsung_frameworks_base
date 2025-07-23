package com.android.systemui.shared.clocks;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import com.android.systemui.plugins.clocks.VPointF;
import com.android.systemui.shared.clocks.view.FlexClockView$animateCharge$2$1$1;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DigitTranslateAnimator {
    public long baseTranslation;
    public final ValueAnimator bounceAnimator;
    public long currentTranslation;
    public long targetTranslation;
    public final Function1 updateCallback;

    public DigitTranslateAnimator(Function1 function1) {
        this.updateCallback = function1;
        VPointF.Companion companion = VPointF.Companion;
        this.currentTranslation = companion.m2772getZEROJv7bpU8();
        this.baseTranslation = companion.m2772getZEROJv7bpU8();
        this.targetTranslation = companion.m2772getZEROJv7bpU8();
        ValueAnimator ofFloat = ValueAnimator.ofFloat(1.0f);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.shared.clocks.DigitTranslateAnimator$bounceAnimator$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                DigitTranslateAnimator digitTranslateAnimator = DigitTranslateAnimator.this;
                Function1 function12 = digitTranslateAnimator.updateCallback;
                float animatedFraction = valueAnimator.getAnimatedFraction();
                long j = digitTranslateAnimator.baseTranslation;
                function12.mo779invoke(VPointF.m2728boximpl(VPointF.m2756plusb2IjXjg(j, VPointF.Companion.m2778timesNvxBqkk(animatedFraction, VPointF.m2751minusb2IjXjg(digitTranslateAnimator.targetTranslation, j)))));
            }
        });
        ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.shared.clocks.DigitTranslateAnimator$bounceAnimator$1$2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
                DigitTranslateAnimator digitTranslateAnimator = DigitTranslateAnimator.this;
                digitTranslateAnimator.baseTranslation = digitTranslateAnimator.currentTranslation;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                DigitTranslateAnimator digitTranslateAnimator = DigitTranslateAnimator.this;
                digitTranslateAnimator.baseTranslation = digitTranslateAnimator.currentTranslation;
            }
        });
        this.bounceAnimator = ofFloat;
    }

    /* renamed from: animatePosition-WofAHi4$default, reason: not valid java name */
    public static void m2932animatePositionWofAHi4$default(final DigitTranslateAnimator digitTranslateAnimator, boolean z, long j, TimeInterpolator timeInterpolator, long j2, final FlexClockView$animateCharge$2$1$1 flexClockView$animateCharge$2$1$1, int i) {
        if ((i & 32) != 0) {
            flexClockView$animateCharge$2$1$1 = null;
        }
        digitTranslateAnimator.targetTranslation = j2;
        if (!z) {
            digitTranslateAnimator.currentTranslation = j2;
            digitTranslateAnimator.baseTranslation = j2;
            digitTranslateAnimator.updateCallback.mo779invoke(VPointF.m2728boximpl(j2));
            return;
        }
        digitTranslateAnimator.bounceAnimator.cancel();
        digitTranslateAnimator.bounceAnimator.setStartDelay(0L);
        digitTranslateAnimator.bounceAnimator.setDuration(j);
        if (timeInterpolator != null) {
            digitTranslateAnimator.bounceAnimator.setInterpolator(timeInterpolator);
        }
        if (flexClockView$animateCharge$2$1$1 != null) {
            digitTranslateAnimator.bounceAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.shared.clocks.DigitTranslateAnimator$animatePosition$listener$1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationCancel(Animator animator) {
                    digitTranslateAnimator.bounceAnimator.removeListener(this);
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    flexClockView$animateCharge$2$1$1.run();
                    digitTranslateAnimator.bounceAnimator.removeListener(this);
                }
            });
        }
        digitTranslateAnimator.bounceAnimator.start();
    }
}
