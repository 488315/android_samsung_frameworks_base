package com.android.internal.hidden_from_bootclasspath.android.service.notification;

/* loaded from: classes5.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_CALLSTYLE_CALLBACK_API = "android.service.notification.callstyle_callback_api";
    public static final String FLAG_RANKING_UPDATE_ASHMEM = "android.service.notification.ranking_update_ashmem";
    public static final String FLAG_REDACT_SENSITIVE_NOTIFICATIONS_BIG_TEXT_STYLE = "android.service.notification.redact_sensitive_notifications_big_text_style";
    public static final String FLAG_REDACT_SENSITIVE_NOTIFICATIONS_FROM_UNTRUSTED_LISTENERS = "android.service.notification.redact_sensitive_notifications_from_untrusted_listeners";

    public static boolean callstyleCallbackApi() {
        return FEATURE_FLAGS.callstyleCallbackApi();
    }

    public static boolean rankingUpdateAshmem() {
        return FEATURE_FLAGS.rankingUpdateAshmem();
    }

    public static boolean redactSensitiveNotificationsBigTextStyle() {
        return FEATURE_FLAGS.redactSensitiveNotificationsBigTextStyle();
    }

    public static boolean redactSensitiveNotificationsFromUntrustedListeners() {
        return FEATURE_FLAGS.redactSensitiveNotificationsFromUntrustedListeners();
    }
}
