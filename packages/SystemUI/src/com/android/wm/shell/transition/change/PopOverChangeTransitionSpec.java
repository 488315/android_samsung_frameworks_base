package com.android.wm.shell.transition.change;

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
import com.android.wm.shell.transition.change.ChangeTransitionSpec;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class PopOverChangeTransitionSpec extends ChangeTransitionSpec {
    @Override // com.android.wm.shell.transition.change.ChangeTransitionSpec
    public final Animation createBoundsChangeAnimation() {
        Rect displayFrame = getDisplayFrame();
        Rect rect = this.mEndBounds;
        Rect rect2 = this.mStartBounds;
        int i = -rect2.left;
        Point point = this.mRootOffsets;
        rect.offset(i - point.x, (-rect2.top) - point.y);
        Rect rect3 = this.mStartBounds;
        Point point2 = this.mRootOffsets;
        rect3.offsetTo(-point2.x, -point2.y);
        ScaleAnimation scaleAnimation = new ScaleAnimation(this.mStartBounds.width() / this.mEndBounds.width(), 1.0f, this.mStartBounds.height() / this.mEndBounds.height(), 1.0f);
        float f = this.mStartBounds.left;
        Rect rect4 = this.mEndBounds;
        TranslateAnimation translateAnimation = new TranslateAnimation(f, rect4.left, r2.top, rect4.top);
        Animation clipRectAnimation = new ClipRectAnimation(new Rect(0, 0, this.mStartBounds.width(), this.mStartBounds.height()), new Rect(0, 0, this.mEndBounds.width(), this.mEndBounds.height()));
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(translateAnimation);
        animationSet.addAnimation(clipRectAnimation);
        animationSet.setStartOffset(0L);
        animationSet.setDuration(getAnimationDuration());
        animationSet.setInterpolator(this.mAnimationAttributePolicy.mDefaultInterpolator);
        animationSet.initialize(this.mStartBounds.width(), this.mStartBounds.height(), displayFrame.width(), displayFrame.height());
        return animationSet;
    }

    @Override // com.android.wm.shell.transition.change.ChangeTransitionSpec
    public final Animation createSnapshotAnimation() {
        long animationDuration = getAnimationDuration();
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        float f = this.mDurationScale;
        ChangeTransitionSpec.AnimationAttributePolicy animationAttributePolicy = this.mAnimationAttributePolicy;
        animationAttributePolicy.getClass();
        alphaAnimation.setDuration((long) (f * 100));
        float f2 = this.mDurationScale;
        animationAttributePolicy.getClass();
        alphaAnimation.setStartOffset((long) (f2 * ChangeTransitionSpec.AnimationAttributePolicy.SNAPSHOT_ALPHA_ANIM_START_OFFSET));
        alphaAnimation.setInterpolator(new LinearInterpolator());
        float width = 1.0f / (this.mStartBounds.width() / this.mEndBounds.width());
        float height = 1.0f / (this.mStartBounds.height() / this.mEndBounds.height());
        ScaleAnimation scaleAnimation = new ScaleAnimation(width, width, height, height);
        scaleAnimation.setDuration(animationDuration);
        scaleAnimation.setInterpolator(animationAttributePolicy.mDefaultInterpolator);
        AnimationSet animationSet = new AnimationSet(false);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(scaleAnimation);
        animationSet.initialize(this.mStartBounds.width(), this.mStartBounds.height(), this.mEndBounds.width(), this.mEndBounds.height());
        return animationSet;
    }

    @Override // com.android.wm.shell.transition.change.ChangeTransitionSpec
    public final boolean isRootOffsetNeeded() {
        return true;
    }

    @Override // com.android.wm.shell.transition.change.ChangeTransitionSpec
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
        transaction.setShadowRadius(changeLeash, 0.0f);
        Log.d("PopOverChangeTransitionSpec", "setupChangeTransitionHierarchy: reparent " + snapshot + " to " + leash + ", change=" + changeLeash);
    }
}
