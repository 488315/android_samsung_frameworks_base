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
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;
import com.android.wm.shell.transition.change.ChangeTransitionSpec;
import com.samsung.android.rune.CoreRune;
import java.util.function.Predicate;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class StandardChangeTransitionSpec extends ChangeTransitionSpec {
    @Override // com.android.wm.shell.transition.change.ChangeTransitionSpec
    public final Animation createBoundsChangeAnimation() {
        float f;
        int dipToPixel;
        Rect displayFrame = getDisplayFrame();
        ScaleAnimation scaleAnimation = new ScaleAnimation(this.mStartBounds.width() / this.mEndBounds.width(), 1.0f, this.mStartBounds.height() / this.mEndBounds.height(), 1.0f);
        float f2 = this.mStartBounds.left;
        Rect rect = this.mEndBounds;
        TranslateAnimation translateAnimation = new TranslateAnimation(f2, rect.left, r2.top, rect.top);
        Rect rect2 = this.mStartOutsets;
        Rect rect3 = new Rect(-rect2.left, -rect2.top, this.mStartBounds.width() + this.mStartOutsets.right, this.mStartBounds.height() + this.mStartOutsets.bottom);
        Rect rect4 = this.mEndOutsets;
        Animation clipRectAnimation = new ClipRectAnimation(rect3, new Rect(-rect4.left, -rect4.top, this.mEndBounds.width() + this.mEndOutsets.right, this.mEndBounds.height() + this.mEndOutsets.bottom));
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(translateAnimation);
        animationSet.addAnimation(clipRectAnimation);
        animationSet.setStartOffset(0L);
        animationSet.setDuration(getAnimationDuration());
        animationSet.setInterpolator(this.mAnimationAttributePolicy.mDefaultInterpolator);
        animationSet.initialize(this.mStartBounds.width(), this.mStartBounds.height(), displayFrame.width(), displayFrame.height());
        animationSet.setHasRoundedCorners(true);
        if (CoreRune.DW_SHELL_CHANGE_TRANSITION) {
            TransitionInfo.Change change = this.mChange;
            int endDisplayId = change.getTaskInfo() != null ? change.getTaskInfo().displayId : change.getEndDisplayId();
            DesktopStateImpl.Companion.getClass();
            if (DesktopStateImpl.Companion.inDesktopWindowing(endDisplayId)) {
                dipToPixel = ChangeTransitionSpec.dipToPixel(8, this.mContext);
                f = dipToPixel;
                animationSet.setRoundedCornerRadius(f);
                return animationSet;
            }
        }
        if (this.mChange.getConfiguration().windowConfiguration.getWindowingMode() != 5) {
            f = 0.0f;
            animationSet.setRoundedCornerRadius(f);
            return animationSet;
        }
        dipToPixel = ChangeTransitionSpec.dipToPixel(14, this.mContext);
        f = dipToPixel;
        animationSet.setRoundedCornerRadius(f);
        return animationSet;
    }

    @Override // com.android.wm.shell.transition.change.ChangeTransitionSpec
    public final Animation createSnapshotAnimation() {
        long animationDuration = getAnimationDuration();
        Rect rect = this.mStartOutsets;
        float f = -rect.left;
        float f2 = -rect.top;
        TranslateAnimation translateAnimation = new TranslateAnimation(f, f, f2, f2);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        float f3 = this.mDurationScale;
        ChangeTransitionSpec.AnimationAttributePolicy animationAttributePolicy = this.mAnimationAttributePolicy;
        animationAttributePolicy.getClass();
        alphaAnimation.setDuration((long) (f3 * 100));
        float f4 = this.mDurationScale;
        animationAttributePolicy.getClass();
        alphaAnimation.setStartOffset((long) (f4 * ChangeTransitionSpec.AnimationAttributePolicy.SNAPSHOT_ALPHA_ANIM_START_OFFSET));
        alphaAnimation.setInterpolator(new LinearInterpolator());
        float width = 1.0f / (this.mStartBounds.width() / this.mEndBounds.width());
        float height = 1.0f / (this.mStartBounds.height() / this.mEndBounds.height());
        ScaleAnimation scaleAnimation = new ScaleAnimation(width, width, height, height);
        scaleAnimation.setDuration(animationDuration);
        scaleAnimation.setInterpolator(animationAttributePolicy.mDefaultInterpolator);
        AnimationSet animationSet = new AnimationSet(false);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(translateAnimation);
        animationSet.initialize(this.mStartBounds.width(), this.mStartBounds.height(), this.mEndBounds.width(), this.mEndBounds.height());
        return animationSet;
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
        transaction.setShadowRadius(changeLeash, 0.0f);
        if (CoreRune.MW_EMBED_ACTIVITY_ANIMATION) {
            TransitionInfo.Change findChange = (this.mChange.getTaskInfo() == null || !this.mChange.getTaskInfo().isFreeform()) ? null : this.mTransitionInfo.findChange(new Predicate() { // from class: com.android.wm.shell.transition.change.StandardChangeTransitionSpec$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    StandardChangeTransitionSpec standardChangeTransitionSpec = StandardChangeTransitionSpec.this;
                    TransitionInfo.Change change2 = (TransitionInfo.Change) obj;
                    standardChangeTransitionSpec.getClass();
                    return change2.getParent() != null && standardChangeTransitionSpec.mTransitionInfo.getChange(change2.getParent()) == standardChangeTransitionSpec.mChange && change2.getTaskFragmentToken() != null && change2.hasFlags(512) && !change2.hasFlags(1024) && change2.getEndRelOffset().equals(0, 0);
                }
            });
            if (findChange != null && !findChange.getEndAbsBounds().isEmpty()) {
                float width = (r4 + 1) / findChange.getEndAbsBounds().width();
                transaction.setScale(findChange.getLeash(), width, 1.0f);
                Log.d("ChangeTransitionProvider", "setupTaskFragmentLeashIfNeeded: " + findChange.getLeash() + ", scaleX=" + width);
            }
        }
        Log.d("ChangeTransitionProvider", "setupChangeTransitionHierarchy: reparent " + snapshot + " to " + leash + ", change=" + changeLeash);
    }

    @Override // com.android.wm.shell.transition.change.ChangeTransitionSpec
    public final String toString() {
        return "StandardChangeTransition" + super.toString();
    }
}
