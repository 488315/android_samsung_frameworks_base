package com.android.systemui.animation;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* renamed from: com.android.systemui.animation.DialogLaunchAnimator$createActivityLaunchController$1$enableDialogDismiss$1 */
/* loaded from: classes.dex */
public final /* synthetic */ class RunnableC1030xf3619ef1 implements Runnable {
    public final /* synthetic */ AnimatedDialog $tmp0;

    public RunnableC1030xf3619ef1(AnimatedDialog animatedDialog) {
        this.$tmp0 = animatedDialog;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.$tmp0.onDialogDismissed();
    }
}
