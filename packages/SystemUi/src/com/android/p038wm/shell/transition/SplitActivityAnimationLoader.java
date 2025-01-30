package com.android.p038wm.shell.transition;

import android.R;
import android.view.animation.Animation;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SplitActivityAnimationLoader extends AnimationLoader {
    public SplitActivityAnimationLoader(MultiTaskingTransitionState multiTaskingTransitionState) {
        super(multiTaskingTransitionState);
    }

    @Override // com.android.p038wm.shell.transition.AnimationLoader
    public final boolean isAvailable() {
        this.mState.getClass();
        return false;
    }

    @Override // com.android.p038wm.shell.transition.AnimationLoader
    public final void loadAnimationIfPossible() {
        Animation animation;
        MultiTaskingTransitionState multiTaskingTransitionState = this.mState;
        int i = multiTaskingTransitionState.mTransitionType;
        boolean z = true;
        if (i != 1 && i != 3) {
            z = false;
        }
        if (z) {
            animation = multiTaskingTransitionState.loadAnimationFromResources(multiTaskingTransitionState.mIsEnter ? R.anim.ft_avd_toarrow_rectangle_path_3_animation : R.anim.ft_avd_toarrow_rectangle_path_4_animation);
        } else if (multiTaskingTransitionState.isClosingTransitionType()) {
            animation = multiTaskingTransitionState.loadAnimationFromResources(multiTaskingTransitionState.mIsEnter ? R.anim.ft_avd_toarrow_rectangle_path_1_animation : R.anim.ft_avd_toarrow_rectangle_path_2_animation);
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
