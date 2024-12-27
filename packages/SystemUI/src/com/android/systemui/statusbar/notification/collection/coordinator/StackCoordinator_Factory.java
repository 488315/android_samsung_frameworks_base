package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.render.GroupExpansionManagerImpl;
import com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor;
import com.android.systemui.statusbar.notification.domain.interactor.RenderNotificationListInteractor;
import com.android.systemui.statusbar.phone.NotificationIconAreaController;
import com.android.systemui.statusbar.policy.SensitiveNotificationProtectionController;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class StackCoordinator_Factory implements Provider {
    private final javax.inject.Provider activeNotificationsInteractorProvider;
    private final javax.inject.Provider groupExpansionManagerImplProvider;
    private final javax.inject.Provider notificationIconAreaControllerProvider;
    private final javax.inject.Provider renderListInteractorProvider;
    private final javax.inject.Provider sensitiveNotificationProtectionControllerProvider;

    public StackCoordinator_Factory(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5) {
        this.groupExpansionManagerImplProvider = provider;
        this.notificationIconAreaControllerProvider = provider2;
        this.renderListInteractorProvider = provider3;
        this.activeNotificationsInteractorProvider = provider4;
        this.sensitiveNotificationProtectionControllerProvider = provider5;
    }

    public static StackCoordinator_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5) {
        return new StackCoordinator_Factory(provider, provider2, provider3, provider4, provider5);
    }

    public static StackCoordinator newInstance(GroupExpansionManagerImpl groupExpansionManagerImpl, NotificationIconAreaController notificationIconAreaController, RenderNotificationListInteractor renderNotificationListInteractor, ActiveNotificationsInteractor activeNotificationsInteractor, SensitiveNotificationProtectionController sensitiveNotificationProtectionController) {
        return new StackCoordinator(groupExpansionManagerImpl, notificationIconAreaController, renderNotificationListInteractor, activeNotificationsInteractor, sensitiveNotificationProtectionController);
    }

    @Override // javax.inject.Provider
    public StackCoordinator get() {
        return newInstance((GroupExpansionManagerImpl) this.groupExpansionManagerImplProvider.get(), (NotificationIconAreaController) this.notificationIconAreaControllerProvider.get(), (RenderNotificationListInteractor) this.renderListInteractorProvider.get(), (ActiveNotificationsInteractor) this.activeNotificationsInteractorProvider.get(), (SensitiveNotificationProtectionController) this.sensitiveNotificationProtectionControllerProvider.get());
    }
}
