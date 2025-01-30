package com.android.wm.shell.transition.change;

import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceControl;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ClipRectAnimation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.window.TransitionInfo;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DismissChangeTransitionSpec extends ChangeTransitionSpec {
    @Override // com.android.wm.shell.transition.change.ChangeTransitionSpec
    public final Animation createBoundsChangeAnimation() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 0.0f);
        alphaAnimation.setDuration(getAnimationDuration());
        return alphaAnimation;
    }

    @Override // com.android.wm.shell.transition.change.ChangeTransitionSpec
    public final Animation createSnapshotAnimation() {
        Rect displayFrame = getDisplayFrame();
        AnimationSet animationSet = new AnimationSet(true);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        Rect rect = this.mStartBounds;
        Rect rect2 = new Rect(0, 0, rect.width(), rect.height());
        ClipRectAnimation clipRectAnimation = new ClipRectAnimation(rect2, rect2);
        int i = rect.left;
        int i2 = rect.top;
        TranslateAnimation translateAnimation = new TranslateAnimation(i, i, i2, i2);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(clipRectAnimation);
        animationSet.addAnimation(translateAnimation);
        animationSet.setInterpolator(new LinearInterpolator());
        animationSet.setDuration(getAnimationDuration());
        animationSet.initialize(rect.width(), rect.height(), displayFrame.width(), displayFrame.height());
        return animationSet;
    }

    @Override // com.android.wm.shell.transition.change.ChangeTransitionSpec
    public final void reduceDurationScaleIfNeeded(TransitionInfo transitionInfo) {
        if (this.mChange.getChangeTransitMode() == 6) {
            this.mDurationScale *= 0.5f;
            return;
        }
        TransitionInfo.Change changeForAppsEdgeActivity = transitionInfo.getChangeForAppsEdgeActivity();
        if (changeForAppsEdgeActivity == null || changeForAppsEdgeActivity.getChangeLeash() == null || changeForAppsEdgeActivity.getChangeTransitMode() != 2) {
            return;
        }
        this.mDurationScale *= 0.25f;
    }

    @Override // com.android.wm.shell.transition.change.ChangeTransitionSpec
    public final void setupChangeTransitionHierarchy(TransitionInfo.Change change, SurfaceControl.Transaction transaction) {
        SurfaceControl snapshot = change.getSnapshot();
        SurfaceControl leash = change.getLeash();
        SurfaceControl changeLeash = change.getChangeLeash();
        if (snapshot == null || leash == null || changeLeash == null) {
            Log.w("ChangeTransitionProvider", "setupChangeTransitionHierarchy: invalid surfaces, snapshot=" + snapshot + ", container=" + leash + ", change=" + changeLeash + ", " + this);
            return;
        }
        transaction.setWindowCrop(leash, -1, -1);
        Rect rect = this.mStartBounds;
        transaction.setPosition(leash, rect.left, rect.top);
        transaction.reparent(snapshot, changeLeash);
        transaction.setLayer(snapshot, Integer.MAX_VALUE);
        transaction.setPosition(snapshot, rect.left, rect.top);
        transaction.setPosition(changeLeash, 0.0f, 0.0f);
        transaction.setWindowCrop(changeLeash, -1, -1);
        Log.d("ChangeTransitionProvider", "setupChangeTransitionHierarchy: place " + snapshot + " above " + leash + ", parent=" + changeLeash);
    }

    @Override // com.android.wm.shell.transition.change.ChangeTransitionSpec
    public final String toString() {
        return "DismissChangeTransition" + super.toString();
    }
}
