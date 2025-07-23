package com.android.internal.hidden_from_bootclasspath.android.service.voice.flags;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes5.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_ALLOW_FOREGROUND_ACTIVITIES_IN_ON_SHOW, Flags.FLAG_ALLOW_HOTWORD_BUMP_EGRESS, Flags.FLAG_ALLOW_TRAINING_DATA_EGRESS_FROM_HDS, ""));

    private boolean isOptimizationEnabled() {
        return false;
    }

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.service.voice.flags.FeatureFlags
    public boolean allowForegroundActivitiesInOnShow() {
        return getValue(Flags.FLAG_ALLOW_FOREGROUND_ACTIVITIES_IN_ON_SHOW, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.service.voice.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).allowForegroundActivitiesInOnShow();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.service.voice.flags.FeatureFlags
    public boolean allowHotwordBumpEgress() {
        return getValue(Flags.FLAG_ALLOW_HOTWORD_BUMP_EGRESS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.service.voice.flags.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).allowHotwordBumpEgress();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.service.voice.flags.FeatureFlags
    public boolean allowTrainingDataEgressFromHds() {
        return getValue(Flags.FLAG_ALLOW_TRAINING_DATA_EGRESS_FROM_HDS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.service.voice.flags.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).allowTrainingDataEgressFromHds();
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
        return Arrays.asList(Flags.FLAG_ALLOW_FOREGROUND_ACTIVITIES_IN_ON_SHOW, Flags.FLAG_ALLOW_HOTWORD_BUMP_EGRESS, Flags.FLAG_ALLOW_TRAINING_DATA_EGRESS_FROM_HDS);
    }
}
