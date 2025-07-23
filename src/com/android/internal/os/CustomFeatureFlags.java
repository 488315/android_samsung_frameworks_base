package com.android.internal.os;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes5.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_APPLICATION_SHARED_MEMORY_ENABLED, Flags.FLAG_DEBUG_STORE_ENABLED, Flags.FLAG_ENABLE_APACHE_HTTP_LEGACY_PRELOAD, Flags.FLAG_ENABLE_MEDIA_AND_LOCATION_PRELOAD, Flags.FLAG_RAVENWOOD_FLAG_RO_1, Flags.FLAG_RAVENWOOD_FLAG_RO_2, Flags.FLAG_RAVENWOOD_FLAG_RW_1, Flags.FLAG_RAVENWOOD_FLAG_RW_2, Flags.FLAG_USE_TRANSACTION_CODES_FOR_UNKNOWN_METHODS, ""));

    private boolean isOptimizationEnabled() {
        return false;
    }

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // com.android.internal.os.FeatureFlags
    public boolean applicationSharedMemoryEnabled() {
        return getValue(Flags.FLAG_APPLICATION_SHARED_MEMORY_ENABLED, new Predicate() { // from class: com.android.internal.os.CustomFeatureFlags$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).applicationSharedMemoryEnabled();
            }
        });
    }

    @Override // com.android.internal.os.FeatureFlags
    public boolean debugStoreEnabled() {
        return getValue(Flags.FLAG_DEBUG_STORE_ENABLED, new Predicate() { // from class: com.android.internal.os.CustomFeatureFlags$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).debugStoreEnabled();
            }
        });
    }

    @Override // com.android.internal.os.FeatureFlags
    public boolean enableApacheHttpLegacyPreload() {
        return getValue(Flags.FLAG_ENABLE_APACHE_HTTP_LEGACY_PRELOAD, new Predicate() { // from class: com.android.internal.os.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableApacheHttpLegacyPreload();
            }
        });
    }

    @Override // com.android.internal.os.FeatureFlags
    public boolean enableMediaAndLocationPreload() {
        return getValue(Flags.FLAG_ENABLE_MEDIA_AND_LOCATION_PRELOAD, new Predicate() { // from class: com.android.internal.os.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableMediaAndLocationPreload();
            }
        });
    }

    @Override // com.android.internal.os.FeatureFlags
    public boolean ravenwoodFlagRo1() {
        return getValue(Flags.FLAG_RAVENWOOD_FLAG_RO_1, new Predicate() { // from class: com.android.internal.os.CustomFeatureFlags$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).ravenwoodFlagRo1();
            }
        });
    }

    @Override // com.android.internal.os.FeatureFlags
    public boolean ravenwoodFlagRo2() {
        return getValue(Flags.FLAG_RAVENWOOD_FLAG_RO_2, new Predicate() { // from class: com.android.internal.os.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).ravenwoodFlagRo2();
            }
        });
    }

    @Override // com.android.internal.os.FeatureFlags
    public boolean ravenwoodFlagRw1() {
        return getValue(Flags.FLAG_RAVENWOOD_FLAG_RW_1, new Predicate() { // from class: com.android.internal.os.CustomFeatureFlags$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).ravenwoodFlagRw1();
            }
        });
    }

    @Override // com.android.internal.os.FeatureFlags
    public boolean ravenwoodFlagRw2() {
        return getValue(Flags.FLAG_RAVENWOOD_FLAG_RW_2, new Predicate() { // from class: com.android.internal.os.CustomFeatureFlags$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).ravenwoodFlagRw2();
            }
        });
    }

    @Override // com.android.internal.os.FeatureFlags
    public boolean useTransactionCodesForUnknownMethods() {
        return getValue(Flags.FLAG_USE_TRANSACTION_CODES_FOR_UNKNOWN_METHODS, new Predicate() { // from class: com.android.internal.os.CustomFeatureFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).useTransactionCodesForUnknownMethods();
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
        return Arrays.asList(Flags.FLAG_APPLICATION_SHARED_MEMORY_ENABLED, Flags.FLAG_DEBUG_STORE_ENABLED, Flags.FLAG_ENABLE_APACHE_HTTP_LEGACY_PRELOAD, Flags.FLAG_ENABLE_MEDIA_AND_LOCATION_PRELOAD, Flags.FLAG_RAVENWOOD_FLAG_RO_1, Flags.FLAG_RAVENWOOD_FLAG_RO_2, Flags.FLAG_RAVENWOOD_FLAG_RW_1, Flags.FLAG_RAVENWOOD_FLAG_RW_2, Flags.FLAG_USE_TRANSACTION_CODES_FOR_UNKNOWN_METHODS);
    }
}
