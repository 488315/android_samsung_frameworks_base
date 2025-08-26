package com.android.systemui.blur.data.repository;

import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes.dex */
public final class SecCapturedBlurRepositoryImpl implements SecCapturedBlurRepository {
    public final StateFlowImpl _fullScreenBlurShowing;
    public final StateFlowImpl _mirrorShowing;
    public final ReadonlyStateFlow fullScreenBlurShowing;
    public final ReadonlyStateFlow mirrorShowing;
    public final SharedFlowImpl requestCaptureBlur;

    public SecCapturedBlurRepositoryImpl() {
        Boolean bool = Boolean.FALSE;
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(bool);
        this._fullScreenBlurShowing = stateFlowImplMutableStateFlow;
        this.fullScreenBlurShowing = FlowKt.asStateFlow(stateFlowImplMutableStateFlow);
        StateFlowImpl stateFlowImplMutableStateFlow2 = StateFlowKt.MutableStateFlow(bool);
        this._mirrorShowing = stateFlowImplMutableStateFlow2;
        this.mirrorShowing = FlowKt.asStateFlow(stateFlowImplMutableStateFlow2);
        this.requestCaptureBlur = SharedFlowKt.MutableSharedFlow$default(1, 0, BufferOverflow.DROP_OLDEST, 2);
    }
}
