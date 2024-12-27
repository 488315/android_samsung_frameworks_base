package android.app.contextualsearch.flags;

/* loaded from: classes.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_ENABLE_SERVICE =
            "android.app.contextualsearch.flags.enable_service";
    public static final String FLAG_ENABLE_TOKEN_REFRESH =
            "android.app.contextualsearch.flags.enable_token_refresh";

    public static boolean enableService() {
        return FEATURE_FLAGS.enableService();
    }

    public static boolean enableTokenRefresh() {
        return FEATURE_FLAGS.enableTokenRefresh();
    }
}
