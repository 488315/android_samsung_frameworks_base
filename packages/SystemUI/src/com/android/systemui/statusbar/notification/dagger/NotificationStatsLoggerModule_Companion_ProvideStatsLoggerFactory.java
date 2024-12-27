package com.android.systemui.statusbar.notification.dagger;

import com.android.systemui.Flags;
import dagger.internal.Provider;
import java.util.Optional;
import kotlin.jvm.internal.Intrinsics;

public final class NotificationStatsLoggerModule_Companion_ProvideStatsLoggerFactory implements Provider {
    public final javax.inject.Provider providerProvider;

    public NotificationStatsLoggerModule_Companion_ProvideStatsLoggerFactory(javax.inject.Provider provider) {
    }

    public static Optional provideStatsLogger() {
        NotificationStatsLoggerModule.Companion.getClass();
        Flags.notificationsLiveDataStoreRefactor();
        Optional empty = Optional.empty();
        Intrinsics.checkNotNull(empty);
        return empty;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideStatsLogger();
    }
}
