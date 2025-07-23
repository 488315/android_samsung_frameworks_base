package com.android.internal.os;

/* loaded from: classes5.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_APPLICATION_SHARED_MEMORY_ENABLED = "com.android.internal.os.application_shared_memory_enabled";
    public static final String FLAG_DEBUG_STORE_ENABLED = "com.android.internal.os.debug_store_enabled";
    public static final String FLAG_ENABLE_APACHE_HTTP_LEGACY_PRELOAD = "com.android.internal.os.enable_apache_http_legacy_preload";
    public static final String FLAG_ENABLE_MEDIA_AND_LOCATION_PRELOAD = "com.android.internal.os.enable_media_and_location_preload";
    public static final String FLAG_RAVENWOOD_FLAG_RO_1 = "com.android.internal.os.ravenwood_flag_ro_1";
    public static final String FLAG_RAVENWOOD_FLAG_RO_2 = "com.android.internal.os.ravenwood_flag_ro_2";
    public static final String FLAG_RAVENWOOD_FLAG_RW_1 = "com.android.internal.os.ravenwood_flag_rw_1";
    public static final String FLAG_RAVENWOOD_FLAG_RW_2 = "com.android.internal.os.ravenwood_flag_rw_2";
    public static final String FLAG_USE_TRANSACTION_CODES_FOR_UNKNOWN_METHODS = "com.android.internal.os.use_transaction_codes_for_unknown_methods";

    public static boolean applicationSharedMemoryEnabled() {
        return FEATURE_FLAGS.applicationSharedMemoryEnabled();
    }

    public static boolean debugStoreEnabled() {
        return FEATURE_FLAGS.debugStoreEnabled();
    }

    public static boolean enableApacheHttpLegacyPreload() {
        return FEATURE_FLAGS.enableApacheHttpLegacyPreload();
    }

    public static boolean enableMediaAndLocationPreload() {
        return FEATURE_FLAGS.enableMediaAndLocationPreload();
    }

    public static boolean ravenwoodFlagRo1() {
        return FEATURE_FLAGS.ravenwoodFlagRo1();
    }

    public static boolean ravenwoodFlagRo2() {
        return FEATURE_FLAGS.ravenwoodFlagRo2();
    }

    public static boolean ravenwoodFlagRw1() {
        return FEATURE_FLAGS.ravenwoodFlagRw1();
    }

    public static boolean ravenwoodFlagRw2() {
        return FEATURE_FLAGS.ravenwoodFlagRw2();
    }

    public static boolean useTransactionCodesForUnknownMethods() {
        return FEATURE_FLAGS.useTransactionCodesForUnknownMethods();
    }
}
