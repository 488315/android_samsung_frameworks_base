package com.android.systemui.qs.tiles.base.actions;

import android.content.Intent;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.animation.Expandable;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
