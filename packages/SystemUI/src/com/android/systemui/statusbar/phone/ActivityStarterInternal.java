package com.android.systemui.statusbar.phone;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import android.view.View;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.plugins.ActivityStarter;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface ActivityStarterInternal {
    static /* synthetic */ void executeRunnableDismissingKeyguard$default(ActivityStarterInternal activityStarterInternal, Runnable runnable, Runnable runnable2, boolean z, boolean z2, boolean z3, boolean z4, String str, int i) {
        if ((i & 2) != 0) {
            runnable2 = null;
        }
        if ((i & 4) != 0) {
            z = false;
        }
        if ((i & 8) != 0) {
            z2 = false;
        }
        if ((i & 16) != 0) {
            z3 = false;
        }
        if ((i & 32) != 0) {
            z4 = false;
        }
        if ((i & 64) != 0) {
            str = null;
        }
        activityStarterInternal.executeRunnableDismissingKeyguard(runnable, runnable2, z, z2, z3, z4, str);
    }

    static /* synthetic */ void startActivityDismissingKeyguard$default(ActivityStarterInternal activityStarterInternal, Intent intent, boolean z, boolean z2, ActivityStarter.Callback callback, int i, ActivityTransitionAnimator.Controller controller, String str, boolean z3, UserHandle userHandle, int i2) {
        if ((i2 & 4) != 0) {
            z2 = false;
        }
        if ((i2 & 8) != 0) {
            callback = null;
        }
        if ((i2 & 16) != 0) {
            i = 0;
        }
        if ((i2 & 32) != 0) {
            controller = null;
        }
        if ((i2 & 64) != 0) {
            str = null;
        }
        if ((i2 & 128) != 0) {
            z3 = false;
        }
        if ((i2 & 256) != 0) {
            userHandle = null;
        }
        activityStarterInternal.startActivityDismissingKeyguard(intent, z, z2, callback, i, controller, str, z3, userHandle);
    }

    void dismissKeyguardThenExecute(ActivityStarter.OnDismissAction onDismissAction, Runnable runnable, boolean z);

    void executeRunnableDismissingKeyguard(Runnable runnable, Runnable runnable2, boolean z, boolean z2, boolean z3, boolean z4, String str);

    void registerTransition(ActivityTransitionAnimator.TransitionCookie transitionCookie, ActivityTransitionAnimator.ControllerFactory controllerFactory, CoroutineScope coroutineScope);

    boolean shouldAnimateLaunch(boolean z);

    void startActivity(Intent intent, boolean z, ActivityTransitionAnimator.Controller controller, boolean z2, UserHandle userHandle);

    void startActivityDismissingKeyguard(Intent intent, boolean z, boolean z2, ActivityStarter.Callback callback, int i, ActivityTransitionAnimator.Controller controller, String str, boolean z3, UserHandle userHandle);

    void startActivityDismissingKeyguard(Intent intent, boolean z, boolean z2, boolean z3, ActivityStarter.Callback callback, int i, ActivityTransitionAnimator.Controller controller, UserHandle userHandle, int i2);

    void startCameraActivity(Intent intent, boolean z, ActivityStarter.Callback callback);

    void startPendingIntentDismissingKeyguard(PendingIntent pendingIntent, boolean z, Runnable runnable, View view, ActivityTransitionAnimator.Controller controller, boolean z2, boolean z3, Intent intent, Bundle bundle, String str);

    void unregisterTransition(ActivityTransitionAnimator.TransitionCookie transitionCookie);
}
