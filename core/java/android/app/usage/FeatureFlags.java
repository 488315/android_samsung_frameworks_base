package android.app.usage;

public interface FeatureFlags {
    boolean disableIdleCheck();

    boolean filterBasedEventQueryApi();

    boolean getAppBytesByDataTypeApi();

    boolean reportUsageStatsPermission();

    boolean useDedicatedHandlerThread();

    boolean useParceledList();

    boolean userInteractionTypeApi();
}
