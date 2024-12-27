package com.android.server.power.feature.flags;

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
                            Flags.FLAG_ENABLE_EARLY_SCREEN_TIMEOUT_DETECTOR,
                            Flags.FLAG_IMPROVE_WAKELOCK_LATENCY,
                            ""));

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> getValueImpl) {
        this.mGetValueImpl = getValueImpl;
    }

    @Override // com.android.server.power.feature.flags.FeatureFlags
    public boolean enableEarlyScreenTimeoutDetector() {
        return getValue(
                Flags.FLAG_ENABLE_EARLY_SCREEN_TIMEOUT_DETECTOR,
                new Predicate() { // from class:
                                  // com.android.server.power.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableEarlyScreenTimeoutDetector();
                    }
                });
    }

    @Override // com.android.server.power.feature.flags.FeatureFlags
    public boolean improveWakelockLatency() {
        return getValue(
                Flags.FLAG_IMPROVE_WAKELOCK_LATENCY,
                new Predicate() { // from class:
                                  // com.android.server.power.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).improveWakelockLatency();
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
                Flags.FLAG_ENABLE_EARLY_SCREEN_TIMEOUT_DETECTOR,
                Flags.FLAG_IMPROVE_WAKELOCK_LATENCY);
    }
}
