package com.android.systemui.statusbar.data.repository;

import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes3.dex */
public final class NotificationListenerSettingsRepository {
    public final StateFlowImpl showSilentStatusIcons = StateFlowKt.MutableStateFlow(Boolean.TRUE);
}
