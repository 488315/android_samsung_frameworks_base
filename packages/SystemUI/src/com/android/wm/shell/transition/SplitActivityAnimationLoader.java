package com.android.wm.shell.transition;

import android.R;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        Animation animation;
        Interpolator interpolator = this.mFastOutExtraSlowInInterpolator;
        MultiTaskingTransitionState multiTaskingTransitionState = this.mState;
        if (interpolator == null) {
            this.mFastOutExtraSlowInInterpolator = AnimationUtils.loadInterpolator(multiTaskingTransitionState.mDisplayController.getDisplayContext(multiTaskingTransitionState.mDisplayId), R.interpolator.fast_out_extra_slow_in);
        }
        multiTaskingTransitionState.getClass();
        if (multiTaskingTransitionState.isOpeningTransitionType()) {
            animation = multiTaskingTransitionState.loadAnimationFromResources(multiTaskingTransitionState.mIsEnter ? 17432931 : 17432932);
        } else if (multiTaskingTransitionState.isClosingTransitionType()) {
            animation = multiTaskingTransitionState.loadAnimationFromResources(multiTaskingTransitionState.mIsEnter ? 17432929 : 17432930);
        } else {
            animation = null;
        }
        if (animation != null) {
            animation.setDuration(336L);
            multiTaskingTransitionState.setAnimation(animation);
        }
    }

    public final String toString() {
        return "SplitActivityAnimationLoader";
    }
}
