package com.android.systemui.controls.management;

import android.animation.Animator;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.view.ViewGroup;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class WindowTransition extends Transition {
    public final Function1 animator;

    public WindowTransition(Function1 function1) {
        this.animator = function1;
    }

    @Override // android.transition.Transition
    public final void captureEndValues(TransitionValues transitionValues) {
        transitionValues.values.put("item", Float.valueOf(1.0f));
    }

    @Override // android.transition.Transition
    public final void captureStartValues(TransitionValues transitionValues) {
        transitionValues.values.put("item", Float.valueOf(0.0f));
    }

    @Override // android.transition.Transition
    public final Animator createAnimator(ViewGroup viewGroup, TransitionValues transitionValues, TransitionValues transitionValues2) {
        Function1 function1 = this.animator;
        transitionValues.getClass();
        return (Animator) function1.mo779invoke(transitionValues.view);
    }
}
