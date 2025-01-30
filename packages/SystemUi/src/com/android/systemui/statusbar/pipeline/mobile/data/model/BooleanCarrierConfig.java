package com.android.systemui.statusbar.pipeline.mobile.data.model;

import android.os.PersistableBundle;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
        return this.key + "=" + this.config.getValue();
    }
}
