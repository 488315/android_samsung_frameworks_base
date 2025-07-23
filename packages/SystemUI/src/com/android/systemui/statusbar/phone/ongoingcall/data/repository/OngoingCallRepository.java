package com.android.systemui.statusbar.phone.ongoingcall.data.repository;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.statusbar.phone.ongoingcall.shared.model.OngoingCallModel;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class OngoingCallRepository {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final StateFlowImpl _ongoingCallState;
    public final LogBuffer logger;
    public final ReadonlyStateFlow ongoingCallState;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(OngoingCallModel.NoCall.INSTANCE);
        this._ongoingCallState = MutableStateFlow;
        this.ongoingCallState = FlowKt.asStateFlow(MutableStateFlow);
    }
}
