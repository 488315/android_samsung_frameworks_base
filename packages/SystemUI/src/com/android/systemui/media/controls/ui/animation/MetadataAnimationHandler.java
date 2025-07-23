package com.android.systemui.media.controls.ui.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import kotlin.Triple;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class MetadataAnimationHandler extends AnimatorListenerAdapter {
    public final Animator enterAnimator;
    public final Animator exitAnimator;
    public Function0 postEnterUpdate;
    public Function0 postExitUpdate;
    public Triple targetData;

    public MetadataAnimationHandler(Animator animator, Animator animator2) {
        this.exitAnimator = animator;
        this.enterAnimator = animator2;
        animator.addListener(this);
        animator2.addListener(this);
    }

    public final boolean isRunning() {
        return this.enterAnimator.isRunning() || this.exitAnimator.isRunning();
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public final void onAnimationEnd(Animator animator) {
        if (animator == this.exitAnimator) {
            Function0 function0 = this.postExitUpdate;
            if (function0 != null) {
                function0.invoke();
            }
            this.postExitUpdate = null;
            this.enterAnimator.start();
        }
        if (animator == this.enterAnimator) {
            if (this.postExitUpdate != null) {
                this.exitAnimator.start();
                return;
            }
            Function0 function02 = this.postEnterUpdate;
            if (function02 != null) {
                function02.invoke();
            }
            this.postEnterUpdate = null;
        }
    }
}
