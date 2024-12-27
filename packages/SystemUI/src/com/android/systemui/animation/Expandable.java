package com.android.systemui.animation;

import android.view.View;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.animation.DialogTransitionAnimator;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface Expandable {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        public static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }
    }

    ActivityTransitionAnimator.Controller activityTransitionController(Integer num);

    DialogTransitionAnimator.Controller dialogTransitionController(DialogCuj dialogCuj);

    View getView();
}
