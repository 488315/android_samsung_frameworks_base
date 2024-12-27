package android.adaptiveauth;

public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_ENABLE_ADAPTIVE_AUTH =
            "android.adaptiveauth.enable_adaptive_auth";
    public static final String FLAG_REPORT_BIOMETRIC_AUTH_ATTEMPTS =
            "android.adaptiveauth.report_biometric_auth_attempts";

    public static boolean enableAdaptiveAuth() {
        return FEATURE_FLAGS.enableAdaptiveAuth();
    }

    public static boolean reportBiometricAuthAttempts() {
        return FEATURE_FLAGS.reportBiometricAuthAttempts();
    }
}
