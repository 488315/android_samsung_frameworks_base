package com.android.internal.hidden_from_bootclasspath.android.crashrecovery.flags;

public interface FeatureFlags {
    boolean allowRescuePartyFlagResets();

    boolean enableCrashrecovery();

    boolean recoverabilityDetection();

    boolean reenableSettingsResets();
}
