package com.android.internal.hidden_from_bootclasspath.android.net.http;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes5.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_PRELOAD_HTTPENGINE_IN_ZYGOTE, Flags.FLAG_PRELOAD_HTTPENGINE_JAVA_IMPL_CLASSES, Flags.FLAG_PRELOAD_HTTPENGINE_SHARED_LIBRARY, ""));

    private boolean isOptimizationEnabled() {
        return false;
    }

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.net.http.FeatureFlags
    public boolean preloadHttpengineInZygote() {
        return getValue(Flags.FLAG_PRELOAD_HTTPENGINE_IN_ZYGOTE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.net.http.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).preloadHttpengineInZygote();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.net.http.FeatureFlags
    public boolean preloadHttpengineJavaImplClasses() {
        return getValue(Flags.FLAG_PRELOAD_HTTPENGINE_JAVA_IMPL_CLASSES, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.net.http.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).preloadHttpengineJavaImplClasses();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.net.http.FeatureFlags
    public boolean preloadHttpengineSharedLibrary() {
        return getValue(Flags.FLAG_PRELOAD_HTTPENGINE_SHARED_LIBRARY, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.net.http.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).preloadHttpengineSharedLibrary();
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
        return Arrays.asList(Flags.FLAG_PRELOAD_HTTPENGINE_IN_ZYGOTE, Flags.FLAG_PRELOAD_HTTPENGINE_JAVA_IMPL_CLASSES, Flags.FLAG_PRELOAD_HTTPENGINE_SHARED_LIBRARY);
    }
}
