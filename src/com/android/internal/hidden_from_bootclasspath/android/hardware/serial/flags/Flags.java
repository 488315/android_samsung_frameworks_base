package com.android.internal.hidden_from_bootclasspath.android.hardware.serial.flags;

/* loaded from: classes5.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_ENABLE_SERIAL_API = "android.hardware.serial.flags.enable_serial_api";

    public static boolean enableSerialApi() {
        return FEATURE_FLAGS.enableSerialApi();
    }
}
