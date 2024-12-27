package com.android.systemui.qs.tiles.base.actions;

import android.content.Intent;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.animation.Expandable;

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
