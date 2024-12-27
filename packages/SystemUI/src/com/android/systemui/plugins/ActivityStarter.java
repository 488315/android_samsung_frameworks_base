package com.android.systemui.plugins;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import android.view.View;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.plugins.annotations.ProvidesInterface;

@ProvidesInterface(version = 2)
public interface ActivityStarter {
    public static final int VERSION = 2;

    public interface Callback {
        void onActivityStarted(int i);
    }

    public interface OnDismissAction {
        boolean onDismiss();

        default boolean willRunAnimationOnKeyguard() {
            return false;
        }
    }

    void dismissKeyguardThenExecute(OnDismissAction onDismissAction, Runnable runnable, boolean z);

    void dismissKeyguardThenExecute(OnDismissAction onDismissAction, Runnable runnable, boolean z, String str);

    void executeRunnableDismissingKeyguard(Runnable runnable, Runnable runnable2, boolean z, boolean z2, boolean z3);

    void postQSCustomizerRunnableDismissingKeyguard(Runnable runnable);

    void postQSRunnableDismissingKeyguard(Runnable runnable);

    void postQSRunnableDismissingKeyguard(Runnable runnable, boolean z);

    void postStartActivityDismissingKeyguard(PendingIntent pendingIntent);

    void postStartActivityDismissingKeyguard(PendingIntent pendingIntent, ActivityTransitionAnimator.Controller controller);

    void postStartActivityDismissingKeyguard(PendingIntent pendingIntent, boolean z);

    void postStartActivityDismissingKeyguard(Intent intent, int i);

    void postStartActivityDismissingKeyguard(Intent intent, int i, ActivityTransitionAnimator.Controller controller);

    void postStartActivityDismissingKeyguard(Intent intent, int i, ActivityTransitionAnimator.Controller controller, String str);

    boolean shouldAnimateLaunch(boolean z);

    void startActivity(Intent intent, boolean z);

    default void startActivity(Intent intent, boolean z, ActivityTransitionAnimator.Controller controller) {
        startActivity(intent, z, controller, false);
    }

    void startActivity(Intent intent, boolean z, ActivityTransitionAnimator.Controller controller, boolean z2);

    void startActivity(Intent intent, boolean z, ActivityTransitionAnimator.Controller controller, boolean z2, UserHandle userHandle);

    void startActivity(Intent intent, boolean z, Callback callback);

    void startActivity(Intent intent, boolean z, boolean z2);

    void startActivity(Intent intent, boolean z, boolean z2, int i);

    void startActivityDismissingKeyguard(Intent intent, boolean z, boolean z2);

    void startActivityDismissingKeyguard(Intent intent, boolean z, boolean z2, boolean z3, Callback callback, int i, ActivityTransitionAnimator.Controller controller, UserHandle userHandle);

    void startActivityDismissingKeyguard(Intent intent, boolean z, boolean z2, boolean z3, Callback callback, int i, ActivityTransitionAnimator.Controller controller, UserHandle userHandle, int i2);

    void startCameraActivity(Intent intent, boolean z, Callback callback);

    void startPendingIntentDismissingKeyguard(PendingIntent pendingIntent);

    void startPendingIntentDismissingKeyguard(PendingIntent pendingIntent, Runnable runnable);

    void startPendingIntentDismissingKeyguard(PendingIntent pendingIntent, Runnable runnable, View view);

    void startPendingIntentDismissingKeyguard(PendingIntent pendingIntent, Runnable runnable, ActivityTransitionAnimator.Controller controller);

    void startPendingIntentMaybeDismissingKeyguard(PendingIntent pendingIntent, Runnable runnable, ActivityTransitionAnimator.Controller controller);

    void startPendingIntentMaybeDismissingKeyguard(PendingIntent pendingIntent, boolean z, Runnable runnable, ActivityTransitionAnimator.Controller controller, Intent intent, Bundle bundle);

    void startPendingIntentWithoutDismissing(PendingIntent pendingIntent, boolean z, Runnable runnable, ActivityTransitionAnimator.Controller controller, Intent intent, Bundle bundle);
}
