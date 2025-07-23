package com.android.systemui.communal.util;

import android.app.ActivityOptions;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Pair;
import android.view.View;
import android.widget.RemoteViews;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.animation.GhostedViewTransitionAnimatorController;
import com.android.systemui.communal.domain.interactor.CommunalSceneInteractor;
import com.android.systemui.communal.widgets.CommunalTransitionAnimatorController;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.log.core.Logger;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class InteractionHandlerDelegate implements RemoteViews.InteractionHandler {
    public final CommunalSceneInteractor communalSceneInteractor;
    public final Function1 findViewToAnimate;
    public final IntentStarter intentStarter;
    public final Logger logger;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface IntentStarter {
        void startActivity(PendingIntent pendingIntent, Intent intent, ActivityOptions activityOptions, CommunalTransitionAnimatorController communalTransitionAnimatorController);

        default boolean startPendingIntent(View view, PendingIntent pendingIntent, Intent intent, ActivityOptions activityOptions) {
            return RemoteViews.startPendingIntent(view, pendingIntent, new Pair(intent, activityOptions));
        }
    }

    public InteractionHandlerDelegate(CommunalSceneInteractor communalSceneInteractor, Function1 function1, IntentStarter intentStarter, Logger logger) {
        this.communalSceneInteractor = communalSceneInteractor;
        this.findViewToAnimate = function1;
        this.intentStarter = intentStarter;
        this.logger = logger;
    }

    public final boolean onInteraction(View view, PendingIntent pendingIntent, RemoteViews.RemoteResponse remoteResponse) {
        View view2;
        GhostedViewTransitionAnimatorController fromView$default;
        Logger logger = this.logger;
        CommunalTransitionAnimatorController communalTransitionAnimatorController = null;
        LogMessage obtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.INFO, new InteractionHandlerDelegate$$ExternalSyntheticLambda0(), null);
        obtain.setStr1(pendingIntent.isActivity() ? "activity" : pendingIntent.isBroadcast() ? "broadcast" : pendingIntent.isForegroundService() ? "fgService" : pendingIntent.isService() ? "service" : "unknown");
        obtain.setStr2(pendingIntent.getCreatorPackage());
        logger.getBuffer().commit(obtain);
        Pair launchOptions = remoteResponse.getLaunchOptions(view);
        launchOptions.getClass();
        Intent intent = (Intent) launchOptions.first;
        ActivityOptions activityOptions = (ActivityOptions) launchOptions.second;
        Object obj = view;
        if (!pendingIntent.isActivity()) {
            IntentStarter intentStarter = this.intentStarter;
            intent.getClass();
            activityOptions.getClass();
            return intentStarter.startPendingIntent(view, pendingIntent, intent, activityOptions);
        }
        while (true) {
            if (!(obj instanceof View)) {
                view2 = null;
                break;
            }
            if (((Boolean) this.findViewToAnimate.mo779invoke(obj)).booleanValue()) {
                view2 = (View) obj;
                break;
            }
            obj = ((View) obj).getParent();
        }
        if (view2 != null && (fromView$default = ActivityTransitionAnimator.Controller.Companion.fromView$default(ActivityTransitionAnimator.Controller.Companion, view2, null, 62)) != null) {
            this.communalSceneInteractor._isLaunchingWidget.updateState(null, Boolean.TRUE);
            communalTransitionAnimatorController = new CommunalTransitionAnimatorController(fromView$default, this.communalSceneInteractor);
        }
        IntentStarter intentStarter2 = this.intentStarter;
        intent.getClass();
        activityOptions.getClass();
        intentStarter2.startActivity(pendingIntent, intent, activityOptions, communalTransitionAnimatorController);
        return true;
    }
}
