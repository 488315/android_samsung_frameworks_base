package com.android.internal.os;

/* loaded from: classes5.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_ENABLE_APACHE_HTTP_LEGACY_PRELOAD =
            "com.android.internal.os.enable_apache_http_legacy_preload";

    public static boolean enableApacheHttpLegacyPreload() {
        return FEATURE_FLAGS.enableApacheHttpLegacyPreload();
    }
}
