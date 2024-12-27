package com.android.systemui.keyguard.data.repository;

import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

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
