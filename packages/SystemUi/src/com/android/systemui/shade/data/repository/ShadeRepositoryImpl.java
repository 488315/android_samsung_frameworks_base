package com.android.systemui.shade.data.repository;

import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.shade.ShadeExpansionStateManager;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ShadeRepositoryImpl implements ShadeRepository {
    public final StateFlowImpl _qsExpansion;
    public final StateFlowImpl _udfpsTransitionToFullShadeProgress;
    public final Flow shadeModel;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public ShadeRepositoryImpl(ShadeExpansionStateManager shadeExpansionStateManager) {
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        ShadeRepositoryImpl$shadeModel$1 shadeRepositoryImpl$shadeModel$1 = new ShadeRepositoryImpl$shadeModel$1(shadeExpansionStateManager, null);
        conflatedCallbackFlow.getClass();
        this.shadeModel = FlowKt.distinctUntilChanged(ConflatedCallbackFlow.conflatedCallbackFlow(shadeRepositoryImpl$shadeModel$1));
        Float valueOf = Float.valueOf(0.0f);
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(valueOf);
        this._qsExpansion = MutableStateFlow;
        FlowKt.asStateFlow(MutableStateFlow);
        StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow(valueOf);
        this._udfpsTransitionToFullShadeProgress = MutableStateFlow2;
        FlowKt.asStateFlow(MutableStateFlow2);
    }
}
