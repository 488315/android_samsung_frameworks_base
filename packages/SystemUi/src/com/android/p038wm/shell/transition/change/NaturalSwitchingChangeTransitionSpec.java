package com.android.p038wm.shell.transition.change;

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

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NaturalSwitchingChangeTransitionSpec extends ChangeTransitionSpec {
    public final boolean isFreeform;

    public NaturalSwitchingChangeTransitionSpec(ActivityManager.RunningTaskInfo runningTaskInfo) {
        this.isFreeform = runningTaskInfo.getWindowingMode() == 5;
    }

    @Override // com.android.p038wm.shell.transition.change.ChangeTransitionSpec
    public final Animation createBoundsChangeAnimation() {
        AnimationSet animationSet = new AnimationSet(true);
        Rect rect = this.mEndBounds;
        int i = rect.left;
        int i2 = rect.top;
        TranslateAnimation translateAnimation = new TranslateAnimation(i, i, i2, i2);
        translateAnimation.setDuration(getAnimationDuration());
        animationSet.addAnimation(translateAnimation);
        if (this.isFreeform) {
            Rect rect2 = new Rect(rect);
            Rect rect3 = new Rect(rect);
            rect2.offsetTo(0, 0);
            rect3.offsetTo(0, 0);
            ClipRectAnimation clipRectAnimation = new ClipRectAnimation(rect2, rect3);
            clipRectAnimation.setDuration(getAnimationDuration());
            animationSet.addAnimation(clipRectAnimation);
            Rect displayFrame = getDisplayFrame();
            animationSet.initialize(rect.width(), rect.height(), displayFrame.width(), displayFrame.height());
        } else {
            AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 1.0f);
            alphaAnimation.setDuration(getAnimationDuration());
            animationSet.addAnimation(alphaAnimation);
        }
        return animationSet;
    }

    @Override // com.android.p038wm.shell.transition.change.ChangeTransitionSpec
    public final Animation createSnapshotAnimation() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 0.0f);
        alphaAnimation.setDuration(getAnimationDuration());
        return alphaAnimation;
    }

    @Override // com.android.p038wm.shell.transition.change.ChangeTransitionSpec
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
            transaction.setPosition(changeLeash, rect.left, rect.top);
        }
        transaction.setWindowCrop(changeLeash, -1, -1);
    }

    @Override // com.android.p038wm.shell.transition.change.ChangeTransitionSpec
    public final String toString() {
        return "CaptionChangeTransitionSpec" + super.toString();
    }
}
