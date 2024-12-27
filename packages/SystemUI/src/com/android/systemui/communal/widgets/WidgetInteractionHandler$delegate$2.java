package com.android.systemui.communal.widgets;

import android.app.ActivityOptions;
import android.app.PendingIntent;
import android.content.Intent;
import com.android.systemui.animation.GhostedViewTransitionAnimatorController;
import com.android.systemui.communal.util.InteractionHandlerDelegate;
import kotlin.Function;
import kotlin.jvm.internal.FunctionAdapter;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class WidgetInteractionHandler$delegate$2 implements InteractionHandlerDelegate.IntentStarter, FunctionAdapter {
    public final /* synthetic */ WidgetInteractionHandler $tmp0;

    public WidgetInteractionHandler$delegate$2(WidgetInteractionHandler widgetInteractionHandler) {
        this.$tmp0 = widgetInteractionHandler;
    }

    public final boolean equals(Object obj) {
        if ((obj instanceof InteractionHandlerDelegate.IntentStarter) && (obj instanceof FunctionAdapter)) {
            return getFunctionDelegate().equals(((FunctionAdapter) obj).getFunctionDelegate());
        }
        return false;
    }

    @Override // kotlin.jvm.internal.FunctionAdapter
    public final Function getFunctionDelegate() {
        return new FunctionReferenceImpl(4, this.$tmp0, WidgetInteractionHandler.class, "startIntent", "startIntent(Landroid/app/PendingIntent;Landroid/content/Intent;Landroid/app/ActivityOptions;Lcom/android/systemui/animation/ActivityTransitionAnimator$Controller;)Z", 0);
    }

    public final int hashCode() {
        return getFunctionDelegate().hashCode();
    }

    @Override // com.android.systemui.communal.util.InteractionHandlerDelegate.IntentStarter
    public final void startPendingIntent(PendingIntent pendingIntent, Intent intent, ActivityOptions activityOptions, GhostedViewTransitionAnimatorController ghostedViewTransitionAnimatorController) {
        this.$tmp0.activityStarter.startPendingIntentMaybeDismissingKeyguard(pendingIntent, false, null, ghostedViewTransitionAnimatorController, intent, activityOptions.toBundle());
    }
}
