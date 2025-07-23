package com.android.internal.hidden_from_bootclasspath.com.android.art.flags;

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
    private Map<String, Integer> mFinalizedFlags = new HashMap(Map.ofEntries(Map.entry(Flags.FLAG_EXECUTABLE_METHOD_FILE_OFFSETS, 36), Map.entry("", Integer.MAX_VALUE)));

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.art.flags.FeatureFlags
    public boolean alwaysEnableProfileCode() {
        return getValue(Flags.FLAG_ALWAYS_ENABLE_PROFILE_CODE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.art.flags.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).alwaysEnableProfileCode();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.art.flags.FeatureFlags
    public boolean artServiceV3() {
        return getValue(Flags.FLAG_ART_SERVICE_V3, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.art.flags.CustomFeatureFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).artServiceV3();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.art.flags.FeatureFlags
    public boolean executableMethodFileOffsets() {
        return getValue(Flags.FLAG_EXECUTABLE_METHOD_FILE_OFFSETS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.art.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).executableMethodFileOffsets();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.art.flags.FeatureFlags
    public boolean executableMethodFileOffsetsV2() {
        return getValue(Flags.FLAG_EXECUTABLE_METHOD_FILE_OFFSETS_V2, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.art.flags.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).executableMethodFileOffsetsV2();
            }
        });
    }

    protected boolean getValue(String str, Predicate<FeatureFlags> predicate) {
        return this.mGetValueImpl.test(str, predicate);
    }

    public List<String> getFlagNames() {
        return Arrays.asList(Flags.FLAG_ALWAYS_ENABLE_PROFILE_CODE, Flags.FLAG_ART_SERVICE_V3, Flags.FLAG_EXECUTABLE_METHOD_FILE_OFFSETS, Flags.FLAG_EXECUTABLE_METHOD_FILE_OFFSETS_V2);
    }

    public boolean isFlagFinalized(String str) {
        return this.mFinalizedFlags.containsKey(str) && Build.VERSION.SDK_INT >= this.mFinalizedFlags.get(str).intValue();
    }
}
