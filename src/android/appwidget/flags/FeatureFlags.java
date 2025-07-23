package android.appwidget.flags;

/* loaded from: classes.dex */
public interface FeatureFlags {
    boolean drawDataParcel();

    boolean engagementMetrics();

    boolean generatedPreviews();

    boolean notKeyguardCategory();

    boolean remoteAdapterConversion();

    boolean remoteDocumentSupport();

    boolean remoteViewsProto();

    boolean removeAppWidgetServiceIoFromCriticalPath();

    boolean securityPolicyInteractAcrossUsers();

    boolean supportResumeRestoreAfterReboot();

    boolean throttleWidgetUpdates();

    boolean useSmallerAppWidgetSystemRadius();
}
