package com.android.wm.shell.transition;

import android.content.Context;
import android.graphics.Rect;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ClipRectAnimation;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class AnimationLoader {
    public final MultiTaskingTransitionState mState;
    public static final Animation NO_ANIMATION = new AlphaAnimation(0.0f, 0.0f);
    public static final Animation DIRECT_SHOW_ANIMATION = new AlphaAnimation(1.0f, 1.0f);

    public AnimationLoader(MultiTaskingTransitionState multiTaskingTransitionState) {
        this.mState = multiTaskingTransitionState;
    }

    public final void addRoundedClipAnimation(Rect rect, AnimationSet animationSet, Context context) {
        Rect rect2 = new Rect(rect);
        rect2.offsetTo(0, 0);
        float cornerRadius = getCornerRadius(context);
        ClipRectAnimation clipRectAnimation = new ClipRectAnimation(rect2, rect2);
        clipRectAnimation.setDuration(animationSet.getDuration());
        animationSet.addAnimation(clipRectAnimation);
        animationSet.setHasRoundedCorners(true);
        animationSet.setRoundedCornerRadius(cornerRadius);
    }

    public float getCornerRadius(Context context) {
        return 0.0f;
    }

    public abstract boolean isAvailable();

    public abstract void loadAnimationIfPossible();

    public void addRoundedClipAnimation(Rect rect, AnimationSet animationSet) {
        MultiTaskingTransitionState multiTaskingTransitionState = this.mState;
        addRoundedClipAnimation(rect, animationSet, multiTaskingTransitionState.mDisplayController.getDisplayContext(multiTaskingTransitionState.mDisplayId));
    }
}
