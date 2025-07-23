package com.android.systemui.qs.tiles.base.domain.actions;

import android.content.Intent;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.animation.Expandable;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface QSTileIntentUserInputHandler {
    static void handle$default(QSTileIntentUserInputHandler qSTileIntentUserInputHandler, Expandable expandable, Intent intent) {
        ActivityTransitionAnimator.Controller controller;
        QSTileIntentUserInputHandlerImpl qSTileIntentUserInputHandlerImpl = (QSTileIntentUserInputHandlerImpl) qSTileIntentUserInputHandler;
        if (expandable != null) {
            qSTileIntentUserInputHandlerImpl.getClass();
            controller = expandable.activityTransitionController(32);
        } else {
            controller = null;
        }
        qSTileIntentUserInputHandlerImpl.activityStarter.postStartActivityDismissingKeyguard(intent, 0, controller);
    }
}
