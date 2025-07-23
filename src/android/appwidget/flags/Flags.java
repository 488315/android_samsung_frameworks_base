package android.appwidget.flags;

/* loaded from: classes.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_DRAW_DATA_PARCEL = "android.appwidget.flags.draw_data_parcel";
    public static final String FLAG_ENGAGEMENT_METRICS = "android.appwidget.flags.engagement_metrics";
    public static final String FLAG_GENERATED_PREVIEWS = "android.appwidget.flags.generated_previews";
    public static final String FLAG_NOT_KEYGUARD_CATEGORY = "android.appwidget.flags.not_keyguard_category";
    public static final String FLAG_REMOTE_ADAPTER_CONVERSION = "android.appwidget.flags.remote_adapter_conversion";
    public static final String FLAG_REMOTE_DOCUMENT_SUPPORT = "android.appwidget.flags.remote_document_support";
    public static final String FLAG_REMOTE_VIEWS_PROTO = "android.appwidget.flags.remote_views_proto";
    public static final String FLAG_REMOVE_APP_WIDGET_SERVICE_IO_FROM_CRITICAL_PATH = "android.appwidget.flags.remove_app_widget_service_io_from_critical_path";
    public static final String FLAG_SECURITY_POLICY_INTERACT_ACROSS_USERS = "android.appwidget.flags.security_policy_interact_across_users";
    public static final String FLAG_SUPPORT_RESUME_RESTORE_AFTER_REBOOT = "android.appwidget.flags.support_resume_restore_after_reboot";
    public static final String FLAG_THROTTLE_WIDGET_UPDATES = "android.appwidget.flags.throttle_widget_updates";
    public static final String FLAG_USE_SMALLER_APP_WIDGET_SYSTEM_RADIUS = "android.appwidget.flags.use_smaller_app_widget_system_radius";

    public static boolean drawDataParcel() {
        return FEATURE_FLAGS.drawDataParcel();
    }

    public static boolean engagementMetrics() {
        return FEATURE_FLAGS.engagementMetrics();
    }

    public static boolean generatedPreviews() {
        return FEATURE_FLAGS.generatedPreviews();
    }

    public static boolean notKeyguardCategory() {
        return FEATURE_FLAGS.notKeyguardCategory();
    }

    public static boolean remoteAdapterConversion() {
        return FEATURE_FLAGS.remoteAdapterConversion();
    }

    public static boolean remoteDocumentSupport() {
        return FEATURE_FLAGS.remoteDocumentSupport();
    }

    public static boolean remoteViewsProto() {
        return FEATURE_FLAGS.remoteViewsProto();
    }

    public static boolean removeAppWidgetServiceIoFromCriticalPath() {
        return FEATURE_FLAGS.removeAppWidgetServiceIoFromCriticalPath();
    }

    public static boolean securityPolicyInteractAcrossUsers() {
        return FEATURE_FLAGS.securityPolicyInteractAcrossUsers();
    }

    public static boolean supportResumeRestoreAfterReboot() {
        return FEATURE_FLAGS.supportResumeRestoreAfterReboot();
    }

    public static boolean throttleWidgetUpdates() {
        return FEATURE_FLAGS.throttleWidgetUpdates();
    }

    public static boolean useSmallerAppWidgetSystemRadius() {
        return FEATURE_FLAGS.useSmallerAppWidgetSystemRadius();
    }
}
