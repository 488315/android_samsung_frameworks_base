package com.android.media.codec.flags;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet =
            new HashSet(
                    Arrays.asList(
                            Flags.FLAG_AIDL_HAL,
                            Flags.FLAG_CODEC_IMPORTANCE,
                            Flags.FLAG_LARGE_AUDIO_FRAME,
                            ""));

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> getValueImpl) {
        this.mGetValueImpl = getValueImpl;
    }

    @Override // com.android.media.codec.flags.FeatureFlags
    public boolean aidlHal() {
        return getValue(
                Flags.FLAG_AIDL_HAL,
                new Predicate() { // from class:
                                  // com.android.media.codec.flags.CustomFeatureFlags$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).aidlHal();
                    }
                });
    }

    @Override // com.android.media.codec.flags.FeatureFlags
    public boolean codecImportance() {
        return getValue(
                Flags.FLAG_CODEC_IMPORTANCE,
                new Predicate() { // from class:
                                  // com.android.media.codec.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).codecImportance();
                    }
                });
    }

    @Override // com.android.media.codec.flags.FeatureFlags
    public boolean largeAudioFrame() {
        return getValue(
                Flags.FLAG_LARGE_AUDIO_FRAME,
                new Predicate() { // from class:
                                  // com.android.media.codec.flags.CustomFeatureFlags$$ExternalSyntheticLambda2
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).largeAudioFrame();
                    }
                });
    }

    public boolean isFlagReadOnlyOptimized(String flagName) {
        if (this.mReadOnlyFlagsSet.contains(flagName) && isOptimizationEnabled()) {
            return true;
        }
        return false;
    }

    private boolean isOptimizationEnabled() {
        return false;
    }

    protected boolean getValue(String flagName, Predicate<FeatureFlags> getter) {
        return this.mGetValueImpl.test(flagName, getter);
    }

    public List<String> getFlagNames() {
        return Arrays.asList(
                Flags.FLAG_AIDL_HAL, Flags.FLAG_CODEC_IMPORTANCE, Flags.FLAG_LARGE_AUDIO_FRAME);
    }
}
