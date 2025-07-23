package com.android.internal.hidden_from_bootclasspath.android.hardware.devicestate.feature.flags;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes5.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_DEVICE_STATE_CONFIGURATION_FLAG, Flags.FLAG_DEVICE_STATE_PROPERTY_API, Flags.FLAG_DEVICE_STATE_PROPERTY_MIGRATION, Flags.FLAG_DEVICE_STATE_RDM_V2, Flags.FLAG_DEVICE_STATE_REQUESTER_CANCEL_STATE, ""));

    private boolean isOptimizationEnabled() {
        return false;
    }

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.hardware.devicestate.feature.flags.FeatureFlags
    public boolean deviceStateConfigurationFlag() {
        return getValue(Flags.FLAG_DEVICE_STATE_CONFIGURATION_FLAG, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.hardware.devicestate.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).deviceStateConfigurationFlag();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.hardware.devicestate.feature.flags.FeatureFlags
    public boolean deviceStatePropertyApi() {
        return getValue(Flags.FLAG_DEVICE_STATE_PROPERTY_API, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.hardware.devicestate.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).deviceStatePropertyApi();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.hardware.devicestate.feature.flags.FeatureFlags
    public boolean deviceStatePropertyMigration() {
        return getValue(Flags.FLAG_DEVICE_STATE_PROPERTY_MIGRATION, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.hardware.devicestate.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).deviceStatePropertyMigration();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.hardware.devicestate.feature.flags.FeatureFlags
    public boolean deviceStateRdmV2() {
        return getValue(Flags.FLAG_DEVICE_STATE_RDM_V2, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.hardware.devicestate.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).deviceStateRdmV2();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.hardware.devicestate.feature.flags.FeatureFlags
    public boolean deviceStateRequesterCancelState() {
        return getValue(Flags.FLAG_DEVICE_STATE_REQUESTER_CANCEL_STATE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.hardware.devicestate.feature.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).deviceStateRequesterCancelState();
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
        return Arrays.asList(Flags.FLAG_DEVICE_STATE_CONFIGURATION_FLAG, Flags.FLAG_DEVICE_STATE_PROPERTY_API, Flags.FLAG_DEVICE_STATE_PROPERTY_MIGRATION, Flags.FLAG_DEVICE_STATE_RDM_V2, Flags.FLAG_DEVICE_STATE_REQUESTER_CANCEL_STATE);
    }
}
