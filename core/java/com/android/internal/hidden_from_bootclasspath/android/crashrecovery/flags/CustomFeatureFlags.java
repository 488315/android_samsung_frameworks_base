package com.android.internal.hidden_from_bootclasspath.android.crashrecovery.flags;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes5.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet =
            new HashSet(
                    Arrays.asList(
                            Flags.FLAG_ALLOW_RESCUE_PARTY_FLAG_RESETS,
                            Flags.FLAG_ENABLE_CRASHRECOVERY,
                            Flags.FLAG_RECOVERABILITY_DETECTION,
                            Flags.FLAG_REENABLE_SETTINGS_RESETS,
                            ""));

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> getValueImpl) {
        this.mGetValueImpl = getValueImpl;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.crashrecovery.flags.FeatureFlags
    public boolean allowRescuePartyFlagResets() {
        return getValue(
                Flags.FLAG_ALLOW_RESCUE_PARTY_FLAG_RESETS,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.crashrecovery.flags.CustomFeatureFlags$$ExternalSyntheticLambda3
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).allowRescuePartyFlagResets();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.crashrecovery.flags.FeatureFlags
    public boolean enableCrashrecovery() {
        return getValue(
                Flags.FLAG_ENABLE_CRASHRECOVERY,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.crashrecovery.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableCrashrecovery();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.crashrecovery.flags.FeatureFlags
    public boolean recoverabilityDetection() {
        return getValue(
                Flags.FLAG_RECOVERABILITY_DETECTION,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.crashrecovery.flags.CustomFeatureFlags$$ExternalSyntheticLambda2
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).recoverabilityDetection();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.crashrecovery.flags.FeatureFlags
    public boolean reenableSettingsResets() {
        return getValue(
                Flags.FLAG_REENABLE_SETTINGS_RESETS,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.crashrecovery.flags.CustomFeatureFlags$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).reenableSettingsResets();
                    }
                });
    }

    public boolean isFlagReadOnlyOptimized(String flagName) {
        if (this.mReadOnlyFlagsSet.contains(flagName) && isOptimizationEnabled()) {
            return true;
        }
        return false;
    }

    private boolean isOptimizationEnabled() {
        return false;
    }

    protected boolean getValue(String flagName, Predicate<FeatureFlags> getter) {
        return this.mGetValueImpl.test(flagName, getter);
    }

    public List<String> getFlagNames() {
        return Arrays.asList(
                Flags.FLAG_ALLOW_RESCUE_PARTY_FLAG_RESETS,
                Flags.FLAG_ENABLE_CRASHRECOVERY,
                Flags.FLAG_RECOVERABILITY_DETECTION,
                Flags.FLAG_REENABLE_SETTINGS_RESETS);
    }
}
