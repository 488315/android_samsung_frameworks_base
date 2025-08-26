package com.android.systemui.statusbar.notification.dagger;

import com.android.systemui.statusbar.notification.logging.NotificationPanelLoggerImpl;
import dagger.internal.Provider;

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
