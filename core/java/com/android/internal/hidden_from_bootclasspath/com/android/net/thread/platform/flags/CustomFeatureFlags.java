package com.android.internal.hidden_from_bootclasspath.com.android.net.thread.platform.flags;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes5.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_THREAD_ENABLED_PLATFORM, Flags.FLAG_THREAD_USER_RESTRICTION_ENABLED, ""));

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> getValueImpl) {
        this.mGetValueImpl = getValueImpl;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.net.thread.platform.flags.FeatureFlags
    public boolean threadEnabledPlatform() {
        return getValue(Flags.FLAG_THREAD_ENABLED_PLATFORM, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.net.thread.platform.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).threadEnabledPlatform();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.net.thread.platform.flags.FeatureFlags
    public boolean threadUserRestrictionEnabled() {
        return getValue(Flags.FLAG_THREAD_USER_RESTRICTION_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.net.thread.platform.flags.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).threadUserRestrictionEnabled();
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
        return Arrays.asList(Flags.FLAG_THREAD_ENABLED_PLATFORM, Flags.FLAG_THREAD_USER_RESTRICTION_ENABLED);
    }
}
