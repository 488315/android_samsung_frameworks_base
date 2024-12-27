package com.android.systemui.statusbar.policy.data.repository;

import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.statusbar.policy.ZenModeController;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ZenModeRepositoryImpl implements ZenModeRepository {
    public final Flow consolidatedNotificationPolicy;
    public final Flow zenMode;
    public final ZenModeController zenModeController;

    public ZenModeRepositoryImpl(ZenModeController zenModeController) {
        this.zenModeController = zenModeController;
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        ZenModeRepositoryImpl$zenMode$1 zenModeRepositoryImpl$zenMode$1 = new ZenModeRepositoryImpl$zenMode$1(this, null);
        conflatedCallbackFlow.getClass();
        this.zenMode = FlowConflatedKt.conflatedCallbackFlow(zenModeRepositoryImpl$zenMode$1);
        ZenModeRepositoryImpl$consolidatedNotificationPolicy$1 zenModeRepositoryImpl$consolidatedNotificationPolicy$1 = new ZenModeRepositoryImpl$consolidatedNotificationPolicy$1(this, null);
        conflatedCallbackFlow.getClass();
        this.consolidatedNotificationPolicy = FlowConflatedKt.conflatedCallbackFlow(zenModeRepositoryImpl$consolidatedNotificationPolicy$1);
    }
}
