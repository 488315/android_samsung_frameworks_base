package com.android.systemui.animation;

import android.view.View;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.animation.DialogTransitionAnimator;

public final class Expandable$Companion$fromView$1 implements Expandable {
    public final /* synthetic */ View $view;

    public Expandable$Companion$fromView$1(View view) {
        this.$view = view;
    }

    @Override // com.android.systemui.animation.Expandable
    public final ActivityTransitionAnimator.Controller activityTransitionController(Integer num) {
        ActivityTransitionAnimator.Controller.Companion companion = ActivityTransitionAnimator.Controller.Companion;
        View view = this.$view;
        companion.getClass();
        return ActivityTransitionAnimator.Controller.Companion.fromView(view, num, null, null, null);
    }

    @Override // com.android.systemui.animation.Expandable
    public final DialogTransitionAnimator.Controller dialogTransitionController(DialogCuj dialogCuj) {
        DialogTransitionAnimator.Controller.Companion companion = DialogTransitionAnimator.Controller.Companion;
        View view = this.$view;
        companion.getClass();
        return DialogTransitionAnimator.Controller.Companion.fromView(view, dialogCuj);
    }

    @Override // com.android.systemui.animation.Expandable
    public final View getView() {
        return this.$view;
    }
}
