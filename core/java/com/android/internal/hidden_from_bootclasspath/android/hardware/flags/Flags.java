package com.android.internal.hidden_from_bootclasspath.android.hardware.flags;

/* loaded from: classes5.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_OVERLAYPROPERTIES_CLASS_API =
            "android.hardware.flags.overlayproperties_class_api";

    public static boolean overlaypropertiesClassApi() {
        return FEATURE_FLAGS.overlaypropertiesClassApi();
    }
}
