package com.android.systemui.statusbar.notification.icon.ui.viewbinder;

import com.android.app.displaylib.PerDisplayRepository;
import com.android.systemui.common.ui.ConfigurationState;
import com.android.systemui.statusbar.notification.icon.ui.viewbinder.ConnectedDisplaysStatusBarNotificationIconViewStore;
import com.android.systemui.statusbar.notification.icon.ui.viewmodel.NotificationIconContainerStatusBarViewModel;
import com.android.systemui.statusbar.ui.SystemBarUtilsState;

/* loaded from: classes3.dex */
public final class NotificationIconContainerStatusBarViewBinder {
    public final PerDisplayRepository configurationStateRepository;
    public final ConnectedDisplaysStatusBarNotificationIconViewStore.Factory connectedDisplaysViewStoreFactory;
    public final ConfigurationState defaultConfigurationState;
    public final StatusBarNotificationIconViewStore defaultDisplayViewStore;
    public final StatusBarIconViewBindingFailureTracker failureTracker;
    public final SystemBarUtilsState systemBarUtilsState;
    public final NotificationIconContainerStatusBarViewModel viewModel;

    public NotificationIconContainerStatusBarViewBinder(NotificationIconContainerStatusBarViewModel notificationIconContainerStatusBarViewModel, PerDisplayRepository perDisplayRepository, ConfigurationState configurationState, SystemBarUtilsState systemBarUtilsState, StatusBarIconViewBindingFailureTracker statusBarIconViewBindingFailureTracker, StatusBarNotificationIconViewStore statusBarNotificationIconViewStore, ConnectedDisplaysStatusBarNotificationIconViewStore.Factory factory) {
        this.viewModel = notificationIconContainerStatusBarViewModel;
        this.configurationStateRepository = perDisplayRepository;
        this.defaultConfigurationState = configurationState;
        this.systemBarUtilsState = systemBarUtilsState;
        this.failureTracker = statusBarIconViewBindingFailureTracker;
        this.defaultDisplayViewStore = statusBarNotificationIconViewStore;
        this.connectedDisplaysViewStoreFactory = factory;
    }
}
