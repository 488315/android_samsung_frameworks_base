package com.android.systemui.statusbar.notification.dagger;

import com.android.systemui.statusbar.notification.logging.NotificationPanelLoggerImpl;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class NotificationsModule_ProvideNotificationPanelLoggerFactory implements Provider {
    public static NotificationPanelLoggerImpl provideNotificationPanelLogger() {
        return new NotificationPanelLoggerImpl();
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new NotificationPanelLoggerImpl();
    }
}
