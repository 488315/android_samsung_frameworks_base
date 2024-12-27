package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.stack.ui.view.NotificationStatsLogger;
import dagger.internal.Provider;
import java.util.Optional;

public final class NotificationStatsLoggerCoordinator_Factory implements Provider {
    private final javax.inject.Provider loggerOptionalProvider;

    public NotificationStatsLoggerCoordinator_Factory(javax.inject.Provider provider) {
        this.loggerOptionalProvider = provider;
    }

    public static NotificationStatsLoggerCoordinator_Factory create(javax.inject.Provider provider) {
        return new NotificationStatsLoggerCoordinator_Factory(provider);
    }

    public static NotificationStatsLoggerCoordinator newInstance(Optional<NotificationStatsLogger> optional) {
        return new NotificationStatsLoggerCoordinator(optional);
    }

    @Override // javax.inject.Provider
    public NotificationStatsLoggerCoordinator get() {
        return newInstance((Optional) this.loggerOptionalProvider.get());
    }
}
