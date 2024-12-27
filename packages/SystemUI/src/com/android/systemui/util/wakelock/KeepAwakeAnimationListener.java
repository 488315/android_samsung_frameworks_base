package com.android.systemui.util.wakelock;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.view.animation.Animation;
import com.android.systemui.util.Assert;

public class KeepAwakeAnimationListener extends AnimatorListenerAdapter implements Animation.AnimationListener {
    private static final String TAG = "KeepAwakeAnimListener";
    static WakeLock sWakeLock;

    public KeepAwakeAnimationListener(Context context) {
        Assert.isMainThread();
        if (sWakeLock == null) {
            sWakeLock = WakeLock.createPartial(context, null, "animation");
        }
    }

    private void onEnd() {
        Assert.isMainThread();
        sWakeLock.release(TAG);
    }

    private void onStart() {
        Assert.isMainThread();
        sWakeLock.acquire(TAG);
    }

    @Override // android.view.animation.Animation.AnimationListener
    public void onAnimationEnd(Animation animation) {
        onEnd();
    }

    @Override // android.view.animation.Animation.AnimationListener
    public void onAnimationStart(Animation animation) {
        onStart();
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public void onAnimationEnd(Animator animator) {
        onEnd();
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public void onAnimationStart(Animator animator) {
        onStart();
    }

    @Override // android.view.animation.Animation.AnimationListener
    public void onAnimationRepeat(Animation animation) {
    }
}
