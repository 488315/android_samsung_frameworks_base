package com.android.internal.hidden_from_bootclasspath.android.hardware.radio;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes5.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_HD_RADIO_EMERGENCY_ALERT_SYSTEM, Flags.FLAG_HD_RADIO_IMPROVED, ""));

    private boolean isOptimizationEnabled() {
        return false;
    }

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.hardware.radio.FeatureFlags
    public boolean hdRadioEmergencyAlertSystem() {
        return getValue(Flags.FLAG_HD_RADIO_EMERGENCY_ALERT_SYSTEM, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.hardware.radio.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).hdRadioEmergencyAlertSystem();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.hardware.radio.FeatureFlags
    public boolean hdRadioImproved() {
        return getValue(Flags.FLAG_HD_RADIO_IMPROVED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.hardware.radio.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).hdRadioImproved();
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
        return Arrays.asList(Flags.FLAG_HD_RADIO_EMERGENCY_ALERT_SYSTEM, Flags.FLAG_HD_RADIO_IMPROVED);
    }
}
