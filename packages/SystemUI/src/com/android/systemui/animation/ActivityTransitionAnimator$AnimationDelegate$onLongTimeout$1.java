package com.android.systemui.animation;

import android.util.Log;

/* loaded from: classes.dex */
public final class ActivityTransitionAnimator$AnimationDelegate$onLongTimeout$1 implements Runnable {
    public static final ActivityTransitionAnimator$AnimationDelegate$onLongTimeout$1 INSTANCE = new ActivityTransitionAnimator$AnimationDelegate$onLongTimeout$1();

    @Override // java.lang.Runnable
    public final void run() {
        Log.wtf("ActivityTransitionAnimator", "The remote animation was neither cancelled or started within 5000");
    }
}
