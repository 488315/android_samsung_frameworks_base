package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.domain.interactor.KeyguardSurfaceBehindInteractor;
import kotlinx.coroutines.flow.Flow;

public final class KeyguardSurfaceBehindViewModel {
    public final Flow surfaceBehindViewParams;

    public KeyguardSurfaceBehindViewModel(KeyguardSurfaceBehindInteractor keyguardSurfaceBehindInteractor) {
        Flow flow = keyguardSurfaceBehindInteractor.viewParams;
    }
}
