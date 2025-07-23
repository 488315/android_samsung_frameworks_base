package android.appwidget.flags;

/* loaded from: classes.dex */
public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // android.appwidget.flags.FeatureFlags
    public boolean drawDataParcel() {
        return true;
    }

    @Override // android.appwidget.flags.FeatureFlags
    public boolean engagementMetrics() {
        return false;
    }

    @Override // android.appwidget.flags.FeatureFlags
    public boolean generatedPreviews() {
        return true;
    }

    @Override // android.appwidget.flags.FeatureFlags
    public boolean notKeyguardCategory() {
        return true;
    }

    @Override // android.appwidget.flags.FeatureFlags
    public boolean remoteAdapterConversion() {
        return false;
    }

    @Override // android.appwidget.flags.FeatureFlags
    public boolean remoteDocumentSupport() {
        return true;
    }

    @Override // android.appwidget.flags.FeatureFlags
    public boolean remoteViewsProto() {
        return true;
    }

    @Override // android.appwidget.flags.FeatureFlags
    public boolean removeAppWidgetServiceIoFromCriticalPath() {
        return true;
    }

    @Override // android.appwidget.flags.FeatureFlags
    public boolean securityPolicyInteractAcrossUsers() {
        return true;
    }

    @Override // android.appwidget.flags.FeatureFlags
    public boolean supportResumeRestoreAfterReboot() {
        return false;
    }

    @Override // android.appwidget.flags.FeatureFlags
    public boolean throttleWidgetUpdates() {
        return false;
    }

    @Override // android.appwidget.flags.FeatureFlags
    public boolean useSmallerAppWidgetSystemRadius() {
        return true;
    }
}
