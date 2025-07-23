package com.android.systemui.statusbar.notification.dagger;

import com.android.internal.jank.InteractionJankMonitor;
import com.android.systemui.statusbar.notification.NotificationLaunchAnimatorControllerProvider;
import com.android.systemui.statusbar.notification.domain.interactor.NotificationLaunchAnimationInteractor;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManager;
import com.android.systemui.statusbar.notification.stack.NotificationListContainer;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class NotificationsModule_ProvideNotificationTransitionAnimatorControllerProviderFactory implements Provider {
    public final Provider headsUpManagerProvider;
    public final Provider jankMonitorProvider;
    public final Provider notificationLaunchAnimationInteractorProvider;
    public final Provider notificationListContainerProvider;

    public NotificationsModule_ProvideNotificationTransitionAnimatorControllerProviderFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        this.notificationLaunchAnimationInteractorProvider = provider;
        this.notificationListContainerProvider = provider2;
        this.headsUpManagerProvider = provider3;
        this.jankMonitorProvider = provider4;
    }

    public static NotificationLaunchAnimatorControllerProvider provideNotificationTransitionAnimatorControllerProvider(NotificationLaunchAnimationInteractor notificationLaunchAnimationInteractor, NotificationListContainer notificationListContainer, HeadsUpManager headsUpManager, InteractionJankMonitor interactionJankMonitor) {
        return new NotificationLaunchAnimatorControllerProvider(notificationLaunchAnimationInteractor, notificationListContainer, headsUpManager, interactionJankMonitor);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new NotificationLaunchAnimatorControllerProvider((NotificationLaunchAnimationInteractor) this.notificationLaunchAnimationInteractorProvider.get(), (NotificationListContainer) this.notificationListContainerProvider.get(), (HeadsUpManager) this.headsUpManagerProvider.get(), (InteractionJankMonitor) this.jankMonitorProvider.get());
    }
}
