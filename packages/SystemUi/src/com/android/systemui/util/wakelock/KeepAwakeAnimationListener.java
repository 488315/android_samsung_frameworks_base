package com.android.systemui.util.wakelock;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.view.animation.Animation;
import com.android.systemui.util.Assert;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class KeepAwakeAnimationListener extends AnimatorListenerAdapter implements Animation.AnimationListener {
    static WakeLock sWakeLock;

    public KeepAwakeAnimationListener(Context context) {
        Assert.isMainThread();
        if (sWakeLock == null) {
            sWakeLock = WakeLock.createPartial(context, null, "animation");
        }
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public final void onAnimationEnd(Animator animator) {
        Assert.isMainThread();
        sWakeLock.release("KeepAwakeAnimListener");
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public final void onAnimationStart(Animator animator) {
        Assert.isMainThread();
        sWakeLock.acquire("KeepAwakeAnimListener");
    }

    @Override // android.view.animation.Animation.AnimationListener
    public final void onAnimationEnd(Animation animation) {
        Assert.isMainThread();
        sWakeLock.release("KeepAwakeAnimListener");
    }

    @Override // android.view.animation.Animation.AnimationListener
    public final void onAnimationStart(Animation animation) {
        Assert.isMainThread();
        sWakeLock.acquire("KeepAwakeAnimListener");
    }

    @Override // android.view.animation.Animation.AnimationListener
    public final void onAnimationRepeat(Animation animation) {
    }
}
