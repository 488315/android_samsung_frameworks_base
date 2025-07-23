package com.android.internal.hidden_from_bootclasspath.android.content.flags;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes5.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_ENABLE_BIND_PACKAGE_ISOLATED_PROCESS, Flags.FLAG_INTENT_SAVE_TO_XML_PACKAGE, ""));

    private boolean isOptimizationEnabled() {
        return false;
    }

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.content.flags.FeatureFlags
    public boolean enableBindPackageIsolatedProcess() {
        return getValue(Flags.FLAG_ENABLE_BIND_PACKAGE_ISOLATED_PROCESS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.content.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableBindPackageIsolatedProcess();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.content.flags.FeatureFlags
    public boolean intentSaveToXmlPackage() {
        return getValue(Flags.FLAG_INTENT_SAVE_TO_XML_PACKAGE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.content.flags.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).intentSaveToXmlPackage();
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
        return Arrays.asList(Flags.FLAG_ENABLE_BIND_PACKAGE_ISOLATED_PROCESS, Flags.FLAG_INTENT_SAVE_TO_XML_PACKAGE);
    }
}
