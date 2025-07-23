package com.android.systemui.statusbar.notification.dagger;

import dagger.internal.Provider;
import java.util.Optional;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class NotificationStatsLoggerModule_Companion_ProvideStatsLoggerFactory implements Provider {
    public final Provider providerProvider;

    public NotificationStatsLoggerModule_Companion_ProvideStatsLoggerFactory(Provider provider) {
        this.providerProvider = provider;
    }

    public static Optional provideStatsLogger(javax.inject.Provider provider) {
        NotificationStatsLoggerModule.Companion.getClass();
        Optional of = Optional.of(provider.get());
        of.getClass();
        return of;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideStatsLogger(this.providerProvider);
    }
}
