package com.android.systemui.keyguard.ui.composable.section;

import android.view.ViewGroup;
import android.view.ViewParent;
import com.android.systemui.common.ui.ConfigurationState;
import com.android.systemui.keyguard.ui.viewmodel.AodBurnInViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardClockViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel;
import com.android.systemui.statusbar.notification.icon.ui.viewbinder.AlwaysOnDisplayNotificationIconViewStore;
import com.android.systemui.statusbar.notification.icon.ui.viewbinder.StatusBarIconViewBindingFailureTracker;
import com.android.systemui.statusbar.notification.icon.ui.viewmodel.NotificationIconContainerAlwaysOnDisplayViewModel;
import com.android.systemui.statusbar.notification.promoted.ui.viewmodel.AODPromotedNotificationViewModel;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.ui.view.SharedNotificationContainer;
import com.android.systemui.statusbar.notification.stack.ui.viewbinder.SharedNotificationContainerBinder;
import com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationsPlaceholderViewModel;
import com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel;
import com.android.systemui.statusbar.ui.SystemBarUtilsState;
import dagger.Lazy;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class NotificationSection {
    public final AodBurnInViewModel aodBurnInViewModel;
    public final AODPromotedNotificationViewModel.Factory aodPromotedNotificationViewModelFactory;
    public final ConfigurationState configurationState;
    public final StatusBarIconViewBindingFailureTracker iconBindingFailureTracker;
    public final KeyguardClockViewModel keyguardClockViewModel;
    public final KeyguardRootViewModel keyguardRootViewModel;
    public final AlwaysOnDisplayNotificationIconViewStore nicAodIconViewStore;
    public final NotificationIconContainerAlwaysOnDisplayViewModel nicAodViewModel;
    public final Lazy stackScrollView;
    public final SystemBarUtilsState systemBarUtilsState;
    public final NotificationsPlaceholderViewModel.Factory viewModelFactory;

    public NotificationSection(Lazy lazy, NotificationsPlaceholderViewModel.Factory factory, AodBurnInViewModel aodBurnInViewModel, SharedNotificationContainer sharedNotificationContainer, SharedNotificationContainerViewModel sharedNotificationContainerViewModel, NotificationStackScrollLayout notificationStackScrollLayout, SharedNotificationContainerBinder sharedNotificationContainerBinder, KeyguardRootViewModel keyguardRootViewModel, ConfigurationState configurationState, StatusBarIconViewBindingFailureTracker statusBarIconViewBindingFailureTracker, NotificationIconContainerAlwaysOnDisplayViewModel notificationIconContainerAlwaysOnDisplayViewModel, AlwaysOnDisplayNotificationIconViewStore alwaysOnDisplayNotificationIconViewStore, AODPromotedNotificationViewModel.Factory factory2, SystemBarUtilsState systemBarUtilsState, KeyguardClockViewModel keyguardClockViewModel) {
        this.stackScrollView = lazy;
        this.viewModelFactory = factory;
        this.aodBurnInViewModel = aodBurnInViewModel;
        this.keyguardRootViewModel = keyguardRootViewModel;
        this.configurationState = configurationState;
        this.iconBindingFailureTracker = statusBarIconViewBindingFailureTracker;
        this.nicAodViewModel = notificationIconContainerAlwaysOnDisplayViewModel;
        this.nicAodIconViewStore = alwaysOnDisplayNotificationIconViewStore;
        this.aodPromotedNotificationViewModelFactory = factory2;
        this.systemBarUtilsState = systemBarUtilsState;
        this.keyguardClockViewModel = keyguardClockViewModel;
        if (!Intrinsics.areEqual(notificationStackScrollLayout.getParent(), sharedNotificationContainer)) {
            ViewParent parent = notificationStackScrollLayout.getParent();
            ViewGroup viewGroup = parent instanceof ViewGroup ? (ViewGroup) parent : null;
            if (viewGroup != null) {
                viewGroup.removeView(notificationStackScrollLayout);
            }
            sharedNotificationContainer.addView(notificationStackScrollLayout);
        }
        sharedNotificationContainerBinder.bind(sharedNotificationContainer, sharedNotificationContainerViewModel);
    }
}
