package com.android.systemui.plugins;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.UserHandle;
import android.view.View;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.plugins.annotations.ProvidesInterface;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@ProvidesInterface(version = 2)
/* loaded from: classes2.dex */
public interface ActivityStarter {
    public static final int VERSION = 2;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Callback {
        void onActivityStarted(int i);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface OnDismissAction {
        boolean onDismiss();

        default boolean willRunAnimationOnKeyguard() {
            return false;
        }
    }

    void dismissKeyguardThenExecute(OnDismissAction onDismissAction, Runnable runnable, boolean z);

    void dismissKeyguardThenExecute(OnDismissAction onDismissAction, Runnable runnable, boolean z, String str);

    void executeRunnableDismissingKeyguard(Runnable runnable, Runnable runnable2, boolean z, boolean z2, boolean z3);

    void executeRunnableDismissingKeyguard(Runnable runnable, Runnable runnable2, boolean z, boolean z2, boolean z3, boolean z4, String str);

    void postQSRunnableDismissingKeyguard(Runnable runnable);

    void postQSRunnableDismissingKeyguard(Runnable runnable, boolean z);

    void postStartActivityDismissingKeyguard(PendingIntent pendingIntent);

    void postStartActivityDismissingKeyguard(PendingIntent pendingIntent, ActivityLaunchAnimator.Controller controller);

    void postStartActivityDismissingKeyguard(PendingIntent pendingIntent, boolean z);

    void postStartActivityDismissingKeyguard(Intent intent, int i);

    void postStartActivityDismissingKeyguard(Intent intent, int i, ActivityLaunchAnimator.Controller controller);

    void postStartActivityDismissingKeyguard(Intent intent, int i, ActivityLaunchAnimator.Controller controller, String str);

    void startActivity(Intent intent, boolean z);

    default void startActivity(Intent intent, boolean z, ActivityLaunchAnimator.Controller controller) {
        startActivity(intent, z, controller, false);
    }

    void startActivity(Intent intent, boolean z, ActivityLaunchAnimator.Controller controller, boolean z2);

    void startActivity(Intent intent, boolean z, ActivityLaunchAnimator.Controller controller, boolean z2, UserHandle userHandle);

    void startActivity(Intent intent, boolean z, Callback callback);

    void startActivity(Intent intent, boolean z, boolean z2);

    void startActivity(Intent intent, boolean z, boolean z2, int i);

    void startActivityDismissingKeyguard(Intent intent, boolean z, boolean z2);

    void startActivityDismissingKeyguard(Intent intent, boolean z, boolean z2, boolean z3, Callback callback, int i, ActivityLaunchAnimator.Controller controller, UserHandle userHandle);

    void startActivityDismissingKeyguard(Intent intent, boolean z, boolean z2, boolean z3, Callback callback, int i, ActivityLaunchAnimator.Controller controller, UserHandle userHandle, int i2);

    void startCameraActivity(Intent intent, boolean z, Callback callback);

    void startPendingIntentDismissingKeyguard(PendingIntent pendingIntent);

    void startPendingIntentDismissingKeyguard(PendingIntent pendingIntent, Runnable runnable);

    void startPendingIntentDismissingKeyguard(PendingIntent pendingIntent, Runnable runnable, View view);

    void startPendingIntentDismissingKeyguard(PendingIntent pendingIntent, Runnable runnable, ActivityLaunchAnimator.Controller controller);
}
