package com.android.internal.hidden_from_bootclasspath.com.android.libcore;

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
    private Map<String, Integer> mFinalizedFlags = new HashMap(Map.ofEntries(Map.entry(Flags.FLAG_HPKE_V_APIS, 35), Map.entry(Flags.FLAG_V_APIS, 35), Map.entry("", Integer.MAX_VALUE)));

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.libcore.FeatureFlags
    public boolean appinfo() {
        return getValue(Flags.FLAG_APPINFO, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.libcore.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).appinfo();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.libcore.FeatureFlags
    public boolean hpkePublicApi() {
        return getValue(Flags.FLAG_HPKE_PUBLIC_API, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.libcore.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).hpkePublicApi();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.libcore.FeatureFlags
    public boolean hpkeVApis() {
        return getValue(Flags.FLAG_HPKE_V_APIS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.libcore.CustomFeatureFlags$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).hpkeVApis();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.libcore.FeatureFlags
    public boolean madviseApi() {
        return getValue(Flags.FLAG_MADVISE_API, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.libcore.CustomFeatureFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).madviseApi();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.libcore.FeatureFlags
    public boolean nativeMetrics() {
        return getValue(Flags.FLAG_NATIVE_METRICS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.libcore.CustomFeatureFlags$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).nativeMetrics();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.libcore.FeatureFlags
    public boolean openjdk21Stringconcat() {
        return getValue(Flags.FLAG_OPENJDK21_STRINGCONCAT, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.libcore.CustomFeatureFlags$$ExternalSyntheticLambda10
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).openjdk21Stringconcat();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.libcore.FeatureFlags
    public boolean openjdk21V1Apis() {
        return getValue(Flags.FLAG_OPENJDK_21_V1_APIS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.libcore.CustomFeatureFlags$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).openjdk21V1Apis();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.libcore.FeatureFlags
    public boolean openjdk21V2Apis() {
        return getValue(Flags.FLAG_OPENJDK_21_V2_APIS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.libcore.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).openjdk21V2Apis();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.libcore.FeatureFlags
    public boolean postCleanupApis() {
        return getValue(Flags.FLAG_POST_CLEANUP_APIS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.libcore.CustomFeatureFlags$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).postCleanupApis();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.libcore.FeatureFlags
    public boolean readOnlyDynamicCodeLoad() {
        return getValue(Flags.FLAG_READ_ONLY_DYNAMIC_CODE_LOAD, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.libcore.CustomFeatureFlags$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).readOnlyDynamicCodeLoad();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.libcore.FeatureFlags
    public boolean vApis() {
        return getValue(Flags.FLAG_V_APIS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.libcore.CustomFeatureFlags$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).vApis();
            }
        });
    }

    protected boolean getValue(String str, Predicate<FeatureFlags> predicate) {
        return this.mGetValueImpl.test(str, predicate);
    }

    public List<String> getFlagNames() {
        return Arrays.asList(Flags.FLAG_APPINFO, Flags.FLAG_HPKE_PUBLIC_API, Flags.FLAG_HPKE_V_APIS, Flags.FLAG_MADVISE_API, Flags.FLAG_NATIVE_METRICS, Flags.FLAG_OPENJDK21_STRINGCONCAT, Flags.FLAG_OPENJDK_21_V1_APIS, Flags.FLAG_OPENJDK_21_V2_APIS, Flags.FLAG_POST_CLEANUP_APIS, Flags.FLAG_READ_ONLY_DYNAMIC_CODE_LOAD, Flags.FLAG_V_APIS);
    }

    public boolean isFlagFinalized(String str) {
        return this.mFinalizedFlags.containsKey(str) && Build.VERSION.SDK_INT >= this.mFinalizedFlags.get(str).intValue();
    }
}
