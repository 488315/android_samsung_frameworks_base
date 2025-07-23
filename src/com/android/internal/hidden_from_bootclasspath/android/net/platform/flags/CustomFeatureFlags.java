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
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_DEPRECATE_NETWORK_POLICY_CALLBACK, Flags.FLAG_MDNS_IMPROVEMENT_FOR_25Q2, Flags.FLAG_POWERED_OFF_FINDING_PLATFORM, Flags.FLAG_REGISTER_NSD_OFFLOAD_ENGINE, Flags.FLAG_VPN_TYPE_OEM_SERVICE_AND_LEGACY, ""));

    private boolean isOptimizationEnabled() {
        return false;
    }

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.net.platform.flags.FeatureFlags
    public boolean deprecateNetworkPolicyCallback() {
        return getValue(Flags.FLAG_DEPRECATE_NETWORK_POLICY_CALLBACK, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.net.platform.flags.CustomFeatureFlags$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).deprecateNetworkPolicyCallback();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.net.platform.flags.FeatureFlags
    public boolean mdnsImprovementFor25q2() {
        return getValue(Flags.FLAG_MDNS_IMPROVEMENT_FOR_25Q2, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.net.platform.flags.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).mdnsImprovementFor25q2();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.net.platform.flags.FeatureFlags
    public boolean poweredOffFindingPlatform() {
        return getValue(Flags.FLAG_POWERED_OFF_FINDING_PLATFORM, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.net.platform.flags.CustomFeatureFlags$$ExternalSyntheticLambda2
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

    @Override // com.android.internal.hidden_from_bootclasspath.android.net.platform.flags.FeatureFlags
    public boolean vpnTypeOemServiceAndLegacy() {
        return getValue(Flags.FLAG_VPN_TYPE_OEM_SERVICE_AND_LEGACY, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.net.platform.flags.CustomFeatureFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).vpnTypeOemServiceAndLegacy();
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
        return Arrays.asList(Flags.FLAG_DEPRECATE_NETWORK_POLICY_CALLBACK, Flags.FLAG_MDNS_IMPROVEMENT_FOR_25Q2, Flags.FLAG_POWERED_OFF_FINDING_PLATFORM, Flags.FLAG_REGISTER_NSD_OFFLOAD_ENGINE, Flags.FLAG_VPN_TYPE_OEM_SERVICE_AND_LEGACY);
    }
}
