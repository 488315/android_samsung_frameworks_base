package com.android.wm.shell.transition;

import android.R;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

/* loaded from: classes3.dex */
public class SplitActivityAnimationLoader extends AnimationLoader {
    public Interpolator mFastOutExtraSlowInInterpolator;

    public SplitActivityAnimationLoader(MultiTaskingTransitionState multiTaskingTransitionState) {
        super(multiTaskingTransitionState);
    }

    @Override // com.android.wm.shell.transition.AnimationLoader
    public final boolean isAvailable() {
        this.mState.getClass();
        return false;
    }

    @Override // com.android.wm.shell.transition.AnimationLoader
    public final void loadAnimationIfPossible() {
        Animation animationLoadAnimationFromResources;
        Interpolator interpolator = this.mFastOutExtraSlowInInterpolator;
        MultiTaskingTransitionState multiTaskingTransitionState = this.mState;
        if (interpolator == null) {
            this.mFastOutExtraSlowInInterpolator = AnimationUtils.loadInterpolator(multiTaskingTransitionState.mDisplayController.getDisplayContext(multiTaskingTransitionState.mDisplayId), R.interpolator.fast_out_extra_slow_in);
        }
        multiTaskingTransitionState.getClass();
        if (multiTaskingTransitionState.isOpeningTransitionType()) {
            animationLoadAnimationFromResources = multiTaskingTransitionState.loadAnimationFromResources(multiTaskingTransitionState.mIsEnter ? 17432931 : 17432932);
        } else if (multiTaskingTransitionState.isClosingTransitionType()) {
            animationLoadAnimationFromResources = multiTaskingTransitionState.loadAnimationFromResources(multiTaskingTransitionState.mIsEnter ? 17432929 : 17432930);
        } else {
            animationLoadAnimationFromResources = null;
        }
        if (animationLoadAnimationFromResources != null) {
            animationLoadAnimationFromResources.setDuration(336L);
            multiTaskingTransitionState.setAnimation(animationLoadAnimationFromResources);
        }
    }

    public final String toString() {
        return "SplitActivityAnimationLoader";
    }
}
