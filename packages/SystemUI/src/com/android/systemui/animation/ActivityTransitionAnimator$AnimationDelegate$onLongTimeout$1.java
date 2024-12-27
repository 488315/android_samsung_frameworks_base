package com.android.systemui.animation;

import android.util.Log;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class ActivityTransitionAnimator$AnimationDelegate$onLongTimeout$1 implements Runnable {
    public static final ActivityTransitionAnimator$AnimationDelegate$onLongTimeout$1 INSTANCE = new ActivityTransitionAnimator$AnimationDelegate$onLongTimeout$1();

    @Override // java.lang.Runnable
    public final void run() {
        Log.wtf("ActivityTransitionAnimator", "The remote animation was neither cancelled or started within 5000");
    }
}
