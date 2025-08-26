package com.android.systemui.statusbar.pipeline.mobile.data.model;

import android.os.PersistableBundle;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes3.dex */
public final class BooleanCarrierConfig {
    public final StateFlowImpl _configValue;
    public final ReadonlyStateFlow config;
    public final String key;

    public BooleanCarrierConfig(String str, PersistableBundle persistableBundle) {
        this.key = str;
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(Boolean.valueOf(persistableBundle.getBoolean(str)));
        this._configValue = stateFlowImplMutableStateFlow;
        this.config = FlowKt.asStateFlow(stateFlowImplMutableStateFlow);
    }

    public final String toString() {
        return this.key + "=" + this.config.$$delegate_0.getValue();
    }
}
