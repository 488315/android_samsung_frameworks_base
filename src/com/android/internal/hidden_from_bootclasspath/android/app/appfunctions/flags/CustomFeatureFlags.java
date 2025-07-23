package com.android.internal.hidden_from_bootclasspath.android.app.appfunctions.flags;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes5.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_ENABLE_APP_FUNCTION_MANAGER, ""));

    private boolean isOptimizationEnabled() {
        return false;
    }

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.appfunctions.flags.FeatureFlags
    public boolean enableAppFunctionManager() {
        return getValue(Flags.FLAG_ENABLE_APP_FUNCTION_MANAGER, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.app.appfunctions.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableAppFunctionManager();
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
        return Arrays.asList(Flags.FLAG_ENABLE_APP_FUNCTION_MANAGER);
    }
}
