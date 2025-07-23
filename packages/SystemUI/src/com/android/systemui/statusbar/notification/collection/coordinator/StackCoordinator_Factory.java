package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.render.GroupExpansionManagerImpl;
import com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor;
import com.android.systemui.statusbar.notification.domain.interactor.RenderNotificationListInteractor;
import com.android.systemui.statusbar.phone.NotificationIconAreaController;
import com.android.systemui.statusbar.policy.SensitiveNotificationProtectionController;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class StackCoordinator_Factory implements Provider {
    private final Provider activeNotificationsInteractorProvider;
    private final Provider groupExpansionManagerImplProvider;
    private final Provider notificationIconAreaControllerProvider;
    private final Provider renderListInteractorProvider;
    private final Provider sensitiveNotificationProtectionControllerProvider;

    public StackCoordinator_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        this.groupExpansionManagerImplProvider = provider;
        this.notificationIconAreaControllerProvider = provider2;
        this.renderListInteractorProvider = provider3;
        this.activeNotificationsInteractorProvider = provider4;
        this.sensitiveNotificationProtectionControllerProvider = provider5;
    }

    public static StackCoordinator_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5) {
        return new StackCoordinator_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2), Providers.asDaggerProvider(provider3), Providers.asDaggerProvider(provider4), Providers.asDaggerProvider(provider5));
    }

    public static StackCoordinator newInstance(GroupExpansionManagerImpl groupExpansionManagerImpl, NotificationIconAreaController notificationIconAreaController, RenderNotificationListInteractor renderNotificationListInteractor, ActiveNotificationsInteractor activeNotificationsInteractor, SensitiveNotificationProtectionController sensitiveNotificationProtectionController) {
        return new StackCoordinator(groupExpansionManagerImpl, notificationIconAreaController, renderNotificationListInteractor, activeNotificationsInteractor, sensitiveNotificationProtectionController);
    }

    public static StackCoordinator_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        return new StackCoordinator_Factory(provider, provider2, provider3, provider4, provider5);
    }

    @Override // javax.inject.Provider
    public StackCoordinator get() {
        return newInstance((GroupExpansionManagerImpl) this.groupExpansionManagerImplProvider.get(), (NotificationIconAreaController) this.notificationIconAreaControllerProvider.get(), (RenderNotificationListInteractor) this.renderListInteractorProvider.get(), (ActiveNotificationsInteractor) this.activeNotificationsInteractorProvider.get(), (SensitiveNotificationProtectionController) this.sensitiveNotificationProtectionControllerProvider.get());
    }
}
