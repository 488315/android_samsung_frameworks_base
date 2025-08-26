package com.android.systemui.statusbar.window.data.repository;

import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.window.shared.model.StatusBarWindowState;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* loaded from: classes3.dex */
public final class StatusBarWindowStatePerDisplayRepositoryImpl implements StatusBarWindowStatePerDisplayRepository {
    public final CommandQueue commandQueue;
    public final int thisDisplayId;
    public final ReadonlyStateFlow windowState;

    public StatusBarWindowStatePerDisplayRepositoryImpl(int i, CommandQueue commandQueue, CoroutineScope coroutineScope) {
        this.thisDisplayId = i;
        this.commandQueue = commandQueue;
        Flow flowConflatedCallbackFlow = FlowConflatedKt.conflatedCallbackFlow(new StatusBarWindowStatePerDisplayRepositoryImpl$windowState$1(this, null));
        SharingStarted.Companion.getClass();
        this.windowState = FlowKt.stateIn(flowConflatedCallbackFlow, coroutineScope, SharingStarted.Companion.Eagerly, StatusBarWindowState.Hidden);
    }
}
