package android.app.usage;

/* loaded from: classes.dex */
public interface FeatureFlags {
    boolean disableIdleCheck();

    boolean filterBasedEventQueryApi();

    boolean getAppArtManagedBytes();

    boolean getAppBytesByDataTypeApi();

    boolean reportUsageStatsPermission();

    boolean useDedicatedHandlerThread();

    boolean useParceledList();

    boolean userInteractionTypeApi();
}
