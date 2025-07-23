package com.android.systemui.statusbar.notification.stack.data.repository;

import com.android.systemui.notifications.ui.composable.NotificationsKt$NotificationScrollingStack$6$1$1;
import com.android.systemui.statusbar.notification.stack.shared.model.ShadeScrollState;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class NotificationPlaceholderRepository {
    public final NotificationsKt$NotificationScrollingStack$6$1$1 accessibilityScrollEventConsumer;
    public final StateFlowImpl alphaForBrightnessMirror = StateFlowKt.MutableStateFlow(Float.valueOf(1.0f));
    public final StateFlowImpl alphaForLockscreenFadeIn = StateFlowKt.MutableStateFlow(Float.valueOf(0.0f));
    public final StateFlowImpl notificationShadeScrimBounds = StateFlowKt.MutableStateFlow(null);
    public final StateFlowImpl constrainedAvailableSpace = StateFlowKt.MutableStateFlow(0);
    public final StateFlowImpl shadeScrollState = StateFlowKt.MutableStateFlow(new ShadeScrollState(false, 0, 0, 7, null));
}
