package com.android.systemui.unfold.data.repository;

import android.os.PowerManager;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import java.util.concurrent.Executor;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ScreenTimeoutPolicyRepository {
    public final Executor executor;
    public final PowerManager powerManager;
    public final ReadonlyStateFlow screenTimeoutActive;

    public ScreenTimeoutPolicyRepository(PowerManager powerManager, Executor executor, CoroutineScope coroutineScope) {
        this.powerManager = powerManager;
        this.executor = executor;
        Flow conflatedCallbackFlow = FlowConflatedKt.conflatedCallbackFlow(new ScreenTimeoutPolicyRepository$screenTimeoutActive$1(this, null));
        SharingStarted.Companion.getClass();
        this.screenTimeoutActive = FlowKt.stateIn(conflatedCallbackFlow, coroutineScope, SharingStarted.Companion.Eagerly, Boolean.TRUE);
    }
}
