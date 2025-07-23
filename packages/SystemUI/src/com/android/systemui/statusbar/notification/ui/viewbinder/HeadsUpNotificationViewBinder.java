package com.android.systemui.statusbar.notification.ui.viewbinder;

import com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel;
import com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationListViewModel;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class HeadsUpNotificationViewBinder {
    public final OngoingActivityChipsViewModel ongoingActivityChipsViewModel;
    public final NotificationListViewModel viewModel;

    public HeadsUpNotificationViewBinder(NotificationListViewModel notificationListViewModel, OngoingActivityChipsViewModel ongoingActivityChipsViewModel) {
        this.viewModel = notificationListViewModel;
        this.ongoingActivityChipsViewModel = ongoingActivityChipsViewModel;
    }
}
