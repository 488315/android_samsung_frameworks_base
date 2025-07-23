package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.provider.NotificationDismissibilityProviderImpl;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DismissibilityCoordinator_Factory implements Provider {
    private final Provider keyguardStateControllerProvider;
    private final Provider providerProvider;

    public DismissibilityCoordinator_Factory(Provider provider, Provider provider2) {
        this.keyguardStateControllerProvider = provider;
        this.providerProvider = provider2;
    }

    public static DismissibilityCoordinator_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2) {
        return new DismissibilityCoordinator_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2));
    }

    public static DismissibilityCoordinator newInstance(KeyguardStateController keyguardStateController, NotificationDismissibilityProviderImpl notificationDismissibilityProviderImpl) {
        return new DismissibilityCoordinator(keyguardStateController, notificationDismissibilityProviderImpl);
    }

    public static DismissibilityCoordinator_Factory create(Provider provider, Provider provider2) {
        return new DismissibilityCoordinator_Factory(provider, provider2);
    }

    @Override // javax.inject.Provider
    public DismissibilityCoordinator get() {
        return newInstance((KeyguardStateController) this.keyguardStateControllerProvider.get(), (NotificationDismissibilityProviderImpl) this.providerProvider.get());
    }
}
