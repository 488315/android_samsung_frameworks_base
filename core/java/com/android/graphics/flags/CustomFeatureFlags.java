package com.android.graphics.flags;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet =
            new HashSet(
                    Arrays.asList(
                            Flags.FLAG_EXACT_COMPUTE_BOUNDS,
                            Flags.FLAG_ICON_LOAD_DRAWABLE_RETURN_NULL_WHEN_URI_DECODE_FAILS,
                            Flags.FLAG_OK_LAB_COLORSPACE,
                            Flags.FLAG_YUV_IMAGE_COMPRESS_TO_ULTRA_HDR,
                            ""));

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> getValueImpl) {
        this.mGetValueImpl = getValueImpl;
    }

    @Override // com.android.graphics.flags.FeatureFlags
    public boolean exactComputeBounds() {
        return getValue(
                Flags.FLAG_EXACT_COMPUTE_BOUNDS,
                new Predicate() { // from class:
                                  // com.android.graphics.flags.CustomFeatureFlags$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).exactComputeBounds();
                    }
                });
    }

    @Override // com.android.graphics.flags.FeatureFlags
    public boolean iconLoadDrawableReturnNullWhenUriDecodeFails() {
        return getValue(
                Flags.FLAG_ICON_LOAD_DRAWABLE_RETURN_NULL_WHEN_URI_DECODE_FAILS,
                new Predicate() { // from class:
                                  // com.android.graphics.flags.CustomFeatureFlags$$ExternalSyntheticLambda3
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).iconLoadDrawableReturnNullWhenUriDecodeFails();
                    }
                });
    }

    @Override // com.android.graphics.flags.FeatureFlags
    public boolean okLabColorspace() {
        return getValue(
                Flags.FLAG_OK_LAB_COLORSPACE,
                new Predicate() { // from class:
                                  // com.android.graphics.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).okLabColorspace();
                    }
                });
    }

    @Override // com.android.graphics.flags.FeatureFlags
    public boolean yuvImageCompressToUltraHdr() {
        return getValue(
                Flags.FLAG_YUV_IMAGE_COMPRESS_TO_ULTRA_HDR,
                new Predicate() { // from class:
                                  // com.android.graphics.flags.CustomFeatureFlags$$ExternalSyntheticLambda2
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).yuvImageCompressToUltraHdr();
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
                Flags.FLAG_EXACT_COMPUTE_BOUNDS,
                Flags.FLAG_ICON_LOAD_DRAWABLE_RETURN_NULL_WHEN_URI_DECODE_FAILS,
                Flags.FLAG_OK_LAB_COLORSPACE,
                Flags.FLAG_YUV_IMAGE_COMPRESS_TO_ULTRA_HDR);
    }
}
