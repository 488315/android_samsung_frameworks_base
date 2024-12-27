package com.android.systemui.animation;

public final /* synthetic */ class DialogTransitionAnimator$createActivityTransitionController$1$enableDialogDismiss$1 implements Runnable {
    public final /* synthetic */ AnimatedDialog $tmp0;

    public DialogTransitionAnimator$createActivityTransitionController$1$enableDialogDismiss$1(AnimatedDialog animatedDialog) {
        this.$tmp0 = animatedDialog;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.$tmp0.onDialogDismissed();
    }
}
