package com.android.internal.hidden_from_bootclasspath.android.credentials.flags;

public interface FeatureFlags {
    boolean clearCredentialsFixEnabled();

    boolean clearSessionEnabled();

    boolean configurableSelectorUiEnabled();

    boolean credmanBiometricApiEnabled();

    boolean hybridFilterOptFixEnabled();

    boolean instantAppsEnabled();

    boolean newFrameworkMetrics();

    boolean newSettingsIntents();

    boolean newSettingsUi();

    boolean selectorUiImprovementsEnabled();

    boolean settingsActivityEnabled();

    boolean wearCredentialManagerEnabled();
}
