package com.android.systemui.statusbar.pipeline.airplane.data.repository;

import android.net.ConnectivityManager;
import android.os.Handler;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.log.table.DiffableKt;
import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.util.settings.GlobalSettings;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class AirplaneModeRepositoryImpl implements AirplaneModeRepository {
    public final CoroutineContext backgroundContext;
    public final Handler bgHandler;
    public final ConnectivityManager connectivityManager;
    public final GlobalSettings globalSettings;
    public final ReadonlyStateFlow isAirplaneMode;

    public AirplaneModeRepositoryImpl(ConnectivityManager connectivityManager, Handler handler, CoroutineContext coroutineContext, GlobalSettings globalSettings, TableLogBuffer tableLogBuffer, CoroutineScope coroutineScope) {
        this.connectivityManager = connectivityManager;
        this.bgHandler = handler;
        this.backgroundContext = coroutineContext;
        this.globalSettings = globalSettings;
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        AirplaneModeRepositoryImpl$isAirplaneMode$1 airplaneModeRepositoryImpl$isAirplaneMode$1 = new AirplaneModeRepositoryImpl$isAirplaneMode$1(this, null);
        conflatedCallbackFlow.getClass();
        this.isAirplaneMode = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.distinctUntilChanged(FlowConflatedKt.conflatedCallbackFlow(airplaneModeRepositoryImpl$isAirplaneMode$1)), tableLogBuffer, "", "isAirplaneMode", false), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), Boolean.FALSE);
    }

    public final Object setIsAirplaneMode(boolean z, Continuation continuation) {
        Object withContext = BuildersKt.withContext(this.backgroundContext, new AirplaneModeRepositoryImpl$setIsAirplaneMode$2(this, z, null), continuation);
        return withContext == CoroutineSingletons.COROUTINE_SUSPENDED ? withContext : Unit.INSTANCE;
    }
}
