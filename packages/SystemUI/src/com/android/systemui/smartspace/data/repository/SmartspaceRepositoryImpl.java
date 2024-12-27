package com.android.systemui.smartspace.data.repository;

import android.app.smartspace.SmartspaceTarget;
import com.android.systemui.communal.smartspace.CommunalSmartspaceController;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SmartspaceRepositoryImpl implements SmartspaceRepository, BcSmartspaceDataPlugin.SmartspaceTargetListener {
    public final StateFlowImpl _communalSmartspaceTargets;
    public final CommunalSmartspaceController communalSmartspaceController;
    public final FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1 communalSmartspaceTargets;
    public final Executor uiExecutor;

    public SmartspaceRepositoryImpl(CommunalSmartspaceController communalSmartspaceController, Executor executor) {
        this.communalSmartspaceController = communalSmartspaceController;
        this.uiExecutor = executor;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(EmptyList.INSTANCE);
        this._communalSmartspaceTargets = MutableStateFlow;
        this.communalSmartspaceTargets = new FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new SmartspaceRepositoryImpl$communalSmartspaceTargets$1(this, null), MutableStateFlow), new SmartspaceRepositoryImpl$communalSmartspaceTargets$2(this, null));
    }

    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceTargetListener
    public final void onSmartspaceTargetsUpdated(List list) {
        Unit unit = null;
        StateFlowImpl stateFlowImpl = this._communalSmartspaceTargets;
        if (list != null) {
            ArrayList arrayList = new ArrayList();
            for (Object obj : list) {
                if (obj instanceof SmartspaceTarget) {
                    arrayList.add(obj);
                }
            }
            stateFlowImpl.updateState(null, arrayList);
            unit = Unit.INSTANCE;
        }
        if (unit == null) {
            stateFlowImpl.setValue(EmptyList.INSTANCE);
        }
    }
}
