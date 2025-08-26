package com.android.systemui.media.controls.ui.animation;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public class AnimatingColorTransition implements ValueAnimator.AnimatorUpdateListener {
    public final Function1 applyColor;
    public int currentColor;
    public final int defaultColor;
    public final Function1 extractColor;
    public int sourceColor;
    public int targetColor;
    public final ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    public final ValueAnimator valueAnimator = buildAnimator();

    public AnimatingColorTransition(int i, Function1 function1, Function1 function12) {
        this.defaultColor = i;
        this.extractColor = function1;
        this.applyColor = function12;
        this.sourceColor = i;
        this.currentColor = i;
        this.targetColor = i;
        function12.mo781invoke(Integer.valueOf(i));
    }

    public ValueAnimator buildAnimator() {
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimatorOfFloat.setDuration(333L);
        valueAnimatorOfFloat.addUpdateListener(this);
        return valueAnimatorOfFloat;
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        int iIntValue = ((Integer) this.argbEvaluator.evaluate(valueAnimator.getAnimatedFraction(), Integer.valueOf(this.sourceColor), Integer.valueOf(this.targetColor))).intValue();
        this.currentColor = iIntValue;
        this.applyColor.mo781invoke(Integer.valueOf(iIntValue));
    }
}
