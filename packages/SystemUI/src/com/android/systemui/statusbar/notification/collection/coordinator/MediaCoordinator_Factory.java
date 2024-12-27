package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.internal.statusbar.IStatusBarService;
import com.android.systemui.media.controls.util.MediaFeatureFlag;
import com.android.systemui.statusbar.notification.icon.IconManager;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class MediaCoordinator_Factory implements Provider {
    private final javax.inject.Provider featureFlagProvider;
    private final javax.inject.Provider iconManagerProvider;
    private final javax.inject.Provider statusBarServiceProvider;

    public MediaCoordinator_Factory(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3) {
        this.featureFlagProvider = provider;
        this.statusBarServiceProvider = provider2;
        this.iconManagerProvider = provider3;
    }

    public static MediaCoordinator_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3) {
        return new MediaCoordinator_Factory(provider, provider2, provider3);
    }

    public static MediaCoordinator newInstance(MediaFeatureFlag mediaFeatureFlag, IStatusBarService iStatusBarService, IconManager iconManager) {
        return new MediaCoordinator(mediaFeatureFlag, iStatusBarService, iconManager);
    }

    @Override // javax.inject.Provider
    public MediaCoordinator get() {
        return newInstance((MediaFeatureFlag) this.featureFlagProvider.get(), (IStatusBarService) this.statusBarServiceProvider.get(), (IconManager) this.iconManagerProvider.get());
    }
}
