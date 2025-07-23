package com.android.libcore.readonly;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes6.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_NATIVE_METRICS, Flags.FLAG_POST_CLEANUP_APIS, ""));

    private boolean isOptimizationEnabled() {
        return false;
    }

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // com.android.libcore.readonly.FeatureFlags
    public boolean nativeMetrics() {
        return getValue(Flags.FLAG_NATIVE_METRICS, new Predicate() { // from class: com.android.libcore.readonly.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).nativeMetrics();
            }
        });
    }

    @Override // com.android.libcore.readonly.FeatureFlags
    public boolean postCleanupApis() {
        return getValue(Flags.FLAG_POST_CLEANUP_APIS, new Predicate() { // from class: com.android.libcore.readonly.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).postCleanupApis();
            }
        });
    }

    public boolean isFlagReadOnlyOptimized(String str) {
        return this.mReadOnlyFlagsSet.contains(str) && isOptimizationEnabled();
    }

    protected boolean getValue(String str, Predicate<FeatureFlags> predicate) {
        return this.mGetValueImpl.test(str, predicate);
    }

    public List<String> getFlagNames() {
        return Arrays.asList(Flags.FLAG_NATIVE_METRICS, Flags.FLAG_POST_CLEANUP_APIS);
    }
}
