package com.android.systemui.statusbar.data.repository;

import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class RemoteInputRepositoryImpl implements RemoteInputRepository {
    public final Flow isRemoteInputActive;
    public final NotificationRemoteInputManager notificationRemoteInputManager;

    public RemoteInputRepositoryImpl(NotificationRemoteInputManager notificationRemoteInputManager) {
        this.notificationRemoteInputManager = notificationRemoteInputManager;
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        RemoteInputRepositoryImpl$isRemoteInputActive$1 remoteInputRepositoryImpl$isRemoteInputActive$1 = new RemoteInputRepositoryImpl$isRemoteInputActive$1(this, null);
        conflatedCallbackFlow.getClass();
        this.isRemoteInputActive = FlowConflatedKt.conflatedCallbackFlow(remoteInputRepositoryImpl$isRemoteInputActive$1);
    }
}
