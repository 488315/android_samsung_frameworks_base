package com.android.systemui.communal.smartspace;

import android.app.ActivityOptions;
import android.app.PendingIntent;
import android.content.Intent;
import com.android.systemui.animation.GhostedViewTransitionAnimatorController;
import com.android.systemui.communal.util.InteractionHandlerDelegate;
import kotlin.Function;
import kotlin.jvm.internal.FunctionAdapter;
import kotlin.jvm.internal.FunctionReferenceImpl;

public final /* synthetic */ class SmartspaceInteractionHandler$delegate$2 implements InteractionHandlerDelegate.IntentStarter, FunctionAdapter {
    public final /* synthetic */ SmartspaceInteractionHandler $tmp0;

    public SmartspaceInteractionHandler$delegate$2(SmartspaceInteractionHandler smartspaceInteractionHandler) {
        this.$tmp0 = smartspaceInteractionHandler;
    }

    public final boolean equals(Object obj) {
        if ((obj instanceof InteractionHandlerDelegate.IntentStarter) && (obj instanceof FunctionAdapter)) {
            return getFunctionDelegate().equals(((FunctionAdapter) obj).getFunctionDelegate());
        }
        return false;
    }

    @Override // kotlin.jvm.internal.FunctionAdapter
    public final Function getFunctionDelegate() {
        return new FunctionReferenceImpl(4, this.$tmp0, SmartspaceInteractionHandler.class, "startIntent", "startIntent(Landroid/app/PendingIntent;Landroid/content/Intent;Landroid/app/ActivityOptions;Lcom/android/systemui/animation/ActivityTransitionAnimator$Controller;)Z", 0);
    }

    public final int hashCode() {
        return getFunctionDelegate().hashCode();
    }

    @Override // com.android.systemui.communal.util.InteractionHandlerDelegate.IntentStarter
    public final void startPendingIntent(PendingIntent pendingIntent, Intent intent, ActivityOptions activityOptions, GhostedViewTransitionAnimatorController ghostedViewTransitionAnimatorController) {
        this.$tmp0.activityStarter.startPendingIntentWithoutDismissing(pendingIntent, false, null, ghostedViewTransitionAnimatorController, intent, activityOptions.toBundle());
    }
}
