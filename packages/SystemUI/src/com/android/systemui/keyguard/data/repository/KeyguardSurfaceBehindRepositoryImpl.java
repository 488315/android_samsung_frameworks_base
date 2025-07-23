package com.android.systemui.keyguard.data.repository;

import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
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
        StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow(bool);
        this._isSurfaceRemoteAnimationTargetAvailable = MutableStateFlow2;
        this.isSurfaceRemoteAnimationTargetAvailable = FlowKt.asStateFlow(MutableStateFlow2);
    }
}
