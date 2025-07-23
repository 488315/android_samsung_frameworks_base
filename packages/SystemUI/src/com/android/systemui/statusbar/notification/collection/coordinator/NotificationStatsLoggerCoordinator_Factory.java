package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.stack.ui.view.NotificationStatsLogger;
import dagger.internal.Provider;
import dagger.internal.Providers;
import java.util.Optional;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class NotificationStatsLoggerCoordinator_Factory implements Provider {
    private final Provider loggerOptionalProvider;

    public NotificationStatsLoggerCoordinator_Factory(Provider provider) {
        this.loggerOptionalProvider = provider;
    }

    public static NotificationStatsLoggerCoordinator_Factory create(javax.inject.Provider provider) {
        return new NotificationStatsLoggerCoordinator_Factory(Providers.asDaggerProvider(provider));
    }

    public static NotificationStatsLoggerCoordinator newInstance(Optional<NotificationStatsLogger> optional) {
        return new NotificationStatsLoggerCoordinator(optional);
    }

    public static NotificationStatsLoggerCoordinator_Factory create(Provider provider) {
        return new NotificationStatsLoggerCoordinator_Factory(provider);
    }

    @Override // javax.inject.Provider
    public NotificationStatsLoggerCoordinator get() {
        return newInstance((Optional) this.loggerOptionalProvider.get());
    }
}
