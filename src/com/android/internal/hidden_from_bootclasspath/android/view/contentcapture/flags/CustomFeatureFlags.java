package com.android.internal.hidden_from_bootclasspath.android.view.contentcapture.flags;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes5.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_CCAPI_BAKLAVA_ENABLED, Flags.FLAG_FLUSH_AFTER_EACH_FRAME, Flags.FLAG_RUN_ON_BACKGROUND_THREAD_ENABLED, ""));

    private boolean isOptimizationEnabled() {
        return false;
    }

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.view.contentcapture.flags.FeatureFlags
    public boolean ccapiBaklavaEnabled() {
        return getValue(Flags.FLAG_CCAPI_BAKLAVA_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.view.contentcapture.flags.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).ccapiBaklavaEnabled();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.view.contentcapture.flags.FeatureFlags
    public boolean flushAfterEachFrame() {
        return getValue(Flags.FLAG_FLUSH_AFTER_EACH_FRAME, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.view.contentcapture.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).flushAfterEachFrame();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.view.contentcapture.flags.FeatureFlags
    public boolean runOnBackgroundThreadEnabled() {
        return getValue(Flags.FLAG_RUN_ON_BACKGROUND_THREAD_ENABLED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.view.contentcapture.flags.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).runOnBackgroundThreadEnabled();
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
        return Arrays.asList(Flags.FLAG_CCAPI_BAKLAVA_ENABLED, Flags.FLAG_FLUSH_AFTER_EACH_FRAME, Flags.FLAG_RUN_ON_BACKGROUND_THREAD_ENABLED);
    }
}
