package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.domain.interactor.WindowManagerLockscreenVisibilityInteractor;
import kotlinx.coroutines.flow.Flow;

/* loaded from: classes2.dex */
public final class WindowManagerLockscreenVisibilityViewModel {
    public final Flow aodVisibility;
    public final Flow lockscreenVisibility;
    public final Flow surfaceBehindAnimating;
    public final Flow surfaceBehindVisibility;

    public WindowManagerLockscreenVisibilityViewModel(WindowManagerLockscreenVisibilityInteractor windowManagerLockscreenVisibilityInteractor) {
        this.surfaceBehindVisibility = windowManagerLockscreenVisibilityInteractor.surfaceBehindVisibility;
        this.surfaceBehindAnimating = windowManagerLockscreenVisibilityInteractor.usingKeyguardGoingAwayAnimation;
        this.lockscreenVisibility = windowManagerLockscreenVisibilityInteractor.lockscreenVisibility;
        this.aodVisibility = windowManagerLockscreenVisibilityInteractor.aodVisibility;
    }
}
