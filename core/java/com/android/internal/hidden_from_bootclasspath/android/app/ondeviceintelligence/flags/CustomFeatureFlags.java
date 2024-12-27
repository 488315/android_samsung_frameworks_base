package com.android.internal.hidden_from_bootclasspath.android.app.ondeviceintelligence.flags;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet =
            new HashSet(Arrays.asList(Flags.FLAG_ENABLE_ON_DEVICE_INTELLIGENCE, ""));

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> getValueImpl) {
        this.mGetValueImpl = getValueImpl;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.ondeviceintelligence.flags.FeatureFlags
    public boolean enableOnDeviceIntelligence() {
        return getValue(
                Flags.FLAG_ENABLE_ON_DEVICE_INTELLIGENCE,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.app.ondeviceintelligence.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableOnDeviceIntelligence();
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
        return Arrays.asList(Flags.FLAG_ENABLE_ON_DEVICE_INTELLIGENCE);
    }
}
