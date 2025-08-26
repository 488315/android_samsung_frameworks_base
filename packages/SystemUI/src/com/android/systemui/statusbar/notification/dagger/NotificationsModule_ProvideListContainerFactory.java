package com.android.systemui.statusbar.notification.dagger;

import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class NotificationsModule_ProvideListContainerFactory implements Provider {
    public final Provider nsslControllerProvider;

    public NotificationsModule_ProvideListContainerFactory(Provider provider) {
        this.nsslControllerProvider = provider;
    }

    public static NotificationStackScrollLayoutController.NotificationListContainerImpl provideListContainer(NotificationStackScrollLayoutController notificationStackScrollLayoutController) {
        NotificationStackScrollLayoutController.NotificationListContainerImpl notificationListContainerImpl = notificationStackScrollLayoutController.mNotificationListContainer;
        notificationListContainerImpl.getClass();
        return notificationListContainerImpl;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        NotificationStackScrollLayoutController.NotificationListContainerImpl notificationListContainerImpl = ((NotificationStackScrollLayoutController) this.nsslControllerProvider.get()).mNotificationListContainer;
        notificationListContainerImpl.getClass();
        return notificationListContainerImpl;
    }
}
