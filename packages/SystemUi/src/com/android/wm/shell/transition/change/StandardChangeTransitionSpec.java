package com.android.wm.shell.transition.change;

import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceControl;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ClipRectAnimation;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.window.TransitionInfo;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class StandardChangeTransitionSpec extends ChangeTransitionSpec {
    @Override // com.android.wm.shell.transition.change.ChangeTransitionSpec
    public final Animation createBoundsChangeAnimation() {
        Rect displayFrame = getDisplayFrame();
        Rect rect = this.mStartBounds;
        float width = rect.width();
        Rect rect2 = this.mEndBounds;
        ScaleAnimation scaleAnimation = new ScaleAnimation(width / rect2.width(), 1.0f, rect.height() / rect2.height(), 1.0f);
        TranslateAnimation translateAnimation = new TranslateAnimation(rect.left, rect2.left, rect.top, rect2.top);
        Rect rect3 = this.mStartOutsets;
        Rect rect4 = new Rect(-rect3.left, -rect3.top, rect.width() + rect3.right, rect.height() + rect3.bottom);
        Rect rect5 = this.mEndOutsets;
        Animation clipRectAnimation = new ClipRectAnimation(rect4, new Rect(-rect5.left, -rect5.top, rect2.width() + rect5.right, rect2.height() + rect5.bottom));
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(translateAnimation);
        animationSet.addAnimation(clipRectAnimation);
        animationSet.setStartOffset(0L);
        animationSet.setDuration(getAnimationDuration());
        animationSet.setInterpolator(ChangeTransitionSpec.ANIMATION_INTERPOLATOR);
        animationSet.initialize(rect.width(), rect.height(), displayFrame.width(), displayFrame.height());
        animationSet.setHasRoundedCorners(true);
        animationSet.setRoundedCornerRadius(getCornerRadius());
        return animationSet;
    }

    @Override // com.android.wm.shell.transition.change.ChangeTransitionSpec
    public Animation createSnapshotAnimation() {
        long animationDuration = getAnimationDuration();
        Rect rect = this.mStartOutsets;
        float f = -rect.left;
        float f2 = -rect.top;
        TranslateAnimation translateAnimation = new TranslateAnimation(f, f, f2, f2);
        Rect rect2 = this.mStartBounds;
        float width = rect2.width();
        Rect rect3 = this.mEndBounds;
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(getSnapshotAlphaAnimationDuration());
        alphaAnimation.setStartOffset((long) (this.mDurationScale * 50.0f));
        alphaAnimation.setInterpolator(new LinearInterpolator());
        float width2 = 1.0f / (width / rect3.width());
        float height = 1.0f / (rect2.height() / rect3.height());
        ScaleAnimation scaleAnimation = new ScaleAnimation(width2, width2, height, height);
        scaleAnimation.setDuration(animationDuration);
        scaleAnimation.setInterpolator(ChangeTransitionSpec.ANIMATION_INTERPOLATOR);
        AnimationSet animationSet = new AnimationSet(false);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(translateAnimation);
        animationSet.initialize(rect2.width(), rect2.height(), rect3.width(), rect3.height());
        return animationSet;
    }

    public float getCornerRadius() {
        if (this.mChange.getConfiguration().windowConfiguration.getWindowingMode() == 5) {
            return ChangeTransitionSpec.dipToPixel(14, this.mContext);
        }
        return 0.0f;
    }

    @Override // com.android.wm.shell.transition.change.ChangeTransitionSpec
    public final void setupChangeTransitionHierarchy(TransitionInfo.Change change, SurfaceControl.Transaction transaction) {
        SurfaceControl snapshot = change.getSnapshot();
        SurfaceControl leash = change.getLeash();
        SurfaceControl changeLeash = change.getChangeLeash();
        if (snapshot == null || leash == null || changeLeash == null) {
            Log.w("ChangeTransitionProvider", "setupChangeTransitionHierarchy: invalid surfaces, snapshot=" + snapshot + ", container=" + leash + ", change=" + changeLeash);
            return;
        }
        transaction.setWindowCrop(leash, -1, -1);
        transaction.reparent(leash, changeLeash);
        Rect rect = this.mStartBounds;
        transaction.setPosition(leash, rect.left, rect.top);
        transaction.reparent(snapshot, leash);
        transaction.setLayer(snapshot, Integer.MAX_VALUE);
        transaction.setPosition(changeLeash, 0.0f, 0.0f);
        transaction.setWindowCrop(changeLeash, -1, -1);
        Log.d("ChangeTransitionProvider", "setupChangeTransitionHierarchy: reparent " + snapshot + " to " + leash + ", change=" + changeLeash);
    }

    @Override // com.android.wm.shell.transition.change.ChangeTransitionSpec
    public final String toString() {
        return "StandardChangeTransition" + super.toString();
    }
}
