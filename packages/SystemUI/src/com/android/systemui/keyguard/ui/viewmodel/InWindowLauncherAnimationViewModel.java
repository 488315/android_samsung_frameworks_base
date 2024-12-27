package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.domain.interactor.InWindowLauncherUnlockAnimationInteractor;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

public final class InWindowLauncherAnimationViewModel {
    public final ReadonlyStateFlow shouldPrepareForInWindowAnimation;
    public final ReadonlyStateFlow shouldStartInWindowAnimation;

    public InWindowLauncherAnimationViewModel(InWindowLauncherUnlockAnimationInteractor inWindowLauncherUnlockAnimationInteractor) {
        this.shouldPrepareForInWindowAnimation = inWindowLauncherUnlockAnimationInteractor.transitioningToGoneWithInWindowAnimation;
        this.shouldStartInWindowAnimation = inWindowLauncherUnlockAnimationInteractor.shouldStartInWindowAnimation;
    }
}
