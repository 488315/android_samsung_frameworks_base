package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.common.shared.model.Position;
import com.android.systemui.keyguard.data.repository.KeyguardRepository;
import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
