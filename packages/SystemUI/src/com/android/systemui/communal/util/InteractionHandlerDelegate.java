package com.android.systemui.communal.util;

import android.app.ActivityOptions;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Pair;
import android.view.View;
import android.widget.RemoteViews;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.animation.GhostedViewTransitionAnimatorController;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class InteractionHandlerDelegate implements RemoteViews.InteractionHandler {
    public final Function1 findViewToAnimate;
    public final IntentStarter intentStarter;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface IntentStarter {
        void startPendingIntent(PendingIntent pendingIntent, Intent intent, ActivityOptions activityOptions, GhostedViewTransitionAnimatorController ghostedViewTransitionAnimatorController);
    }

    public InteractionHandlerDelegate(Function1 function1, IntentStarter intentStarter) {
        this.findViewToAnimate = function1;
        this.intentStarter = intentStarter;
    }

    public final boolean onInteraction(View view, PendingIntent pendingIntent, RemoteViews.RemoteResponse remoteResponse) {
        View view2;
        Pair launchOptions = remoteResponse.getLaunchOptions(view);
        Object obj = view;
        if (!pendingIntent.isActivity()) {
            return RemoteViews.startPendingIntent(view, pendingIntent, launchOptions);
        }
        while (true) {
            if (!(obj instanceof View)) {
                view2 = null;
                break;
            }
            if (((Boolean) this.findViewToAnimate.invoke(obj)).booleanValue()) {
                view2 = (View) obj;
                break;
            }
            obj = ((View) obj).getParent();
        }
        GhostedViewTransitionAnimatorController fromView$default = view2 != null ? ActivityTransitionAnimator.Controller.Companion.fromView$default(ActivityTransitionAnimator.Controller.Companion, view2, null, 30) : null;
        Intrinsics.checkNotNull(launchOptions);
        Intent intent = (Intent) launchOptions.first;
        ActivityOptions activityOptions = (ActivityOptions) launchOptions.second;
        IntentStarter intentStarter = this.intentStarter;
        Intrinsics.checkNotNull(intent);
        Intrinsics.checkNotNull(activityOptions);
        intentStarter.startPendingIntent(pendingIntent, intent, activityOptions, fromView$default);
        return true;
    }
}
