package com.android.media.codec.flags;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes6.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_AIDL_HAL, Flags.FLAG_CODEC_IMPORTANCE, Flags.FLAG_LARGE_AUDIO_FRAME, ""));

    private boolean isOptimizationEnabled() {
        return false;
    }

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // com.android.media.codec.flags.FeatureFlags
    public boolean aidlHal() {
        return getValue(Flags.FLAG_AIDL_HAL, new Predicate() { // from class: com.android.media.codec.flags.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).aidlHal();
            }
        });
    }

    @Override // com.android.media.codec.flags.FeatureFlags
    public boolean codecImportance() {
        return getValue(Flags.FLAG_CODEC_IMPORTANCE, new Predicate() { // from class: com.android.media.codec.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).codecImportance();
            }
        });
    }

    @Override // com.android.media.codec.flags.FeatureFlags
    public boolean largeAudioFrame() {
        return getValue(Flags.FLAG_LARGE_AUDIO_FRAME, new Predicate() { // from class: com.android.media.codec.flags.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).largeAudioFrame();
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
        return Arrays.asList(Flags.FLAG_AIDL_HAL, Flags.FLAG_CODEC_IMPORTANCE, Flags.FLAG_LARGE_AUDIO_FRAME);
    }
}
