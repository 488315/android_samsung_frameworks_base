package com.android.systemui.statusbar.data.repository;

import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

public final class NotificationListenerSettingsRepository {
    public final StateFlowImpl showSilentStatusIcons = StateFlowKt.MutableStateFlow(Boolean.TRUE);
}
