package com.android.systemui.statusbar.pipeline.mobile.data.model;

import android.os.PersistableBundle;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class BooleanCarrierConfig {
    public final StateFlowImpl _configValue;
    public final ReadonlyStateFlow config;
    public final String key;

    public BooleanCarrierConfig(String str, PersistableBundle persistableBundle) {
        this.key = str;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(Boolean.valueOf(persistableBundle.getBoolean(str)));
        this._configValue = MutableStateFlow;
        this.config = FlowKt.asStateFlow(MutableStateFlow);
    }

    public final String toString() {
        return this.key + "=" + this.config.$$delegate_0.getValue();
    }
}
