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

/* loaded from: classes3.dex */
public class DismissChangeTransitionSpec extends ChangeTransitionSpec {
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
        Rect rect = new Rect(0, 0, this.mStartBounds.width(), this.mStartBounds.height());
        ClipRectAnimation clipRectAnimation = new ClipRectAnimation(rect, rect);
        Rect rect2 = this.mStartBounds;
        int i = rect2.left;
        int i2 = rect2.top;
        TranslateAnimation translateAnimation = new TranslateAnimation(i, i, i2, i2);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(clipRectAnimation);
        animationSet.addAnimation(translateAnimation);
        animationSet.setInterpolator(new LinearInterpolator());
        animationSet.setDuration(getAnimationDuration());
        animationSet.initialize(this.mStartBounds.width(), this.mStartBounds.height(), displayFrame.width(), displayFrame.height());
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
        Rect rect2 = this.mStartBounds;
        transaction.setPosition(snapshot, rect2.left, rect2.top);
        transaction.setPosition(changeLeash, 0.0f, 0.0f);
        transaction.setWindowCrop(changeLeash, -1, -1);
        transaction.setShadowRadius(changeLeash, 0.0f);
        Log.d("ChangeTransitionProvider", "setupChangeTransitionHierarchy: place " + snapshot + " above " + leash + ", parent=" + changeLeash);
    }

    @Override // com.android.wm.shell.transition.change.ChangeTransitionSpec
    public final String toString() {
        return "DismissChangeTransition" + super.toString();
    }
}
