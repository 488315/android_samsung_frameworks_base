package com.android.systemui.statusbar.notification.data.repository;

import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes3.dex */
public final class NotificationsKeyguardViewStateRepository {
    public final StateFlowImpl areNotificationsFullyHidden = StateFlowKt.MutableStateFlow(Boolean.FALSE);
}
