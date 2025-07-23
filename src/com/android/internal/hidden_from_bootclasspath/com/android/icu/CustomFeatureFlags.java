package com.android.internal.hidden_from_bootclasspath.com.android.icu;

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
    private Map<String, Integer> mFinalizedFlags = new HashMap(Map.ofEntries(Map.entry(Flags.FLAG_ICU_V_API, 35), Map.entry("", Integer.MAX_VALUE)));

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.icu.FeatureFlags
    public boolean icu25q2Api() {
        return getValue(Flags.FLAG_ICU_25Q2_API, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.icu.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).icu25q2Api();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.icu.FeatureFlags
    public boolean icuVApi() {
        return getValue(Flags.FLAG_ICU_V_API, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.icu.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).icuVApi();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.icu.FeatureFlags
    public boolean telephonyLookupMccExtension() {
        return getValue(Flags.FLAG_TELEPHONY_LOOKUP_MCC_EXTENSION, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.icu.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).telephonyLookupMccExtension();
            }
        });
    }

    protected boolean getValue(String str, Predicate<FeatureFlags> predicate) {
        return this.mGetValueImpl.test(str, predicate);
    }

    public List<String> getFlagNames() {
        return Arrays.asList(Flags.FLAG_ICU_25Q2_API, Flags.FLAG_ICU_V_API, Flags.FLAG_TELEPHONY_LOOKUP_MCC_EXTENSION);
    }

    public boolean isFlagFinalized(String str) {
        return this.mFinalizedFlags.containsKey(str) && Build.VERSION.SDK_INT >= this.mFinalizedFlags.get(str).intValue();
    }
}
