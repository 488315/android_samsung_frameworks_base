package com.android.systemui.media.controls.ui.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class MetadataAnimationHandler extends AnimatorListenerAdapter {
    public final Animator enterAnimator;
    public final Animator exitAnimator;
    public Function0 postEnterUpdate;
    public Function0 postExitUpdate;
    public Object targetData;

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
