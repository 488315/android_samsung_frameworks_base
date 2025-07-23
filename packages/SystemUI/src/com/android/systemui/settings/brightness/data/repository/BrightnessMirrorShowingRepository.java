package com.android.systemui.settings.brightness.data.repository;

import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class BrightnessMirrorShowingRepository {
    public final StateFlowImpl _isShowing;
    public final ReadonlyStateFlow isShowing;

    public BrightnessMirrorShowingRepository() {
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(Boolean.FALSE);
        this._isShowing = MutableStateFlow;
        this.isShowing = FlowKt.asStateFlow(MutableStateFlow);
    }
}
