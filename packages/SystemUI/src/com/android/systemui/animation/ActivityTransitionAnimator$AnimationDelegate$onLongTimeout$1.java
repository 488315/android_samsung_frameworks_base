package com.android.systemui.animation;

import android.util.Log;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ActivityTransitionAnimator$AnimationDelegate$onLongTimeout$1 implements Runnable {
    public static final ActivityTransitionAnimator$AnimationDelegate$onLongTimeout$1 INSTANCE = new ActivityTransitionAnimator$AnimationDelegate$onLongTimeout$1();

    @Override // java.lang.Runnable
    public final void run() {
        Log.wtf("ActivityTransitionAnimator", "The remote animation was neither cancelled or started within 5000");
    }
}
