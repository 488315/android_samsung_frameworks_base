package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.notification.collection.provider.HighPriorityProvider;
import com.android.systemui.statusbar.notification.collection.render.NodeController;
import com.android.systemui.statusbar.notification.collection.render.SectionHeaderController;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* loaded from: classes3.dex */
public final class RankingCoordinator_Factory implements Provider {
    private final Provider alertingHeaderControllerProvider;
    private final Provider highPriorityProvider;
    private final Provider silentHeaderControllerProvider;
    private final Provider silentNodeControllerProvider;
    private final Provider statusBarStateControllerProvider;

    public RankingCoordinator_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        this.statusBarStateControllerProvider = provider;
        this.highPriorityProvider = provider2;
        this.alertingHeaderControllerProvider = provider3;
        this.silentHeaderControllerProvider = provider4;
        this.silentNodeControllerProvider = provider5;
    }

    public static RankingCoordinator_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5) {
        return new RankingCoordinator_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2), Providers.asDaggerProvider(provider3), Providers.asDaggerProvider(provider4), Providers.asDaggerProvider(provider5));
    }

    public static RankingCoordinator newInstance(StatusBarStateController statusBarStateController, HighPriorityProvider highPriorityProvider, NodeController nodeController, SectionHeaderController sectionHeaderController, NodeController nodeController2) {
        return new RankingCoordinator(statusBarStateController, highPriorityProvider, nodeController, sectionHeaderController, nodeController2);
    }

    public static RankingCoordinator_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        return new RankingCoordinator_Factory(provider, provider2, provider3, provider4, provider5);
    }

    @Override // javax.inject.Provider
    public RankingCoordinator get() {
        return newInstance((StatusBarStateController) this.statusBarStateControllerProvider.get(), (HighPriorityProvider) this.highPriorityProvider.get(), (NodeController) this.alertingHeaderControllerProvider.get(), (SectionHeaderController) this.silentHeaderControllerProvider.get(), (NodeController) this.silentNodeControllerProvider.get());
    }
}
