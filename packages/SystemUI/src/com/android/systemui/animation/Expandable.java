package com.android.systemui.animation;

import android.view.View;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.animation.DialogTransitionAnimator;

public interface Expandable {
    public static final Companion Companion = Companion.$$INSTANCE;

    public final class Companion {
        public static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }
    }

    ActivityTransitionAnimator.Controller activityTransitionController(Integer num);

    DialogTransitionAnimator.Controller dialogTransitionController(DialogCuj dialogCuj);

    View getView();
}
