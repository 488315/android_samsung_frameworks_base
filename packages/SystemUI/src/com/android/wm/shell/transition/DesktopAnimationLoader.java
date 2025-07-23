package com.android.wm.shell.transition;

import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.window.TransitionInfo;
import com.android.systemui.R;
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class DesktopAnimationLoader extends AnimationLoader {
    public DesktopAnimationLoader(MultiTaskingTransitionState multiTaskingTransitionState) {
        super(multiTaskingTransitionState);
    }

    @Override // com.android.wm.shell.transition.AnimationLoader
    public final boolean isAvailable() {
        MultiTaskingTransitionState multiTaskingTransitionState = this.mState;
        TransitionInfo.Change change = multiTaskingTransitionState.mChange;
        int endDisplayId = change.getTaskInfo() != null ? change.getTaskInfo().displayId : change.getEndDisplayId();
        DesktopStateImpl.Companion.getClass();
        return DesktopStateImpl.Companion.inDesktopWindowing(endDisplayId) || MultiTaskingTransitionProvider.isMovingBackFromRemovingDesktopDisplay(multiTaskingTransitionState.mChange);
    }

    @Override // com.android.wm.shell.transition.AnimationLoader
    public final void loadAnimationIfPossible() {
        Animation loadAnimationFromResources;
        MultiTaskingTransitionState multiTaskingTransitionState = this.mState;
        if (MultiTaskingTransitionProvider.isMovingBackFromRemovingDesktopDisplay(multiTaskingTransitionState.mChange)) {
            multiTaskingTransitionState.setAnimation(AnimationLoader.NO_ANIMATION);
            return;
        }
        boolean z = multiTaskingTransitionState.mIsEnter;
        TransitionInfo.Change change = multiTaskingTransitionState.mChange;
        int endDisplayId = change.getTaskInfo() != null ? change.getTaskInfo().displayId : change.getEndDisplayId();
        DesktopStateImpl.Companion.getClass();
        int i = (DesktopStateImpl.Companion.inDesktopWindowing(endDisplayId) && multiTaskingTransitionState.mTransitionType == 1020) ? z ? R.anim.freeform_close_enter : R.anim.freeform_close_exit : -1;
        if (i == -1 || (loadAnimationFromResources = multiTaskingTransitionState.loadAnimationFromResources(i)) == null) {
            return;
        }
        if (((multiTaskingTransitionState.isOpeningTransitionType() && z) || (multiTaskingTransitionState.isClosingTransitionType() && !z)) && (loadAnimationFromResources instanceof AnimationSet)) {
            addRoundedClipAnimation(multiTaskingTransitionState.getBounds(), (AnimationSet) loadAnimationFromResources);
        }
        multiTaskingTransitionState.setAnimation(loadAnimationFromResources);
    }

    public final String toString() {
        return "DesktopAnimationLoader";
    }
}
