package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.provider.NotificationDismissibilityProviderImpl;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class DismissibilityCoordinator_Factory implements Provider {
    private final javax.inject.Provider keyguardStateControllerProvider;
    private final javax.inject.Provider providerProvider;

    public DismissibilityCoordinator_Factory(javax.inject.Provider provider, javax.inject.Provider provider2) {
        this.keyguardStateControllerProvider = provider;
        this.providerProvider = provider2;
    }

    public static DismissibilityCoordinator_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2) {
        return new DismissibilityCoordinator_Factory(provider, provider2);
    }

    public static DismissibilityCoordinator newInstance(KeyguardStateController keyguardStateController, NotificationDismissibilityProviderImpl notificationDismissibilityProviderImpl) {
        return new DismissibilityCoordinator(keyguardStateController, notificationDismissibilityProviderImpl);
    }

    @Override // javax.inject.Provider
    public DismissibilityCoordinator get() {
        return newInstance((KeyguardStateController) this.keyguardStateControllerProvider.get(), (NotificationDismissibilityProviderImpl) this.providerProvider.get());
    }
}
