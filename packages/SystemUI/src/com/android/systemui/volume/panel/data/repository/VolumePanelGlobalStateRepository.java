package com.android.systemui.volume.panel.data.repository;

import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.volume.panel.shared.VolumePanelLogger;
import com.android.systemui.volume.panel.shared.model.VolumePanelGlobalState;
import java.io.PrintWriter;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class VolumePanelGlobalStateRepository implements Dumpable {
    public final ReadonlyStateFlow globalState;
    public final VolumePanelLogger logger;
    public final StateFlowImpl mutableGlobalState;

    public VolumePanelGlobalStateRepository(DumpManager dumpManager, VolumePanelLogger volumePanelLogger) {
        this.logger = volumePanelLogger;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(new VolumePanelGlobalState(false));
        this.mutableGlobalState = MutableStateFlow;
        this.globalState = FlowKt.asStateFlow(MutableStateFlow);
        dumpManager.registerNormalDumpable("VolumePanelGlobalStateRepository", this);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "isVisible: ", ((VolumePanelGlobalState) this.globalState.$$delegate_0.getValue()).isVisible);
    }
}
