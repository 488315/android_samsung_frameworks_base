package com.android.systemui.volume.panel.data.repository;

import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.volume.panel.shared.model.VolumePanelGlobalState;
import java.io.PrintWriter;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class VolumePanelGlobalStateRepository implements Dumpable {
    public final ReadonlyStateFlow globalState;
    public final StateFlowImpl mutableGlobalState;

    public VolumePanelGlobalStateRepository(DumpManager dumpManager) {
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(new VolumePanelGlobalState(false));
        this.mutableGlobalState = MutableStateFlow;
        this.globalState = FlowKt.asStateFlow(MutableStateFlow);
        dumpManager.registerNormalDumpable("VolumePanelGlobalState", this);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m("isVisible: ", ((VolumePanelGlobalState) this.globalState.$$delegate_0.getValue()).isVisible, printWriter);
    }
}
