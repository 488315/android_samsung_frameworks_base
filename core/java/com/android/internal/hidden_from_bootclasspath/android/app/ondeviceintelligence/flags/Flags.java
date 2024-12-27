package com.android.internal.hidden_from_bootclasspath.android.app.ondeviceintelligence.flags;

/* loaded from: classes5.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_ENABLE_ON_DEVICE_INTELLIGENCE = "android.app.ondeviceintelligence.flags.enable_on_device_intelligence";

    public static boolean enableOnDeviceIntelligence() {
        return FEATURE_FLAGS.enableOnDeviceIntelligence();
    }
}
