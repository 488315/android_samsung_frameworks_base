package com.android.p038wm.shell.transition.change;

import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceControl;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ClipRectAnimation;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.view.animation.TranslateAnimation;
import android.window.TransitionInfo;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class CaptionChangeTransitionSpec extends ChangeTransitionSpec {
    public static final String TAG = TAG;
    public static final String TAG = TAG;
    public static final Interpolator RESIZE_DECELERATE_INTERPOLATOR = new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f);

    @Override // com.android.p038wm.shell.transition.change.ChangeTransitionSpec
    public final Animation createBoundsChangeAnimation() {
        AnimationSet animationSet = new AnimationSet(true);
        Rect rect = this.mStartBounds;
        float f = rect.left;
        Rect rect2 = this.mEndBounds;
        TranslateAnimation translateAnimation = new TranslateAnimation(f, rect2.left, rect.top, rect2.top);
        translateAnimation.setDuration(getAnimationDuration());
        animationSet.addAnimation(translateAnimation);
        Rect rect3 = this.mStartOutsets;
        Rect rect4 = new Rect(-rect3.left, -rect3.top, rect.width() + rect3.right, rect.height() + rect3.bottom);
        Rect rect5 = this.mEndOutsets;
        ClipRectAnimation clipRectAnimation = new ClipRectAnimation(rect4, new Rect(-rect5.left, -rect5.top, rect2.width() + rect5.right, rect2.height() + rect5.bottom));
        clipRectAnimation.setDuration(getAnimationDuration());
        animationSet.addAnimation(clipRectAnimation);
        animationSet.setInterpolator(RESIZE_DECELERATE_INTERPOLATOR);
        Rect displayFrame = getDisplayFrame();
        animationSet.initialize(rect.width(), rect.height(), displayFrame.width(), displayFrame.height());
        animationSet.setHasRoundedCorners(true);
        animationSet.setRoundedCornerRadius(ChangeTransitionSpec.dipToPixel(14, this.mContext));
        return animationSet;
    }

    @Override // com.android.p038wm.shell.transition.change.ChangeTransitionSpec
    public final Animation createSnapshotAnimation() {
        AnimationSet animationSet = new AnimationSet(true);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 1.0f);
        alphaAnimation.setDuration(getAnimationDuration());
        animationSet.addAnimation(alphaAnimation);
        Rect rect = this.mStartOutsets;
        int i = -rect.left;
        int i2 = -rect.top;
        int i3 = this.mEndBounds.bottom;
        Rect rect2 = this.mStartBounds;
        float f = i;
        TranslateAnimation translateAnimation = new TranslateAnimation(f, f, i2, ((i2 + rect2.top) - r3.top) + (i3 - rect2.bottom));
        translateAnimation.setDuration(getAnimationDuration());
        animationSet.addAnimation(translateAnimation);
        animationSet.setInterpolator(RESIZE_DECELERATE_INTERPOLATOR);
        animationSet.initialize(rect2.width(), rect2.height(), rect2.width(), rect2.height());
        return animationSet;
    }

    @Override // com.android.p038wm.shell.transition.change.ChangeTransitionSpec
    public final void setupChangeTransitionHierarchy(TransitionInfo.Change change, SurfaceControl.Transaction transaction) {
        SurfaceControl snapshot = change.getSnapshot();
        SurfaceControl leash = change.getLeash();
        SurfaceControl changeLeash = change.getChangeLeash();
        String str = TAG;
        if (snapshot == null || leash == null || changeLeash == null) {
            Log.w(str, "setupChangeTransitionHierarchy: invalid surfaces, snapshot=" + snapshot + ", container=" + leash + ", change=" + changeLeash + ", " + this);
            return;
        }
        transaction.setWindowCrop(leash, -1, -1);
        Rect rect = this.mStartBounds;
        transaction.setPosition(leash, rect.left, rect.top);
        transaction.reparent(snapshot, leash);
        transaction.setLayer(snapshot, Integer.MAX_VALUE);
        transaction.setPosition(changeLeash, 0.0f, 0.0f);
        transaction.setWindowCrop(changeLeash, -1, -1);
        Log.d(str, "setupChangeTransitionHierarchy: reparent " + snapshot + " to " + leash + ", change=" + changeLeash);
    }

    @Override // com.android.p038wm.shell.transition.change.ChangeTransitionSpec
    public final String toString() {
        return "CaptionChangeTransitionSpec" + super.toString();
    }
}
