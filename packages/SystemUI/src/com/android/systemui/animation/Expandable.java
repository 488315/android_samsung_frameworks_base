package com.android.systemui.animation;

import android.content.ComponentName;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.animation.DialogTransitionAnimator;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface Expandable {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }
    }

    default ActivityTransitionAnimator.Controller activityTransitionController(Integer num) {
        return activityTransitionController(num, null, null, null, true);
    }

    ActivityTransitionAnimator.Controller activityTransitionController(Integer num, ActivityTransitionAnimator.TransitionCookie transitionCookie, ComponentName componentName, Integer num2, boolean z);

    DialogTransitionAnimator.Controller dialogTransitionController(DialogCuj dialogCuj);
}
