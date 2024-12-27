package android.adaptiveauth;

public interface FeatureFlags {
    boolean enableAdaptiveAuth();

    boolean reportBiometricAuthAttempts();
}
