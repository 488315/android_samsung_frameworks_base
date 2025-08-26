package com.android.systemui.keyguard.data.repository;

import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes2.dex */
public final class KeyguardSurfaceBehindRepositoryImpl implements KeyguardSurfaceBehindRepository {
    public final StateFlowImpl _isAnimatingSurface;
    public final StateFlowImpl _isSurfaceRemoteAnimationTargetAvailable;
    public final ReadonlyStateFlow isAnimatingSurface;
    public final ReadonlyStateFlow isSurfaceRemoteAnimationTargetAvailable;

    public KeyguardSurfaceBehindRepositoryImpl() {
        Boolean bool = Boolean.FALSE;
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(bool);
        this._isAnimatingSurface = stateFlowImplMutableStateFlow;
        this.isAnimatingSurface = FlowKt.asStateFlow(stateFlowImplMutableStateFlow);
        StateFlowImpl stateFlowImplMutableStateFlow2 = StateFlowKt.MutableStateFlow(bool);
        this._isSurfaceRemoteAnimationTargetAvailable = stateFlowImplMutableStateFlow2;
        this.isSurfaceRemoteAnimationTargetAvailable = FlowKt.asStateFlow(stateFlowImplMutableStateFlow2);
    }
}
