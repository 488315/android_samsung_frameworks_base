package com.android.internal.hidden_from_bootclasspath.android.provider.flags;

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
    private Map<String, Integer> mFinalizedFlags = new HashMap(Map.ofEntries(Map.entry("", Integer.MAX_VALUE)));

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.provider.flags.FeatureFlags
    public boolean deviceConfigWritableNamespacesApi() {
        return getValue(Flags.FLAG_DEVICE_CONFIG_WRITABLE_NAMESPACES_API, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.provider.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).deviceConfigWritableNamespacesApi();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.provider.flags.FeatureFlags
    public boolean dumpImprovements() {
        return getValue(Flags.FLAG_DUMP_IMPROVEMENTS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.provider.flags.CustomFeatureFlags$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).dumpImprovements();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.provider.flags.FeatureFlags
    public boolean mmdDeviceConfig() {
        return getValue(Flags.FLAG_MMD_DEVICE_CONFIG, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.provider.flags.CustomFeatureFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).mmdDeviceConfig();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.provider.flags.FeatureFlags
    public boolean newStoragePublicApi() {
        return getValue(Flags.FLAG_NEW_STORAGE_PUBLIC_API, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.provider.flags.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).newStoragePublicApi();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.provider.flags.FeatureFlags
    public boolean newStorageWriterSystemApi() {
        return getValue(Flags.FLAG_NEW_STORAGE_WRITER_SYSTEM_API, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.provider.flags.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).newStorageWriterSystemApi();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.provider.flags.FeatureFlags
    public boolean stageFlagsForBuild() {
        return getValue(Flags.FLAG_STAGE_FLAGS_FOR_BUILD, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.provider.flags.CustomFeatureFlags$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).stageFlagsForBuild();
            }
        });
    }

    protected boolean getValue(String str, Predicate<FeatureFlags> predicate) {
        return this.mGetValueImpl.test(str, predicate);
    }

    public List<String> getFlagNames() {
        return Arrays.asList(Flags.FLAG_DEVICE_CONFIG_WRITABLE_NAMESPACES_API, Flags.FLAG_DUMP_IMPROVEMENTS, Flags.FLAG_MMD_DEVICE_CONFIG, Flags.FLAG_NEW_STORAGE_PUBLIC_API, Flags.FLAG_NEW_STORAGE_WRITER_SYSTEM_API, Flags.FLAG_STAGE_FLAGS_FOR_BUILD);
    }

    public boolean isFlagFinalized(String str) {
        return this.mFinalizedFlags.containsKey(str) && Build.VERSION.SDK_INT >= this.mFinalizedFlags.get(str).intValue();
    }
}
