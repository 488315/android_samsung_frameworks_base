package com.android.internal.hidden_from_bootclasspath.android.xr;

/* loaded from: classes5.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_XR_MANIFEST_ENTRIES = "android.xr.xr_manifest_entries";

    public static boolean xrManifestEntries() {
        return FEATURE_FLAGS.xrManifestEntries();
    }
}
