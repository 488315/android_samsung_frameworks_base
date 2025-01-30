package com.android.p038wm.shell.transition;

import android.graphics.PointF;
import android.view.DisplayInfo;
import android.view.animation.Animation;
import com.android.systemui.R;
import com.samsung.android.rune.CoreRune;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DexAnimationLoader extends AnimationLoader {
    public DexAnimationLoader(MultiTaskingTransitionState multiTaskingTransitionState) {
        super(multiTaskingTransitionState);
    }

    @Override // com.android.p038wm.shell.transition.AnimationLoader
    public final boolean isAvailable() {
        return this.mState.mConfiguration.semDesktopModeEnabled == 1;
    }

    /* JADX WARN: Code restructure failed: missing block: B:39:0x0091, code lost:
    
        if (r8.mIsEnter == false) goto L44;
     */
    @Override // com.android.p038wm.shell.transition.AnimationLoader
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void loadAnimationIfPossible() {
        Animation createMinimizeAnimation;
        MultiTaskingTransitionState multiTaskingTransitionState = this.mState;
        if (multiTaskingTransitionState.isActivityTypeHome()) {
            multiTaskingTransitionState.setAnimation(AnimationLoader.DIRECT_SHOW_ANIMATION);
            return;
        }
        boolean z = true;
        if (CoreRune.MD_DEX_MINIMIZE_SHELL_TRANSITION) {
            multiTaskingTransitionState.mDisplayController.getDisplayContext(multiTaskingTransitionState.mDisplayId).getDisplay().getDisplayInfo(new DisplayInfo());
            PointF pointF = new PointF();
            pointF.set(r0.logicalWidth / 2.0f, r0.logicalHeight);
            int i = multiTaskingTransitionState.mMinimizeAnimState;
            if (i == 1) {
                createMinimizeAnimation = multiTaskingTransitionState.createMinimizeAnimation(false, pointF, multiTaskingTransitionState.getBounds(), 1.0f, false);
            } else {
                createMinimizeAnimation = i == 2 ? multiTaskingTransitionState.createMinimizeAnimation(true, pointF, multiTaskingTransitionState.getBounds(), 1.0f, false) : null;
            }
            if (createMinimizeAnimation != null) {
                multiTaskingTransitionState.setAnimation(createMinimizeAnimation);
            }
            if (multiTaskingTransitionState.mAnimationLoaded) {
                return;
            }
        }
        if (multiTaskingTransitionState.mWindowingMode == 1) {
            if (multiTaskingTransitionState.mTaskInfo != null) {
                if (multiTaskingTransitionState.isClosingTransitionType()) {
                    if (!multiTaskingTransitionState.isActivityTypeHome()) {
                    }
                }
            }
        }
        z = false;
        if (z) {
            multiTaskingTransitionState.setAnimation(multiTaskingTransitionState.loadAnimationFromResources(R.anim.freeform_close_exit));
        }
    }

    public final String toString() {
        return "DexAnimationLoader";
    }
}
