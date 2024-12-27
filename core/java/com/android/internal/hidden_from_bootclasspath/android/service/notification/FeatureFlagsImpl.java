package com.android.internal.hidden_from_bootclasspath.android.service.notification;

public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // com.android.internal.hidden_from_bootclasspath.android.service.notification.FeatureFlags
    public boolean callstyleCallbackApi() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.service.notification.FeatureFlags
    public boolean rankingUpdateAshmem() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.service.notification.FeatureFlags
    public boolean redactSensitiveNotificationsBigTextStyle() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.service.notification.FeatureFlags
    public boolean redactSensitiveNotificationsFromUntrustedListeners() {
        return true;
    }
}
