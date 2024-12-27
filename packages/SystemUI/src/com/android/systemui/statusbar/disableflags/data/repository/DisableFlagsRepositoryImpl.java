package com.android.systemui.statusbar.disableflags.data.repository;

import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.disableflags.DisableFlagsLogger;
import com.android.systemui.statusbar.disableflags.data.model.DisableFlagsModel;
import com.android.systemui.statusbar.policy.RemoteInputQuickSettingsDisabler;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class DisableFlagsRepositoryImpl implements DisableFlagsRepository {
    public final ReadonlyStateFlow disableFlags;
    public final DisableFlagsLogger disableFlagsLogger;
    public final LogBuffer logBuffer;
    public final int thisDisplayId;

    public DisableFlagsRepositoryImpl(CommandQueue commandQueue, int i, CoroutineScope coroutineScope, RemoteInputQuickSettingsDisabler remoteInputQuickSettingsDisabler, LogBuffer logBuffer, DisableFlagsLogger disableFlagsLogger) {
        this.thisDisplayId = i;
        this.logBuffer = logBuffer;
        this.disableFlagsLogger = disableFlagsLogger;
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        DisableFlagsRepositoryImpl$disableFlags$1 disableFlagsRepositoryImpl$disableFlags$1 = new DisableFlagsRepositoryImpl$disableFlags$1(commandQueue, this, remoteInputQuickSettingsDisabler, null);
        conflatedCallbackFlow.getClass();
        FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.distinctUntilChanged(FlowConflatedKt.conflatedCallbackFlow(disableFlagsRepositoryImpl$disableFlags$1)), new DisableFlagsRepositoryImpl$disableFlags$2(this, null));
        SharingStarted.Companion.getClass();
        this.disableFlags = FlowKt.stateIn(flowKt__TransformKt$onEach$$inlined$unsafeTransform$1, coroutineScope, SharingStarted.Companion.Eagerly, new DisableFlagsModel(0, 0, 3, null));
    }
}
