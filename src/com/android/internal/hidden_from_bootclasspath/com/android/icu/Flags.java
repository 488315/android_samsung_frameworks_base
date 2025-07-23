package com.android.internal.hidden_from_bootclasspath.com.android.icu;

/* loaded from: classes5.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_ICU_25Q2_API = "com.android.icu.icu_25q2_api";
    public static final String FLAG_ICU_V_API = "com.android.icu.icu_v_api";
    public static final String FLAG_TELEPHONY_LOOKUP_MCC_EXTENSION = "com.android.icu.telephony_lookup_mcc_extension";

    public static boolean icuVApi() {
        return true;
    }

    public static boolean icu25q2Api() {
        return FEATURE_FLAGS.icu25q2Api();
    }

    public static boolean telephonyLookupMccExtension() {
        return FEATURE_FLAGS.telephonyLookupMccExtension();
    }
}
