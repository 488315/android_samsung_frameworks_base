package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.domain.interactor.KeyguardSurfaceBehindInteractor;
import kotlinx.coroutines.flow.Flow;

/* loaded from: classes2.dex */
public final class KeyguardSurfaceBehindViewModel {
    public final Flow surfaceBehindViewParams;

    public KeyguardSurfaceBehindViewModel(KeyguardSurfaceBehindInteractor keyguardSurfaceBehindInteractor) {
        this.surfaceBehindViewParams = keyguardSurfaceBehindInteractor.viewParams;
    }
}
