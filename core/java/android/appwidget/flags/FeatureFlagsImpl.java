package android.appwidget.flags;

public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // android.appwidget.flags.FeatureFlags
    public boolean drawDataParcel() {
        return true;
    }

    @Override // android.appwidget.flags.FeatureFlags
    public boolean generatedPreviews() {
        return true;
    }

    @Override // android.appwidget.flags.FeatureFlags
    public boolean remoteAdapterConversion() {
        return false;
    }

    @Override // android.appwidget.flags.FeatureFlags
    public boolean removeAppWidgetServiceIoFromCriticalPath() {
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
}
