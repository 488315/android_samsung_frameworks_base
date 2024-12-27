package com.android.systemui.statusbar.notification.data.repository;

import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class NotificationsKeyguardViewStateRepository {
    public final StateFlowImpl areNotificationsFullyHidden;
    public final StateFlowImpl isPulseExpanding;

    public NotificationsKeyguardViewStateRepository() {
        Boolean bool = Boolean.FALSE;
        this.areNotificationsFullyHidden = StateFlowKt.MutableStateFlow(bool);
        this.isPulseExpanding = StateFlowKt.MutableStateFlow(bool);
    }
}
