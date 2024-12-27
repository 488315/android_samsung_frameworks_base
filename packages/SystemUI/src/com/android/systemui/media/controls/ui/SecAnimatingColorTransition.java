package com.android.systemui.media.controls.ui;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SecAnimatingColorTransition implements ValueAnimator.AnimatorUpdateListener {
    public final Function1 applyColor;
    public int currentColor;
    public final int defaultColor;
    public final Function1 extractColor;
    public int sourceColor;
    public int targetColor;
    public final ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    public final ValueAnimator valueAnimator = buildAnimator();

    public SecAnimatingColorTransition(int i, Function1 function1, Function1 function12) {
        this.defaultColor = i;
        this.extractColor = function1;
        this.applyColor = function12;
        this.sourceColor = i;
        this.currentColor = i;
        this.targetColor = i;
        function12.invoke(Integer.valueOf(i));
    }

    public ValueAnimator buildAnimator() {
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        ofFloat.setDuration(333L);
        ofFloat.addUpdateListener(this);
        return ofFloat;
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        int intValue = ((Integer) this.argbEvaluator.evaluate(valueAnimator.getAnimatedFraction(), Integer.valueOf(this.sourceColor), Integer.valueOf(this.targetColor))).intValue();
        this.currentColor = intValue;
        this.applyColor.invoke(Integer.valueOf(intValue));
    }
}
