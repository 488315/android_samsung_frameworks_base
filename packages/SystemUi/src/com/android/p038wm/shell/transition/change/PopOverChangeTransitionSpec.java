package com.android.p038wm.shell.transition.change;

import android.graphics.Point;
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
public final class PopOverChangeTransitionSpec extends ChangeTransitionSpec {
    @Override // com.android.p038wm.shell.transition.change.ChangeTransitionSpec
    public final Animation createBoundsChangeAnimation() {
        Rect displayFrame = getDisplayFrame();
        Rect rect = this.mStartBounds;
        int i = -rect.left;
        Point point = this.mRootOffsets;
        int i2 = i - point.x;
        int i3 = (-rect.top) - point.y;
        Rect rect2 = this.mEndBounds;
        rect2.offset(i2, i3);
        rect.offsetTo(-point.x, -point.y);
        ScaleAnimation scaleAnimation = new ScaleAnimation(rect.width() / rect2.width(), 1.0f, rect.height() / rect2.height(), 1.0f);
        TranslateAnimation translateAnimation = new TranslateAnimation(rect.left, rect2.left, rect.top, rect2.top);
        Animation clipRectAnimation = new ClipRectAnimation(new Rect(0, 0, rect.width(), rect.height()), new Rect(0, 0, rect2.width(), rect2.height()));
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(translateAnimation);
        animationSet.addAnimation(clipRectAnimation);
        animationSet.setStartOffset(0L);
        animationSet.setDuration(getAnimationDuration());
        animationSet.setInterpolator(ChangeTransitionSpec.ANIMATION_INTERPOLATOR);
        animationSet.initialize(rect.width(), rect.height(), displayFrame.width(), displayFrame.height());
        return animationSet;
    }

    @Override // com.android.p038wm.shell.transition.change.ChangeTransitionSpec
    public final Animation createSnapshotAnimation() {
        long animationDuration = getAnimationDuration();
        Rect rect = this.mStartBounds;
        float width = rect.width();
        Rect rect2 = this.mEndBounds;
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(getSnapshotAlphaAnimationDuration());
        alphaAnimation.setStartOffset((long) (this.mDurationScale * 50.0f));
        alphaAnimation.setInterpolator(new LinearInterpolator());
        float width2 = 1.0f / (width / rect2.width());
        float height = 1.0f / (rect.height() / rect2.height());
        ScaleAnimation scaleAnimation = new ScaleAnimation(width2, width2, height, height);
        scaleAnimation.setDuration(animationDuration);
        scaleAnimation.setInterpolator(ChangeTransitionSpec.ANIMATION_INTERPOLATOR);
        AnimationSet animationSet = new AnimationSet(false);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(scaleAnimation);
        animationSet.initialize(rect.width(), rect.height(), rect2.width(), rect2.height());
        return animationSet;
    }

    @Override // com.android.p038wm.shell.transition.change.ChangeTransitionSpec
    public final boolean isRootOffsetNeeded() {
        return true;
    }

    @Override // com.android.p038wm.shell.transition.change.ChangeTransitionSpec
    public final void setupChangeTransitionHierarchy(TransitionInfo.Change change, SurfaceControl.Transaction transaction) {
        SurfaceControl snapshot = change.getSnapshot();
        SurfaceControl leash = change.getLeash();
        SurfaceControl changeLeash = change.getChangeLeash();
        if (snapshot == null || leash == null || changeLeash == null) {
            Log.w("PopOverChangeTransitionSpec", "setupChangeTransitionHierarchy: invalid surfaces, snapshot=" + snapshot + ", container=" + leash + ", change=" + changeLeash);
            return;
        }
        transaction.setCornerRadius(leash, ChangeTransitionSpec.dipToPixel(26, this.mContext));
        transaction.setWindowCrop(leash, -1, -1);
        transaction.reparent(leash, changeLeash);
        transaction.setPosition(leash, 0.0f, 0.0f);
        transaction.setCornerRadius(snapshot, ChangeTransitionSpec.dipToPixel(26, this.mContext));
        transaction.reparent(snapshot, leash);
        transaction.setLayer(snapshot, Integer.MAX_VALUE);
        transaction.setPosition(snapshot, 0.0f, 0.0f);
        transaction.setWindowCrop(changeLeash, -1, -1);
        Log.d("PopOverChangeTransitionSpec", "setupChangeTransitionHierarchy: reparent " + snapshot + " to " + leash + ", change=" + changeLeash);
    }
}
