package com.android.systemui.util.service.dagger;

import android.content.res.Resources;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* loaded from: classes3.dex */
public final class ObservableServiceModule_ProvidesMinConnectionDurationFactory implements Provider {
    private final Provider resourcesProvider;

    public ObservableServiceModule_ProvidesMinConnectionDurationFactory(Provider provider) {
        this.resourcesProvider = provider;
    }

    public static ObservableServiceModule_ProvidesMinConnectionDurationFactory create(javax.inject.Provider provider) {
        return new ObservableServiceModule_ProvidesMinConnectionDurationFactory(Providers.asDaggerProvider(provider));
    }

    public static int providesMinConnectionDuration(Resources resources) {
        return ObservableServiceModule.providesMinConnectionDuration(resources);
    }

    public static ObservableServiceModule_ProvidesMinConnectionDurationFactory create(Provider provider) {
        return new ObservableServiceModule_ProvidesMinConnectionDurationFactory(provider);
    }

    @Override // javax.inject.Provider
    public Integer get() {
        return Integer.valueOf(providesMinConnectionDuration((Resources) this.resourcesProvider.get()));
    }
}
