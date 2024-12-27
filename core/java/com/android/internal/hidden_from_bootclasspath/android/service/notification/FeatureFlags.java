package com.android.internal.hidden_from_bootclasspath.android.service.notification;

public interface FeatureFlags {
    boolean callstyleCallbackApi();

    boolean rankingUpdateAshmem();

    boolean redactSensitiveNotificationsBigTextStyle();

    boolean redactSensitiveNotificationsFromUntrustedListeners();
}
