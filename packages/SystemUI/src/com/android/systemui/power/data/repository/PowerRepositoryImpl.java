package com.android.systemui.power.data.repository;

import android.content.Context;
import android.os.PowerManager;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.keyguard.UserActivityNotifier;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.power.shared.model.DozeScreenStateModel;
import com.android.systemui.power.shared.model.ScreenPowerState;
import com.android.systemui.power.shared.model.WakefulnessModel;
import com.android.systemui.util.time.SystemClock;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes2.dex */
public final class PowerRepositoryImpl implements PowerRepository {
    public final StateFlowImpl _screenPowerState;
    public final StateFlowImpl _wakefulness;
    public final Context applicationContext;
    public final StateFlowImpl dozeScreenState = StateFlowKt.MutableStateFlow(DozeScreenStateModel.UNKNOWN);
    public final Flow isInteractive;
    public final PowerManager manager;
    public final ReadonlyStateFlow screenPowerState;
    public final SystemClock systemClock;
    public final ReadonlyStateFlow wakefulness;

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

    public PowerRepositoryImpl(PowerManager powerManager, Context context, SystemClock systemClock, BroadcastDispatcher broadcastDispatcher, UserActivityNotifier userActivityNotifier) {
        this.manager = powerManager;
        this.applicationContext = context;
        this.systemClock = systemClock;
        this.isInteractive = FlowConflatedKt.conflatedCallbackFlow(new PowerRepositoryImpl$isInteractive$1(broadcastDispatcher, this, null));
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(new WakefulnessModel(null, null, null, false, 15, null));
        this._wakefulness = stateFlowImplMutableStateFlow;
        this.wakefulness = FlowKt.asStateFlow(stateFlowImplMutableStateFlow);
        StateFlowImpl stateFlowImplMutableStateFlow2 = StateFlowKt.MutableStateFlow(ScreenPowerState.SCREEN_OFF);
        this._screenPowerState = stateFlowImplMutableStateFlow2;
        this.screenPowerState = FlowKt.asStateFlow(stateFlowImplMutableStateFlow2);
    }

    public final void wakeUp(int i, String str) {
        this.manager.wakeUp(this.systemClock.uptimeMillis(), i, AbstractResolvableFuture$$ExternalSyntheticOutline0.m(this.applicationContext.getPackageName(), ":", str));
    }
}
