package com.android.systemui.util;

import androidx.core.animation.Animator;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class AnimatorExtensionsKt$addListener$listener$1 implements Animator.AnimatorListener {
    final /* synthetic */ Function1 $onCancel;
    final /* synthetic */ Function1 $onEnd;
    final /* synthetic */ Function1 $onRepeat;
    final /* synthetic */ Function1 $onStart;

    public AnimatorExtensionsKt$addListener$listener$1(Function1 function1, Function1 function12, Function1 function13, Function1 function14) {
        this.$onRepeat = function1;
        this.$onEnd = function12;
        this.$onCancel = function13;
        this.$onStart = function14;
    }

    @Override // androidx.core.animation.Animator.AnimatorListener
    public void onAnimationCancel(Animator animator) {
        this.$onCancel.mo779invoke(animator);
    }

    @Override // androidx.core.animation.Animator.AnimatorListener
    public void onAnimationEnd(Animator animator) {
        this.$onEnd.mo779invoke(animator);
    }

    @Override // androidx.core.animation.Animator.AnimatorListener
    public void onAnimationRepeat(Animator animator) {
        this.$onRepeat.mo779invoke(animator);
    }

    @Override // androidx.core.animation.Animator.AnimatorListener
    public void onAnimationStart(Animator animator) {
        this.$onStart.mo779invoke(animator);
    }

    @Override // androidx.core.animation.Animator.AnimatorListener
    public void onAnimationEnd(Animator animator, boolean z) {
        onAnimationEnd(animator);
    }

    @Override // androidx.core.animation.Animator.AnimatorListener
    public void onAnimationStart(Animator animator, boolean z) {
        onAnimationStart(animator);
    }
}
