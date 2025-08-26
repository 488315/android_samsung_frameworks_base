package com.android.wm.shell.transition;

import android.app.WindowConfiguration;
import android.content.Context;
import android.util.Slog;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import androidx.slice.widget.ActionRow$$ExternalSyntheticOutline0;
import com.android.systemui.R;

/* loaded from: classes3.dex */
public class SplitAnimationLoader extends AnimationLoader {
    public SplitAnimationLoader(MultiTaskingTransitionState multiTaskingTransitionState) {
        super(multiTaskingTransitionState);
    }

    @Override // com.android.wm.shell.transition.AnimationLoader
    public final float getCornerRadius(Context context) {
        if (context == null) {
            return 0.0f;
        }
        return (int) ActionRow$$ExternalSyntheticOutline0.m(context, 1, 12);
    }

    @Override // com.android.wm.shell.transition.AnimationLoader
    public final boolean isAvailable() {
        MultiTaskingTransitionState multiTaskingTransitionState = this.mState;
        return multiTaskingTransitionState.mWindowingMode == 6 && WindowConfiguration.isSplitScreenWindowingMode(multiTaskingTransitionState.mConfiguration.windowConfiguration);
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x003e  */
    @Override // com.android.wm.shell.transition.AnimationLoader
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void loadAnimationIfPossible() {
        int i;
        MultiTaskingTransitionState multiTaskingTransitionState = this.mState;
        if (multiTaskingTransitionState.mHasCustomDisplayChangeTransition || multiTaskingTransitionState.mSeparatedFromCustomDisplayChange) {
            multiTaskingTransitionState.setAnimation(AnimationLoader.NO_ANIMATION);
            return;
        }
        if (multiTaskingTransitionState.mIsPopOverAnimationNeeded) {
            return;
        }
        if (multiTaskingTransitionState.mOpeningAppsEdgeTaskInfo == null) {
            i = multiTaskingTransitionState.isOpeningTransitionType() ? multiTaskingTransitionState.mIsEnter ? R.anim.split_open_enter : R.anim.split_open_exit : multiTaskingTransitionState.isClosingTransitionType() ? multiTaskingTransitionState.mIsEnter ? R.anim.split_close_enter : R.anim.split_close_exit : -1;
        } else {
            int stageType = multiTaskingTransitionState.mConfiguration.windowConfiguration.getStageType();
            int stageType2 = multiTaskingTransitionState.mOpeningAppsEdgeTaskInfo.getConfiguration().windowConfiguration.getStageType();
            if (stageType != 0 && stageType == stageType2 && !multiTaskingTransitionState.mIsEnter) {
                i = R.anim.split_wallpaper_open_exit;
            }
        }
        if (i != -1) {
            Animation animationLoadAnimationFromResources = multiTaskingTransitionState.loadAnimationFromResources(i);
            if ((i == R.anim.split_open_enter || i == R.anim.split_close_exit) && (animationLoadAnimationFromResources instanceof AnimationSet)) {
                addRoundedClipAnimation(multiTaskingTransitionState.getBounds(), (AnimationSet) animationLoadAnimationFromResources);
            }
            if (animationLoadAnimationFromResources == null) {
                Slog.w("SplitAnimationLoader", "loadAnimationIfPossible: animation is null");
            } else {
                multiTaskingTransitionState.setAnimation(animationLoadAnimationFromResources);
            }
        }
    }

    public final String toString() {
        return "SplitAnimationLoader";
    }
}
