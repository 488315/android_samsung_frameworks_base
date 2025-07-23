package com.android.systemui.keyguard.data.repository;

import com.android.systemui.dump.DumpManager;
import com.android.systemui.util.kotlin.FlowDumperImpl;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class PulseExpansionRepository extends FlowDumperImpl {
    public final MutableStateFlow isPulseExpanding;

    public PulseExpansionRepository(DumpManager dumpManager) {
        super(dumpManager, null, 2, null);
        this.isPulseExpanding = (MutableStateFlow) dumpValue(StateFlowKt.MutableStateFlow(Boolean.FALSE), "pulseExpanding");
    }
}
