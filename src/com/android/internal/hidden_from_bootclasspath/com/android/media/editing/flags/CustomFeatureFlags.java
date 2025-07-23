package com.android.internal.hidden_from_bootclasspath.com.android.media.editing.flags;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes5.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_ADD_MEDIA_METRICS_EDITING, Flags.FLAG_MUXER_MP4_ENABLE_APV, Flags.FLAG_STAGEFRIGHTRECORDER_ENABLE_B_FRAMES, ""));

    private boolean isOptimizationEnabled() {
        return false;
    }

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.media.editing.flags.FeatureFlags
    public boolean addMediaMetricsEditing() {
        return getValue(Flags.FLAG_ADD_MEDIA_METRICS_EDITING, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.media.editing.flags.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).addMediaMetricsEditing();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.media.editing.flags.FeatureFlags
    public boolean muxerMp4EnableApv() {
        return getValue(Flags.FLAG_MUXER_MP4_ENABLE_APV, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.media.editing.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).muxerMp4EnableApv();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.media.editing.flags.FeatureFlags
    public boolean stagefrightrecorderEnableBFrames() {
        return getValue(Flags.FLAG_STAGEFRIGHTRECORDER_ENABLE_B_FRAMES, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.media.editing.flags.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).stagefrightrecorderEnableBFrames();
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
        return Arrays.asList(Flags.FLAG_ADD_MEDIA_METRICS_EDITING, Flags.FLAG_MUXER_MP4_ENABLE_APV, Flags.FLAG_STAGEFRIGHTRECORDER_ENABLE_B_FRAMES);
    }
}
