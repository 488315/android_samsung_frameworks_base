package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.notification.collection.provider.SectionHeaderVisibilityProvider;
import com.android.systemui.statusbar.notification.interruption.KeyguardNotificationVisibilityProvider;
import com.android.systemui.statusbar.notification.stack.AmbientState;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* loaded from: classes3.dex */
public final class KeyguardCoordinator_Factory implements Provider {
    private final Provider ambientStateProvider;
    private final Provider keyguardNotificationVisibilityProvider;
    private final Provider sectionHeaderVisibilityProvider;
    private final Provider statusBarStateControllerProvider;

    public KeyguardCoordinator_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        this.keyguardNotificationVisibilityProvider = provider;
        this.sectionHeaderVisibilityProvider = provider2;
        this.statusBarStateControllerProvider = provider3;
        this.ambientStateProvider = provider4;
    }

    public static KeyguardCoordinator_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4) {
        return new KeyguardCoordinator_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2), Providers.asDaggerProvider(provider3), Providers.asDaggerProvider(provider4));
    }

    public static KeyguardCoordinator newInstance(KeyguardNotificationVisibilityProvider keyguardNotificationVisibilityProvider, SectionHeaderVisibilityProvider sectionHeaderVisibilityProvider, StatusBarStateController statusBarStateController, AmbientState ambientState) {
        return new KeyguardCoordinator(keyguardNotificationVisibilityProvider, sectionHeaderVisibilityProvider, statusBarStateController, ambientState);
    }

    public static KeyguardCoordinator_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        return new KeyguardCoordinator_Factory(provider, provider2, provider3, provider4);
    }

    @Override // javax.inject.Provider
    public KeyguardCoordinator get() {
        return newInstance((KeyguardNotificationVisibilityProvider) this.keyguardNotificationVisibilityProvider.get(), (SectionHeaderVisibilityProvider) this.sectionHeaderVisibilityProvider.get(), (StatusBarStateController) this.statusBarStateControllerProvider.get(), (AmbientState) this.ambientStateProvider.get());
    }
}
