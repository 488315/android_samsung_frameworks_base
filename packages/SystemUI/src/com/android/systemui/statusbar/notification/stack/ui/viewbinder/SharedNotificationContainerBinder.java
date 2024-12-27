package com.android.systemui.statusbar.notification.stack.ui.viewbinder;

import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.notification.stack.NotificationStackSizeCalculator;
import kotlinx.coroutines.CoroutineDispatcher;

public final class SharedNotificationContainerBinder {
    public final NotificationStackScrollLayoutController controller;
    public final NotificationStackSizeCalculator notificationStackSizeCalculator;

    public SharedNotificationContainerBinder(NotificationStackScrollLayoutController notificationStackScrollLayoutController, NotificationStackSizeCalculator notificationStackSizeCalculator, NotificationScrollViewBinder notificationScrollViewBinder, CoroutineDispatcher coroutineDispatcher) {
    }
}
