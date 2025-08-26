package com.android.systemui.statusbar.data.repository;

import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes3.dex */
public final class RemoteInputRepositoryImpl implements RemoteInputRepository {
    public final NotificationRemoteInputManager notificationRemoteInputManager;
    public final Flow isRemoteInputActive = FlowConflatedKt.conflatedCallbackFlow(new RemoteInputRepositoryImpl$_isRemoteInputActive$1(this, null));
    public final StateFlowImpl remoteInputRowBottomBound = StateFlowKt.MutableStateFlow(null);

    public RemoteInputRepositoryImpl(CoroutineScope coroutineScope, NotificationRemoteInputManager notificationRemoteInputManager) {
        this.notificationRemoteInputManager = notificationRemoteInputManager;
    }
}
