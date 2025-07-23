package com.android.internal.hidden_from_bootclasspath.android.app.smartspace.flags;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes5.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_ACCESS_SMARTSPACE, Flags.FLAG_REMOTE_VIEWS, ""));

    private boolean isOptimizationEnabled() {
        return false;
    }

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.smartspace.flags.FeatureFlags
    public boolean accessSmartspace() {
        return getValue(Flags.FLAG_ACCESS_SMARTSPACE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.app.smartspace.flags.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).accessSmartspace();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.smartspace.flags.FeatureFlags
    public boolean remoteViews() {
        return getValue(Flags.FLAG_REMOTE_VIEWS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.app.smartspace.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).remoteViews();
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
        return Arrays.asList(Flags.FLAG_ACCESS_SMARTSPACE, Flags.FLAG_REMOTE_VIEWS);
    }
}
