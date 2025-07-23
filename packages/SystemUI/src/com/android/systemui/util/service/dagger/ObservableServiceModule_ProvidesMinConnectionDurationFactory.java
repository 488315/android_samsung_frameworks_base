package com.android.systemui.util.service.dagger;

import android.content.res.Resources;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
