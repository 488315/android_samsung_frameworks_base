package com.android.systemui.blur.data.repository;

import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SecCapturedBlurRepositoryImpl implements SecCapturedBlurRepository {
    public final StateFlowImpl _fullScreenBlurShowing;
    public final StateFlowImpl _mirrorShowing;
    public final ReadonlyStateFlow fullScreenBlurShowing;
    public final ReadonlyStateFlow mirrorShowing;
    public final SharedFlowImpl requestCaptureBlur;

    public SecCapturedBlurRepositoryImpl() {
        Boolean bool = Boolean.FALSE;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(bool);
        this._fullScreenBlurShowing = MutableStateFlow;
        this.fullScreenBlurShowing = FlowKt.asStateFlow(MutableStateFlow);
        StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow(bool);
        this._mirrorShowing = MutableStateFlow2;
        this.mirrorShowing = FlowKt.asStateFlow(MutableStateFlow2);
        this.requestCaptureBlur = SharedFlowKt.MutableSharedFlow$default(1, 0, BufferOverflow.DROP_OLDEST, 2);
    }
}
