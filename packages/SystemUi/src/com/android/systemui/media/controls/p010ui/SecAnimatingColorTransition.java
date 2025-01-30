package com.android.systemui.media.controls.p010ui;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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
