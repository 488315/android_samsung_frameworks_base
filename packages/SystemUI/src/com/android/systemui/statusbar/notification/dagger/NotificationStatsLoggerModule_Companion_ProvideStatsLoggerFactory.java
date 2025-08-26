package com.android.systemui.statusbar.notification.dagger;

import dagger.internal.Provider;
import java.util.Optional;

/* loaded from: classes3.dex */
public final class NotificationStatsLoggerModule_Companion_ProvideStatsLoggerFactory implements Provider {
    public final Provider providerProvider;

    public NotificationStatsLoggerModule_Companion_ProvideStatsLoggerFactory(Provider provider) {
        this.providerProvider = provider;
    }

    public static Optional provideStatsLogger(javax.inject.Provider provider) {
        NotificationStatsLoggerModule.Companion.getClass();
        Optional optionalOf = Optional.of(provider.get());
        optionalOf.getClass();
        return optionalOf;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideStatsLogger(this.providerProvider);
    }
}
