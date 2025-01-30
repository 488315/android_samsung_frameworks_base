package com.google.android.material.floatingactionbutton;

import android.animation.Animator;
import android.animation.AnimatorSet;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface MotionStrategy {
    AnimatorSet createAnimator();

    int getDefaultMotionSpecResource();

    void onAnimationCancel();

    void onAnimationEnd();

    void onAnimationStart(Animator animator);

    void onChange();

    void performNow();

    boolean shouldCancel();
}
