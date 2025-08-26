package com.android.systemui.statusbar.phone.ongoingcall.data.repository;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.statusbar.phone.ongoingcall.shared.model.OngoingCallModel;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes3.dex */
public final class OngoingCallRepository {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final StateFlowImpl _ongoingCallState;
    public final LogBuffer logger;
    public final ReadonlyStateFlow ongoingCallState;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public OngoingCallRepository(LogBuffer logBuffer) {
        this.logger = logBuffer;
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(OngoingCallModel.NoCall.INSTANCE);
        this._ongoingCallState = stateFlowImplMutableStateFlow;
        this.ongoingCallState = FlowKt.asStateFlow(stateFlowImplMutableStateFlow);
    }
}
