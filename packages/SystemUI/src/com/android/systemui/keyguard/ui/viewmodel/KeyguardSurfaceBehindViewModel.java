package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.domain.interactor.KeyguardSurfaceBehindInteractor;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class KeyguardSurfaceBehindViewModel {
    public final Flow surfaceBehindViewParams;

    public KeyguardSurfaceBehindViewModel(KeyguardSurfaceBehindInteractor keyguardSurfaceBehindInteractor) {
        this.surfaceBehindViewParams = keyguardSurfaceBehindInteractor.viewParams;
    }
}
