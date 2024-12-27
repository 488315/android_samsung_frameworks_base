package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.domain.interactor.InWindowLauncherUnlockAnimationInteractor;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class InWindowLauncherAnimationViewModel {
    public final ReadonlyStateFlow shouldPrepareForInWindowAnimation;
    public final ReadonlyStateFlow shouldStartInWindowAnimation;

    public InWindowLauncherAnimationViewModel(InWindowLauncherUnlockAnimationInteractor inWindowLauncherUnlockAnimationInteractor) {
        this.shouldPrepareForInWindowAnimation = inWindowLauncherUnlockAnimationInteractor.transitioningToGoneWithInWindowAnimation;
        this.shouldStartInWindowAnimation = inWindowLauncherUnlockAnimationInteractor.shouldStartInWindowAnimation;
    }
}
