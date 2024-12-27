package com.android.systemui.communal.ui.viewmodel;

import com.android.systemui.communal.domain.interactor.CommunalTutorialInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardBottomAreaInteractor;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class CommunalTutorialIndicatorViewModel {
    public final Flow alpha;
    public final CommunalTutorialInteractor communalTutorialInteractor;

    public CommunalTutorialIndicatorViewModel(CommunalTutorialInteractor communalTutorialInteractor, KeyguardBottomAreaInteractor keyguardBottomAreaInteractor) {
        this.communalTutorialInteractor = communalTutorialInteractor;
        this.alpha = FlowKt.distinctUntilChanged(keyguardBottomAreaInteractor.alpha);
    }
}
