package android.appwidget.flags;

/* loaded from: classes.dex */
public interface FeatureFlags {
    boolean drawDataParcel();

    boolean generatedPreviews();

    boolean remoteAdapterConversion();

    boolean removeAppWidgetServiceIoFromCriticalPath();

    boolean supportResumeRestoreAfterReboot();

    boolean throttleWidgetUpdates();
}
