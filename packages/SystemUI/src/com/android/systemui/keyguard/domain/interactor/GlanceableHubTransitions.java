package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.communal.domain.interactor.CommunalInteractor;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository;

public final class GlanceableHubTransitions {
    public final CommunalInteractor communalInteractor;
    public final KeyguardTransitionInteractor transitionInteractor;
    public final KeyguardTransitionRepository transitionRepository;

    public GlanceableHubTransitions(KeyguardTransitionInteractor keyguardTransitionInteractor, KeyguardTransitionRepository keyguardTransitionRepository, CommunalInteractor communalInteractor) {
    }
}
