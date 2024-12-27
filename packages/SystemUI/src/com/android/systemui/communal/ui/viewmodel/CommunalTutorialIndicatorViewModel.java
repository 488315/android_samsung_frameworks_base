package com.android.systemui.communal.ui.viewmodel;

import com.android.systemui.communal.domain.interactor.CommunalTutorialInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardBottomAreaInteractor;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

public final class CommunalTutorialIndicatorViewModel {
    public final Flow alpha;
    public final CommunalTutorialInteractor communalTutorialInteractor;

    public CommunalTutorialIndicatorViewModel(CommunalTutorialInteractor communalTutorialInteractor, KeyguardBottomAreaInteractor keyguardBottomAreaInteractor) {
        this.communalTutorialInteractor = communalTutorialInteractor;
        this.alpha = FlowKt.distinctUntilChanged(keyguardBottomAreaInteractor.alpha);
    }
}
