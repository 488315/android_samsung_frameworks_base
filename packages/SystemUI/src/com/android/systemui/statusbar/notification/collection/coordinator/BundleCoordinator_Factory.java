package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.render.NodeController;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* loaded from: classes3.dex */
public final class BundleCoordinator_Factory implements Provider {
    private final Provider newsHeaderControllerProvider;
    private final Provider promoHeaderControllerProvider;
    private final Provider recsHeaderControllerProvider;
    private final Provider socialHeaderControllerProvider;

    public BundleCoordinator_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        this.newsHeaderControllerProvider = provider;
        this.socialHeaderControllerProvider = provider2;
        this.recsHeaderControllerProvider = provider3;
        this.promoHeaderControllerProvider = provider4;
    }

    public static BundleCoordinator_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4) {
        return new BundleCoordinator_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2), Providers.asDaggerProvider(provider3), Providers.asDaggerProvider(provider4));
    }

    public static BundleCoordinator newInstance(NodeController nodeController, NodeController nodeController2, NodeController nodeController3, NodeController nodeController4) {
        return new BundleCoordinator(nodeController, nodeController2, nodeController3, nodeController4);
    }

    public static BundleCoordinator_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        return new BundleCoordinator_Factory(provider, provider2, provider3, provider4);
    }

    @Override // javax.inject.Provider
    public BundleCoordinator get() {
        return newInstance((NodeController) this.newsHeaderControllerProvider.get(), (NodeController) this.socialHeaderControllerProvider.get(), (NodeController) this.recsHeaderControllerProvider.get(), (NodeController) this.promoHeaderControllerProvider.get());
    }
}
