package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.domain.interactor.WindowManagerLockscreenVisibilityInteractor;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class WindowManagerLockscreenVisibilityViewModel {
    public final Flow aodVisibility;
    public final Flow lockscreenVisibility;
    public final Flow surfaceBehindAnimating;
    public final Flow surfaceBehindVisibility;

    public WindowManagerLockscreenVisibilityViewModel(WindowManagerLockscreenVisibilityInteractor windowManagerLockscreenVisibilityInteractor) {
        Flow flow = windowManagerLockscreenVisibilityInteractor.surfaceBehindVisibility;
        Flow flow2 = windowManagerLockscreenVisibilityInteractor.usingKeyguardGoingAwayAnimation;
        Flow flow3 = windowManagerLockscreenVisibilityInteractor.lockscreenVisibility;
        Flow flow4 = windowManagerLockscreenVisibilityInteractor.aodVisibility;
    }
}
