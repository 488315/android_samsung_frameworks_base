package com.android.internal.hidden_from_bootclasspath.com.android.org.conscrypt.flags;

/* loaded from: classes5.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_CERTIFICATE_TRANSPARENCY_CHECKSERVERTRUSTED_API = "com.android.org.conscrypt.flags.certificate_transparency_checkservertrusted_api";
    public static final String FLAG_SPAKE2PLUS_API = "com.android.org.conscrypt.flags.spake2plus_api";

    public static boolean certificateTransparencyCheckservertrustedApi() {
        return FEATURE_FLAGS.certificateTransparencyCheckservertrustedApi();
    }

    public static boolean spake2plusApi() {
        return FEATURE_FLAGS.spake2plusApi();
    }
}
