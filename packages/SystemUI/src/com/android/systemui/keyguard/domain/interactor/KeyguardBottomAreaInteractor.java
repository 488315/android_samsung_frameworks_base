package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.common.shared.model.Position;
import com.android.systemui.keyguard.data.repository.KeyguardRepository;
import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

public final class KeyguardBottomAreaInteractor {
    public final StateFlowImpl _clockPosition;
    public final StateFlow alpha;
    public final StateFlow animateDozingTransitions;
    public final ReadonlyStateFlow clockPosition;
    public final KeyguardRepository repository;

    public KeyguardBottomAreaInteractor(KeyguardRepository keyguardRepository) {
        this.repository = keyguardRepository;
        KeyguardRepositoryImpl keyguardRepositoryImpl = (KeyguardRepositoryImpl) keyguardRepository;
        this.animateDozingTransitions = keyguardRepositoryImpl.animateBottomAreaDozingTransitions;
        this.alpha = keyguardRepositoryImpl.bottomAreaAlpha;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(new Position(0, 0));
        this._clockPosition = MutableStateFlow;
        this.clockPosition = FlowKt.asStateFlow(MutableStateFlow);
    }
}
