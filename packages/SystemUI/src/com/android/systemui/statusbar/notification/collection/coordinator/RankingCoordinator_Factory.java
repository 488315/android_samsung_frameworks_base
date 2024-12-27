package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.notification.collection.provider.HighPriorityProvider;
import com.android.systemui.statusbar.notification.collection.render.NodeController;
import com.android.systemui.statusbar.notification.collection.render.SectionHeaderController;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class RankingCoordinator_Factory implements Provider {
    private final javax.inject.Provider alertingHeaderControllerProvider;
    private final javax.inject.Provider highPriorityProvider;
    private final javax.inject.Provider silentHeaderControllerProvider;
    private final javax.inject.Provider silentNodeControllerProvider;
    private final javax.inject.Provider statusBarStateControllerProvider;

    public RankingCoordinator_Factory(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5) {
        this.statusBarStateControllerProvider = provider;
        this.highPriorityProvider = provider2;
        this.alertingHeaderControllerProvider = provider3;
        this.silentHeaderControllerProvider = provider4;
        this.silentNodeControllerProvider = provider5;
    }

    public static RankingCoordinator_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5) {
        return new RankingCoordinator_Factory(provider, provider2, provider3, provider4, provider5);
    }

    public static RankingCoordinator newInstance(StatusBarStateController statusBarStateController, HighPriorityProvider highPriorityProvider, NodeController nodeController, SectionHeaderController sectionHeaderController, NodeController nodeController2) {
        return new RankingCoordinator(statusBarStateController, highPriorityProvider, nodeController, sectionHeaderController, nodeController2);
    }

    @Override // javax.inject.Provider
    public RankingCoordinator get() {
        return newInstance((StatusBarStateController) this.statusBarStateControllerProvider.get(), (HighPriorityProvider) this.highPriorityProvider.get(), (NodeController) this.alertingHeaderControllerProvider.get(), (SectionHeaderController) this.silentHeaderControllerProvider.get(), (NodeController) this.silentNodeControllerProvider.get());
    }
}
