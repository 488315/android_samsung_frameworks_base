package com.android.wm.shell.transition;

import android.graphics.Rect;
import android.view.DisplayInfo;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.PathInterpolator;
import android.view.animation.ScaleAnimation;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PopOverAnimationLoader extends AnimationLoader {
    public static final Interpolator POP_OVER_LINEAR_INTERPOLATOR = new LinearInterpolator();
    public static final Interpolator POP_OVER_CUSTOM_INTERPOLATOR = new PathInterpolator(0.4f, 0.6f, 0.0f, 1.0f);
    public static final Interpolator POP_OVER_SINE_IN_OUT_33_INTERPOLATOR = new PathInterpolator(0.33f, 0.0f, 0.67f, 1.0f);

    public PopOverAnimationLoader(MultiTaskingTransitionState multiTaskingTransitionState) {
        super(multiTaskingTransitionState);
    }

    @Override // com.android.wm.shell.transition.AnimationLoader
    public final boolean isAvailable() {
        return this.mState.mIsPopOverAnimationNeeded;
    }

    @Override // com.android.wm.shell.transition.AnimationLoader
    public final void loadAnimationIfPossible() {
        DisplayInfo displayInfo = new DisplayInfo();
        MultiTaskingTransitionState multiTaskingTransitionState = this.mState;
        multiTaskingTransitionState.mDisplayController.getDisplayContext(multiTaskingTransitionState.mDisplayId).getDisplay().getDisplayInfo(displayInfo);
        Rect rect = new Rect(0, 0, displayInfo.appWidth, displayInfo.appHeight);
        float width = (rect.width() / 2.0f) + rect.left;
        float height = (rect.height() / 2.0f) + rect.top;
        AnimationSet animationSet = new AnimationSet(false);
        int i = multiTaskingTransitionState.mTransitionType;
        boolean z = i == 1 || i == 3;
        Interpolator interpolator = POP_OVER_LINEAR_INTERPOLATOR;
        if (z) {
            AlphaAnimation alphaAnimation = multiTaskingTransitionState.mIsEnter ? new AlphaAnimation(0.0f, 1.0f) : new AlphaAnimation(1.0f, 0.0f);
            alphaAnimation.setInterpolator(interpolator);
            if (multiTaskingTransitionState.isActivityTypeHome() && !multiTaskingTransitionState.mIsEnter && multiTaskingTransitionState.mChange.getMode() == 2) {
                alphaAnimation.setDuration(80L);
            } else {
                alphaAnimation.setDuration(150L);
                alphaAnimation.setStartOffset(multiTaskingTransitionState.mIsEnter ? 0L : 150L);
            }
            animationSet.addAnimation(alphaAnimation);
            if (multiTaskingTransitionState.mIsEnter) {
                ScaleAnimation scaleAnimation = new ScaleAnimation(0.95f, 1.0f, 0.95f, 1.0f, width, height);
                scaleAnimation.setInterpolator(POP_OVER_CUSTOM_INTERPOLATOR);
                scaleAnimation.setDuration(350L);
                animationSet.addAnimation(scaleAnimation);
            }
        } else if (multiTaskingTransitionState.isClosingTransitionType() && !multiTaskingTransitionState.mIsEnter) {
            AlphaAnimation alphaAnimation2 = new AlphaAnimation(1.0f, 0.0f);
            alphaAnimation2.setInterpolator(interpolator);
            alphaAnimation2.setDuration(200L);
            animationSet.addAnimation(alphaAnimation2);
            ScaleAnimation scaleAnimation2 = new ScaleAnimation(1.0f, 0.95f, 1.0f, 0.95f, width, height);
            scaleAnimation2.setInterpolator(POP_OVER_SINE_IN_OUT_33_INTERPOLATOR);
            scaleAnimation2.setDuration(200L);
            animationSet.addAnimation(scaleAnimation2);
        }
        multiTaskingTransitionState.setAnimation(animationSet);
    }

    public final String toString() {
        return "PopOverAnimationLoader";
    }
}
