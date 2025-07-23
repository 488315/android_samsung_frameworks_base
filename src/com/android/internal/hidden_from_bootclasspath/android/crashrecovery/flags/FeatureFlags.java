package com.android.internal.hidden_from_bootclasspath.android.crashrecovery.flags;

/* loaded from: classes5.dex */
public interface FeatureFlags {
    boolean allowRescuePartyFlagResets();

    boolean deprecateFlagsAndSettingsResets();

    boolean enableCrashrecovery();

    boolean recoverabilityDetection();

    boolean refactorCrashrecovery();

    boolean synchronousRebootInRescueParty();
}
