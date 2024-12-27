package com.android.systemui.statusbar.notification.icon.ui.viewbinder;

import com.android.systemui.common.ui.ConfigurationState;
import com.android.systemui.statusbar.notification.icon.ui.viewmodel.NotificationIconContainerStatusBarViewModel;
import com.android.systemui.statusbar.ui.SystemBarUtilsState;

public final class NotificationIconContainerStatusBarViewBinder {
    public final ConfigurationState configuration;
    public final StatusBarIconViewBindingFailureTracker failureTracker;
    public final SystemBarUtilsState systemBarUtilsState;
    public final NotificationIconContainerStatusBarViewModel viewModel;
    public final StatusBarNotificationIconViewStore viewStore;

    public NotificationIconContainerStatusBarViewBinder(NotificationIconContainerStatusBarViewModel notificationIconContainerStatusBarViewModel, ConfigurationState configurationState, SystemBarUtilsState systemBarUtilsState, StatusBarIconViewBindingFailureTracker statusBarIconViewBindingFailureTracker, StatusBarNotificationIconViewStore statusBarNotificationIconViewStore) {
    }
}
