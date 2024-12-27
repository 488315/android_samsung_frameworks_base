package com.android.systemui.keyguard.data.repository;

import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class KeyguardSurfaceBehindRepositoryImpl implements KeyguardSurfaceBehindRepository {
    public final StateFlowImpl _isAnimatingSurface;
    public final StateFlowImpl _isSurfaceRemoteAnimationTargetAvailable;
    public final ReadonlyStateFlow isAnimatingSurface;
    public final ReadonlyStateFlow isSurfaceRemoteAnimationTargetAvailable;

    public KeyguardSurfaceBehindRepositoryImpl() {
        Boolean bool = Boolean.FALSE;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(bool);
        this._isAnimatingSurface = MutableStateFlow;
        this.isAnimatingSurface = FlowKt.asStateFlow(MutableStateFlow);
        this.isSurfaceRemoteAnimationTargetAvailable = FlowKt.asStateFlow(StateFlowKt.MutableStateFlow(bool));
    }
}
