package com.android.systemui.communal.smartspace;

import android.app.ActivityOptions;
import android.app.PendingIntent;
import android.content.Intent;
import android.view.View;
import android.widget.RemoteViews;
import com.android.systemui.communal.domain.interactor.CommunalSceneInteractor;
import com.android.systemui.communal.util.InteractionHandlerDelegate;
import com.android.systemui.communal.widgets.CommunalTransitionAnimatorController;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.Logger;
import com.android.systemui.plugins.ActivityStarter;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SmartspaceInteractionHandler implements RemoteViews.InteractionHandler {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ActivityStarter activityStarter;
    public final InteractionHandlerDelegate delegate;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public SmartspaceInteractionHandler(ActivityStarter activityStarter, CommunalSceneInteractor communalSceneInteractor, LogBuffer logBuffer) {
        this.activityStarter = activityStarter;
        this.delegate = new InteractionHandlerDelegate(communalSceneInteractor, new SmartspaceInteractionHandler$$ExternalSyntheticLambda0(), new InteractionHandlerDelegate.IntentStarter() { // from class: com.android.systemui.communal.smartspace.SmartspaceInteractionHandler$delegate$2
            @Override // com.android.systemui.communal.util.InteractionHandlerDelegate.IntentStarter
            public final void startActivity(PendingIntent pendingIntent, Intent intent, ActivityOptions activityOptions, CommunalTransitionAnimatorController communalTransitionAnimatorController) {
                SmartspaceInteractionHandler.this.activityStarter.startPendingIntentWithoutDismissing(pendingIntent, false, null, communalTransitionAnimatorController, intent, activityOptions.toBundle());
            }
        }, new Logger(logBuffer, "SmartspaceInteractionHandler"));
    }

    public final boolean onInteraction(View view, PendingIntent pendingIntent, RemoteViews.RemoteResponse remoteResponse) {
        return this.delegate.onInteraction(view, pendingIntent, remoteResponse);
    }
}
