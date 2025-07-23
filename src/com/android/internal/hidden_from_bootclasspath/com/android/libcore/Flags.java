package com.android.internal.hidden_from_bootclasspath.com.android.libcore;

/* loaded from: classes5.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_APPINFO = "com.android.libcore.appinfo";
    public static final String FLAG_HPKE_PUBLIC_API = "com.android.libcore.hpke_public_api";
    public static final String FLAG_HPKE_V_APIS = "com.android.libcore.hpke_v_apis";
    public static final String FLAG_MADVISE_API = "com.android.libcore.madvise_api";
    public static final String FLAG_NATIVE_METRICS = "com.android.libcore.native_metrics";
    public static final String FLAG_OPENJDK21_STRINGCONCAT = "com.android.libcore.openjdk21_stringconcat";
    public static final String FLAG_OPENJDK_21_V1_APIS = "com.android.libcore.openjdk_21_v1_apis";
    public static final String FLAG_OPENJDK_21_V2_APIS = "com.android.libcore.openjdk_21_v2_apis";
    public static final String FLAG_POST_CLEANUP_APIS = "com.android.libcore.post_cleanup_apis";
    public static final String FLAG_READ_ONLY_DYNAMIC_CODE_LOAD = "com.android.libcore.read_only_dynamic_code_load";
    public static final String FLAG_V_APIS = "com.android.libcore.v_apis";

    public static boolean hpkeVApis() {
        return true;
    }

    public static boolean vApis() {
        return true;
    }

    public static boolean appinfo() {
        return FEATURE_FLAGS.appinfo();
    }

    public static boolean hpkePublicApi() {
        return FEATURE_FLAGS.hpkePublicApi();
    }

    public static boolean madviseApi() {
        return FEATURE_FLAGS.madviseApi();
    }

    public static boolean nativeMetrics() {
        return FEATURE_FLAGS.nativeMetrics();
    }

    public static boolean openjdk21Stringconcat() {
        return FEATURE_FLAGS.openjdk21Stringconcat();
    }

    public static boolean openjdk21V1Apis() {
        return FEATURE_FLAGS.openjdk21V1Apis();
    }

    public static boolean openjdk21V2Apis() {
        return FEATURE_FLAGS.openjdk21V2Apis();
    }

    public static boolean postCleanupApis() {
        return FEATURE_FLAGS.postCleanupApis();
    }

    public static boolean readOnlyDynamicCodeLoad() {
        return FEATURE_FLAGS.readOnlyDynamicCodeLoad();
    }
}
