package com.android.internal.hidden_from_bootclasspath.android.net.platform.flags;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes5.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_IPSEC_TRANSFORM_STATE, Flags.FLAG_POWERED_OFF_FINDING_PLATFORM, Flags.FLAG_REGISTER_NSD_OFFLOAD_ENGINE, ""));

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> getValueImpl) {
        this.mGetValueImpl = getValueImpl;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.net.platform.flags.FeatureFlags
    public boolean ipsecTransformState() {
        return getValue(Flags.FLAG_IPSEC_TRANSFORM_STATE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.net.platform.flags.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).ipsecTransformState();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.net.platform.flags.FeatureFlags
    public boolean poweredOffFindingPlatform() {
        return getValue(Flags.FLAG_POWERED_OFF_FINDING_PLATFORM, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.net.platform.flags.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).poweredOffFindingPlatform();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.net.platform.flags.FeatureFlags
    public boolean registerNsdOffloadEngine() {
        return getValue(Flags.FLAG_REGISTER_NSD_OFFLOAD_ENGINE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.net.platform.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).registerNsdOffloadEngine();
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
        return Arrays.asList(Flags.FLAG_IPSEC_TRANSFORM_STATE, Flags.FLAG_POWERED_OFF_FINDING_PLATFORM, Flags.FLAG_REGISTER_NSD_OFFLOAD_ENGINE);
    }
}
