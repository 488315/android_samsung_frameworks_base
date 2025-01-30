package com.android.systemui.statusbar.notification.stack;

import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* renamed from: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutListContainerModule_ProvideListContainerFactory */
/* loaded from: classes2.dex */
public final class C2968xab7007e4 implements Provider {
    public final Provider nsslControllerProvider;

    public C2968xab7007e4(Provider provider) {
        this.nsslControllerProvider = provider;
    }

    public static NotificationListContainer provideListContainer(NotificationStackScrollLayoutController notificationStackScrollLayoutController) {
        NotificationStackScrollLayoutController.NotificationListContainerImpl notificationListContainerImpl = notificationStackScrollLayoutController.mNotificationListContainer;
        Preconditions.checkNotNullFromProvides(notificationListContainerImpl);
        return notificationListContainerImpl;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        NotificationStackScrollLayoutController.NotificationListContainerImpl notificationListContainerImpl = ((NotificationStackScrollLayoutController) this.nsslControllerProvider.get()).mNotificationListContainer;
        Preconditions.checkNotNullFromProvides(notificationListContainerImpl);
        return notificationListContainerImpl;
    }
}
