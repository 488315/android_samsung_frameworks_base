package com.android.wm.shell.transition.change;

import android.app.ActivityManager;
import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceControl;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ClipRectAnimation;
import android.view.animation.TranslateAnimation;
import android.window.TransitionInfo;

/* loaded from: classes3.dex */
public class NaturalSwitchingChangeTransitionSpec extends ChangeTransitionSpec {
    public final boolean isFreeform;

    public NaturalSwitchingChangeTransitionSpec(ActivityManager.RunningTaskInfo runningTaskInfo) {
        this.isFreeform = runningTaskInfo.isFreeform();
    }

    @Override // com.android.wm.shell.transition.change.ChangeTransitionSpec
    public final Animation createBoundsChangeAnimation() {
        AnimationSet animationSet = new AnimationSet(true);
        Rect rect = this.mEndBounds;
        int i = rect.left;
        int i2 = rect.top;
        TranslateAnimation translateAnimation = new TranslateAnimation(i, i, i2, i2);
        translateAnimation.setDuration(getAnimationDuration());
        animationSet.addAnimation(translateAnimation);
        if (!this.isFreeform) {
            AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 1.0f);
            alphaAnimation.setDuration(getAnimationDuration());
            animationSet.addAnimation(alphaAnimation);
            return animationSet;
        }
        Rect rect2 = new Rect(this.mEndBounds);
        Rect rect3 = new Rect(this.mEndBounds);
        rect2.offsetTo(0, 0);
        rect3.offsetTo(0, 0);
        ClipRectAnimation clipRectAnimation = new ClipRectAnimation(rect2, rect3);
        clipRectAnimation.setDuration(getAnimationDuration());
        animationSet.addAnimation(clipRectAnimation);
        Rect displayFrame = getDisplayFrame();
        animationSet.initialize(this.mEndBounds.width(), this.mEndBounds.height(), displayFrame.width(), displayFrame.height());
        return animationSet;
    }

    @Override // com.android.wm.shell.transition.change.ChangeTransitionSpec
    public final Animation createSnapshotAnimation() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 0.0f);
        alphaAnimation.setDuration(getAnimationDuration());
        return alphaAnimation;
    }

    @Override // com.android.wm.shell.transition.change.ChangeTransitionSpec
    public final void setupChangeTransitionHierarchy(TransitionInfo.Change change, SurfaceControl.Transaction transaction) {
        SurfaceControl snapshot = change.getSnapshot();
        SurfaceControl leash = change.getLeash();
        SurfaceControl changeLeash = change.getChangeLeash();
        if (leash == null || changeLeash == null) {
            Log.w("NaturalSwitchingChangeTransitionSpec", "setupChangeTransitionHierarchy: invalid surfaces, container=" + leash + ", change=" + changeLeash + ", " + this);
            return;
        }
        transaction.setWindowCrop(leash, -1, -1);
        transaction.reparent(leash, changeLeash);
        Rect rect = this.mEndBounds;
        transaction.setPosition(leash, rect.left, rect.top);
        transaction.reparent(snapshot, leash);
        transaction.setLayer(snapshot, Integer.MAX_VALUE);
        if (this.isFreeform) {
            transaction.setPosition(changeLeash, 0.0f, 0.0f);
        } else {
            Rect rect2 = this.mEndBounds;
            transaction.setPosition(changeLeash, rect2.left, rect2.top);
        }
        transaction.setWindowCrop(changeLeash, -1, -1);
        transaction.setShadowRadius(changeLeash, 0.0f);
    }

    @Override // com.android.wm.shell.transition.change.ChangeTransitionSpec
    public final String toString() {
        return "CaptionChangeTransitionSpec" + super.toString();
    }
}
