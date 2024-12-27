package com.android.internal.hidden_from_bootclasspath.android.media.tv.flags;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes5.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_BROADCAST_VISIBILITY_TYPES, Flags.FLAG_ENABLE_AD_SERVICE_FW, Flags.FLAG_TIAF_V_APIS, ""));

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> getValueImpl) {
        this.mGetValueImpl = getValueImpl;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.media.tv.flags.FeatureFlags
    public boolean broadcastVisibilityTypes() {
        return getValue(Flags.FLAG_BROADCAST_VISIBILITY_TYPES, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.media.tv.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).broadcastVisibilityTypes();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.media.tv.flags.FeatureFlags
    public boolean enableAdServiceFw() {
        return getValue(Flags.FLAG_ENABLE_AD_SERVICE_FW, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.media.tv.flags.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableAdServiceFw();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.media.tv.flags.FeatureFlags
    public boolean tiafVApis() {
        return getValue(Flags.FLAG_TIAF_V_APIS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.media.tv.flags.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).tiafVApis();
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
        return Arrays.asList(Flags.FLAG_BROADCAST_VISIBILITY_TYPES, Flags.FLAG_ENABLE_AD_SERVICE_FW, Flags.FLAG_TIAF_V_APIS);
    }
}
