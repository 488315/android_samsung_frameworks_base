package com.android.wm.shell.transition;

import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import com.android.systemui.R;
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;

/* loaded from: classes3.dex */
public class DesktopAnimationLoader extends AnimationLoader {
    public DesktopAnimationLoader(MultiTaskingTransitionState multiTaskingTransitionState) {
        super(multiTaskingTransitionState);
    }

    @Override // com.android.wm.shell.transition.AnimationLoader
    public final boolean isAvailable() {
        MultiTaskingTransitionState multiTaskingTransitionState = this.mState;
        int displayId = MultiTaskingTransitions.getDisplayId(multiTaskingTransitionState.mChange);
        DesktopStateImpl.Companion.getClass();
        return DesktopStateImpl.Companion.inDesktopWindowing(displayId) || MultiTaskingTransitionProvider.isMovingBackFromRemovingDesktopDisplay(multiTaskingTransitionState.mChange);
    }

    @Override // com.android.wm.shell.transition.AnimationLoader
    public final void loadAnimationIfPossible() {
        Animation animationLoadAnimationFromResources;
        MultiTaskingTransitionState multiTaskingTransitionState = this.mState;
        if (MultiTaskingTransitionProvider.isMovingBackFromRemovingDesktopDisplay(multiTaskingTransitionState.mChange)) {
            multiTaskingTransitionState.setAnimation(AnimationLoader.NO_ANIMATION);
            return;
        }
        boolean z = multiTaskingTransitionState.mIsEnter;
        int displayId = MultiTaskingTransitions.getDisplayId(multiTaskingTransitionState.mChange);
        DesktopStateImpl.Companion.getClass();
        int i = (DesktopStateImpl.Companion.inDesktopWindowing(displayId) && multiTaskingTransitionState.mTransitionType == 1020) ? z ? R.anim.freeform_close_enter : R.anim.freeform_close_exit : -1;
        if (i == -1 || (animationLoadAnimationFromResources = multiTaskingTransitionState.loadAnimationFromResources(i)) == null) {
            return;
        }
        if (((multiTaskingTransitionState.isOpeningTransitionType() && z) || (multiTaskingTransitionState.isClosingTransitionType() && !z)) && (animationLoadAnimationFromResources instanceof AnimationSet)) {
            addRoundedClipAnimation(multiTaskingTransitionState.getBounds(), (AnimationSet) animationLoadAnimationFromResources);
        }
        multiTaskingTransitionState.setAnimation(animationLoadAnimationFromResources);
    }

    public final String toString() {
        return "DesktopAnimationLoader";
    }
}
