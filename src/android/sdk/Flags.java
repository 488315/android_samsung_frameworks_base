package android.sdk;

/* loaded from: classes3.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_MAJOR_MINOR_VERSIONING_SCHEME = "android.sdk.major_minor_versioning_scheme";

    public static boolean majorMinorVersioningScheme() {
        return FEATURE_FLAGS.majorMinorVersioningScheme();
    }
}
