package com.android.systemui.statusbar.notification.dagger;

import com.android.systemui.Flags;
import dagger.internal.Provider;
import java.util.Optional;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
