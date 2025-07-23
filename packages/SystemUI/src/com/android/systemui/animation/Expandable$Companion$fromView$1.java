package com.android.systemui.animation;

import android.content.ComponentName;
import android.view.View;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.animation.DialogTransitionAnimator;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class Expandable$Companion$fromView$1 implements Expandable {
    public final /* synthetic */ View $view;

    public Expandable$Companion$fromView$1(View view) {
        this.$view = view;
    }

    @Override // com.android.systemui.animation.Expandable
    public final ActivityTransitionAnimator.Controller activityTransitionController(Integer num, ActivityTransitionAnimator.TransitionCookie transitionCookie, ComponentName componentName, Integer num2, boolean z) {
        ActivityTransitionAnimator.Controller.Companion companion = ActivityTransitionAnimator.Controller.Companion;
        View view = this.$view;
        companion.getClass();
        return ActivityTransitionAnimator.Controller.Companion.fromView(view, num, transitionCookie, componentName, num2, z);
    }

    @Override // com.android.systemui.animation.Expandable
    public final DialogTransitionAnimator.Controller dialogTransitionController(DialogCuj dialogCuj) {
        DialogTransitionAnimator.Controller.Companion companion = DialogTransitionAnimator.Controller.Companion;
        View view = this.$view;
        companion.getClass();
        return DialogTransitionAnimator.Controller.Companion.fromView(view, dialogCuj);
    }
}
