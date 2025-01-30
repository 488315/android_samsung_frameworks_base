package com.android.p038wm.shell.transition;

import android.app.WindowConfiguration;
import android.util.TypedValue;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SplitAnimationLoader extends AnimationLoader {
    public SplitAnimationLoader(MultiTaskingTransitionState multiTaskingTransitionState) {
        super(multiTaskingTransitionState);
    }

    @Override // com.android.p038wm.shell.transition.AnimationLoader
    public final float getCornerRadius() {
        MultiTaskingTransitionState multiTaskingTransitionState = this.mState;
        if (multiTaskingTransitionState.mDisplayController.getDisplayContext(multiTaskingTransitionState.mDisplayId) == null) {
            return 0.0f;
        }
        return (int) TypedValue.applyDimension(1, 12, r2.getResources().getDisplayMetrics());
    }

    @Override // com.android.p038wm.shell.transition.AnimationLoader
    public final boolean isAvailable() {
        MultiTaskingTransitionState multiTaskingTransitionState = this.mState;
        return multiTaskingTransitionState.mWindowingMode == 6 && WindowConfiguration.isSplitScreenWindowingMode(multiTaskingTransitionState.mConfiguration.windowConfiguration);
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x0078  */
    /* JADX WARN: Removed duplicated region for block: B:35:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:42:0x005b  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0065  */
    @Override // com.android.p038wm.shell.transition.AnimationLoader
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void loadAnimationIfPossible() {
        boolean z;
        int i;
        MultiTaskingTransitionState multiTaskingTransitionState = this.mState;
        if (multiTaskingTransitionState.mHasCustomDisplayChangeTransition || multiTaskingTransitionState.mSeparatedFromCustomDisplayChange) {
            multiTaskingTransitionState.setAnimation(AnimationLoader.NO_ANIMATION);
            return;
        }
        if (multiTaskingTransitionState.mIsPopOverAnimationNeeded) {
            return;
        }
        if (multiTaskingTransitionState.mOpeningAppsEdgeTaskInfo != null) {
            int stageType = multiTaskingTransitionState.mConfiguration.windowConfiguration.getStageType();
            int stageType2 = multiTaskingTransitionState.mOpeningAppsEdgeTaskInfo.getConfiguration().windowConfiguration.getStageType();
            if (stageType != 0 && stageType == stageType2) {
                z = true;
                if (z || multiTaskingTransitionState.mIsEnter) {
                    int i2 = multiTaskingTransitionState.mTransitionType;
                    i = !(i2 != 1 || i2 == 3) ? multiTaskingTransitionState.mIsEnter ? R.anim.split_open_enter : R.anim.split_open_exit : multiTaskingTransitionState.isClosingTransitionType() ? multiTaskingTransitionState.mIsEnter ? R.anim.split_close_enter : R.anim.split_close_exit : -1;
                } else {
                    i = R.anim.split_wallpaper_open_exit;
                }
                if (i == -1) {
                    Animation loadAnimationFromResources = multiTaskingTransitionState.loadAnimationFromResources(i);
                    if ((i == R.anim.split_open_enter || i == R.anim.split_close_exit) && (loadAnimationFromResources instanceof AnimationSet)) {
                        addRoundedClipAnimation(multiTaskingTransitionState.getBounds(), (AnimationSet) loadAnimationFromResources);
                    }
                    multiTaskingTransitionState.setAnimation(loadAnimationFromResources);
                    return;
                }
                return;
            }
        }
        z = false;
        if (z) {
        }
        int i22 = multiTaskingTransitionState.mTransitionType;
        if (!(i22 != 1 || i22 == 3)) {
        }
        if (i == -1) {
        }
    }

    public final String toString() {
        return "SplitAnimationLoader";
    }
}
