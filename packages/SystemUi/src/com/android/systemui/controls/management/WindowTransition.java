package com.android.systemui.controls.management;

import android.animation.Animator;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.view.ViewGroup;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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
        Intrinsics.checkNotNull(transitionValues);
        return (Animator) function1.invoke(transitionValues.view);
    }
}
