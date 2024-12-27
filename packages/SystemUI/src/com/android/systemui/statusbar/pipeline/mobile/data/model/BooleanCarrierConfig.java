package com.android.systemui.statusbar.pipeline.mobile.data.model;

import android.os.PersistableBundle;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
