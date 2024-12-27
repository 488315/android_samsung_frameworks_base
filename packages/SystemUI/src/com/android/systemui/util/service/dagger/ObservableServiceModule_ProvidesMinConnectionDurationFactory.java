package com.android.systemui.util.service.dagger;

import android.content.res.Resources;
import dagger.internal.Provider;

public final class ObservableServiceModule_ProvidesMinConnectionDurationFactory implements Provider {
    private final javax.inject.Provider resourcesProvider;

    public ObservableServiceModule_ProvidesMinConnectionDurationFactory(javax.inject.Provider provider) {
        this.resourcesProvider = provider;
    }

    public static ObservableServiceModule_ProvidesMinConnectionDurationFactory create(javax.inject.Provider provider) {
        return new ObservableServiceModule_ProvidesMinConnectionDurationFactory(provider);
    }

    public static int providesMinConnectionDuration(Resources resources) {
        return ObservableServiceModule.providesMinConnectionDuration(resources);
    }

    @Override // javax.inject.Provider
    public Integer get() {
        return Integer.valueOf(providesMinConnectionDuration((Resources) this.resourcesProvider.get()));
    }
}
