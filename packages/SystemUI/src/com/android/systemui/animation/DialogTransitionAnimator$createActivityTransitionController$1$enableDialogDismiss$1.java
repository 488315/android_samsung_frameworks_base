package com.android.systemui.animation;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
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
