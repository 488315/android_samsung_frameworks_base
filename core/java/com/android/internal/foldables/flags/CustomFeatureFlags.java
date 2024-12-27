package com.android.internal.foldables.flags;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet =
            new HashSet(
                    Arrays.asList(
                            Flags.FLAG_FOLD_GRACE_PERIOD_ENABLED,
                            Flags.FLAG_FOLD_LOCK_SETTING_ENABLED,
                            ""));

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> getValueImpl) {
        this.mGetValueImpl = getValueImpl;
    }

    @Override // com.android.internal.foldables.flags.FeatureFlags
    public boolean foldGracePeriodEnabled() {
        return getValue(
                Flags.FLAG_FOLD_GRACE_PERIOD_ENABLED,
                new Predicate() { // from class:
                                  // com.android.internal.foldables.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).foldGracePeriodEnabled();
                    }
                });
    }

    @Override // com.android.internal.foldables.flags.FeatureFlags
    public boolean foldLockSettingEnabled() {
        return getValue(
                Flags.FLAG_FOLD_LOCK_SETTING_ENABLED,
                new Predicate() { // from class:
                                  // com.android.internal.foldables.flags.CustomFeatureFlags$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).foldLockSettingEnabled();
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
                Flags.FLAG_FOLD_GRACE_PERIOD_ENABLED, Flags.FLAG_FOLD_LOCK_SETTING_ENABLED);
    }
}
