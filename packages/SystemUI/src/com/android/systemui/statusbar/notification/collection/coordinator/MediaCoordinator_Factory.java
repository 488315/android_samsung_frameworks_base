package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.internal.statusbar.IStatusBarService;
import com.android.systemui.media.controls.util.MediaFeatureFlag;
import com.android.systemui.statusbar.notification.icon.IconManager;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* loaded from: classes3.dex */
public final class MediaCoordinator_Factory implements Provider {
    private final Provider featureFlagProvider;
    private final Provider iconManagerProvider;
    private final Provider statusBarServiceProvider;

    public MediaCoordinator_Factory(Provider provider, Provider provider2, Provider provider3) {
        this.featureFlagProvider = provider;
        this.statusBarServiceProvider = provider2;
        this.iconManagerProvider = provider3;
    }

    public static MediaCoordinator_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3) {
        return new MediaCoordinator_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2), Providers.asDaggerProvider(provider3));
    }

    public static MediaCoordinator newInstance(MediaFeatureFlag mediaFeatureFlag, IStatusBarService iStatusBarService, IconManager iconManager) {
        return new MediaCoordinator(mediaFeatureFlag, iStatusBarService, iconManager);
    }

    public static MediaCoordinator_Factory create(Provider provider, Provider provider2, Provider provider3) {
        return new MediaCoordinator_Factory(provider, provider2, provider3);
    }

    @Override // javax.inject.Provider
    public MediaCoordinator get() {
        return newInstance((MediaFeatureFlag) this.featureFlagProvider.get(), (IStatusBarService) this.statusBarServiceProvider.get(), (IconManager) this.iconManagerProvider.get());
    }
}
