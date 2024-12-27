package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.domain.interactor.LightRevealScrimInteractor;
import com.android.systemui.keyguard.domain.interactor.LightRevealScrimInteractor$special$$inlined$filter$1;
import kotlinx.coroutines.flow.Flow;

public final class LightRevealScrimViewModel {
    public final Flow lightRevealEffect;
    public final LightRevealScrimInteractor$special$$inlined$filter$1 revealAmount;

    public LightRevealScrimViewModel(LightRevealScrimInteractor lightRevealScrimInteractor) {
        Flow flow = lightRevealScrimInteractor.lightRevealEffect;
        LightRevealScrimInteractor$special$$inlined$filter$1 lightRevealScrimInteractor$special$$inlined$filter$1 = lightRevealScrimInteractor.revealAmount;
    }
}
