package com.android.systemui.statusbar.notification.data.repository;

import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

public final class NotificationsKeyguardViewStateRepository {
    public final StateFlowImpl areNotificationsFullyHidden;
    public final StateFlowImpl isPulseExpanding;

    public NotificationsKeyguardViewStateRepository() {
        Boolean bool = Boolean.FALSE;
        this.areNotificationsFullyHidden = StateFlowKt.MutableStateFlow(bool);
        this.isPulseExpanding = StateFlowKt.MutableStateFlow(bool);
    }
}
