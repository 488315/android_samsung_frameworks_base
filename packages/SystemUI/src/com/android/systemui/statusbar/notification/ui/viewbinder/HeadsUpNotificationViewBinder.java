package com.android.systemui.statusbar.notification.ui.viewbinder;

import com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel;
import com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationListViewModel;

/* loaded from: classes3.dex */
public final class HeadsUpNotificationViewBinder {
    public final OngoingActivityChipsViewModel ongoingActivityChipsViewModel;
    public final NotificationListViewModel viewModel;

    public HeadsUpNotificationViewBinder(NotificationListViewModel notificationListViewModel, OngoingActivityChipsViewModel ongoingActivityChipsViewModel) {
        this.viewModel = notificationListViewModel;
        this.ongoingActivityChipsViewModel = ongoingActivityChipsViewModel;
    }
}
