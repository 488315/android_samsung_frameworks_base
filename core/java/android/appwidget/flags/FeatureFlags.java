package android.appwidget.flags;

public interface FeatureFlags {
    boolean drawDataParcel();

    boolean generatedPreviews();

    boolean remoteAdapterConversion();

    boolean removeAppWidgetServiceIoFromCriticalPath();

    boolean supportResumeRestoreAfterReboot();

    boolean throttleWidgetUpdates();
}
