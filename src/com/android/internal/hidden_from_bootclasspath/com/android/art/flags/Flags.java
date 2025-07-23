package com.android.internal.hidden_from_bootclasspath.com.android.art.flags;

/* loaded from: classes5.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_ALWAYS_ENABLE_PROFILE_CODE = "com.android.art.flags.always_enable_profile_code";
    public static final String FLAG_ART_SERVICE_V3 = "com.android.art.flags.art_service_v3";
    public static final String FLAG_EXECUTABLE_METHOD_FILE_OFFSETS = "com.android.art.flags.executable_method_file_offsets";
    public static final String FLAG_EXECUTABLE_METHOD_FILE_OFFSETS_V2 = "com.android.art.flags.executable_method_file_offsets_v2";

    public static boolean executableMethodFileOffsets() {
        return true;
    }

    public static boolean alwaysEnableProfileCode() {
        return FEATURE_FLAGS.alwaysEnableProfileCode();
    }

    public static boolean artServiceV3() {
        return FEATURE_FLAGS.artServiceV3();
    }

    public static boolean executableMethodFileOffsetsV2() {
        return FEATURE_FLAGS.executableMethodFileOffsetsV2();
    }
}
