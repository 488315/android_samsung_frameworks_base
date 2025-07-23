package com.android.internal.hidden_from_bootclasspath.com.android.ranging.flags;

import android.os.Build;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes5.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(""));
    private Map<String, Integer> mFinalizedFlags = new HashMap(Map.ofEntries(Map.entry(Flags.FLAG_RANGING_CS_ENABLED, 36), Map.entry(Flags.FLAG_RANGING_STACK_ENABLED, 36), Map.entry("", Integer.MAX_VALUE)));

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.ranging.flags.FeatureFlags
    public boolean rangingCsEnabled() {
        return getValue(Flags.FLAG_RANGING_CS_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.ranging.flags.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).rangingCsEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.ranging.flags.FeatureFlags
    public boolean rangingRttEnabled() {
        return getValue(Flags.FLAG_RANGING_RTT_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.ranging.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).rangingRttEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.ranging.flags.FeatureFlags
    public boolean rangingStackEnabled() {
        return getValue(Flags.FLAG_RANGING_STACK_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.ranging.flags.CustomFeatureFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).rangingStackEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.ranging.flags.FeatureFlags
    public boolean rangingStackUpdates25q4() {
        return getValue(Flags.FLAG_RANGING_STACK_UPDATES_25Q4, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.ranging.flags.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).rangingStackUpdates25q4();
            }
        });
    }

    protected boolean getValue(String str, Predicate<FeatureFlags> predicate) {
        return this.mGetValueImpl.test(str, predicate);
    }

    public List<String> getFlagNames() {
        return Arrays.asList(Flags.FLAG_RANGING_CS_ENABLED, Flags.FLAG_RANGING_RTT_ENABLED, Flags.FLAG_RANGING_STACK_ENABLED, Flags.FLAG_RANGING_STACK_UPDATES_25Q4);
    }

    public boolean isFlagFinalized(String str) {
        return this.mFinalizedFlags.containsKey(str) && Build.VERSION.SDK_INT >= this.mFinalizedFlags.get(str).intValue();
    }
}
