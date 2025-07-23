package com.android.systemui.util.service.dagger;

import android.content.res.Resources;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ObservableServiceModule_ProvidesMaxReconnectAttemptsFactory implements Provider {
    private final Provider resourcesProvider;

    public ObservableServiceModule_ProvidesMaxReconnectAttemptsFactory(Provider provider) {
        this.resourcesProvider = provider;
    }

    public static ObservableServiceModule_ProvidesMaxReconnectAttemptsFactory create(javax.inject.Provider provider) {
        return new ObservableServiceModule_ProvidesMaxReconnectAttemptsFactory(Providers.asDaggerProvider(provider));
    }

    public static int providesMaxReconnectAttempts(Resources resources) {
        return ObservableServiceModule.providesMaxReconnectAttempts(resources);
    }

    public static ObservableServiceModule_ProvidesMaxReconnectAttemptsFactory create(Provider provider) {
        return new ObservableServiceModule_ProvidesMaxReconnectAttemptsFactory(provider);
    }

    @Override // javax.inject.Provider
    public Integer get() {
        return Integer.valueOf(providesMaxReconnectAttempts((Resources) this.resourcesProvider.get()));
    }
}
