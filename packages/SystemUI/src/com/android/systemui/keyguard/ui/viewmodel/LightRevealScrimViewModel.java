package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.domain.interactor.LightRevealScrimInteractor;
import com.android.systemui.keyguard.domain.interactor.LightRevealScrimInteractor$special$$inlined$filter$1;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class LightRevealScrimViewModel {
    public final Flow lightRevealEffect;
    public final LightRevealScrimInteractor$special$$inlined$filter$1 revealAmount;

    public LightRevealScrimViewModel(LightRevealScrimInteractor lightRevealScrimInteractor) {
        Flow flow = lightRevealScrimInteractor.lightRevealEffect;
        LightRevealScrimInteractor$special$$inlined$filter$1 lightRevealScrimInteractor$special$$inlined$filter$1 = lightRevealScrimInteractor.revealAmount;
    }
}
