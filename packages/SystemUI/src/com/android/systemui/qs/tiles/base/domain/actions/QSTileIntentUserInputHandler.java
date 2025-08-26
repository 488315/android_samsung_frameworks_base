package com.android.systemui.qs.tiles.base.domain.actions;

import android.content.Intent;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.animation.Expandable;

/* loaded from: classes2.dex */
public interface QSTileIntentUserInputHandler {
    static void handle$default(QSTileIntentUserInputHandler qSTileIntentUserInputHandler, Expandable expandable, Intent intent) {
        ActivityTransitionAnimator.Controller controllerActivityTransitionController;
        QSTileIntentUserInputHandlerImpl qSTileIntentUserInputHandlerImpl = (QSTileIntentUserInputHandlerImpl) qSTileIntentUserInputHandler;
        if (expandable != null) {
            qSTileIntentUserInputHandlerImpl.getClass();
            controllerActivityTransitionController = expandable.activityTransitionController(32);
        } else {
            controllerActivityTransitionController = null;
        }
        qSTileIntentUserInputHandlerImpl.activityStarter.postStartActivityDismissingKeyguard(intent, 0, controllerActivityTransitionController);
    }
}
