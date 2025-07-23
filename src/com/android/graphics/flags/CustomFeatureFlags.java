package com.android.graphics.flags;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes5.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_DISPLAY_BT2020_COLORSPACE, Flags.FLAG_EXACT_COMPUTE_BOUNDS, Flags.FLAG_GRADIENT_DRAWABLE_SHAPE_ARC_FOR_ROUNDED_CAP, Flags.FLAG_ICON_LOAD_DRAWABLE_RETURN_NULL_WHEN_URI_DECODE_FAILS, Flags.FLAG_OK_LAB_COLORSPACE, Flags.FLAG_YUV_IMAGE_COMPRESS_TO_ULTRA_HDR, ""));

    private boolean isOptimizationEnabled() {
        return false;
    }

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // com.android.graphics.flags.FeatureFlags
    public boolean displayBt2020Colorspace() {
        return getValue(Flags.FLAG_DISPLAY_BT2020_COLORSPACE, new Predicate() { // from class: com.android.graphics.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).displayBt2020Colorspace();
            }
        });
    }

    @Override // com.android.graphics.flags.FeatureFlags
    public boolean exactComputeBounds() {
        return getValue(Flags.FLAG_EXACT_COMPUTE_BOUNDS, new Predicate() { // from class: com.android.graphics.flags.CustomFeatureFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).exactComputeBounds();
            }
        });
    }

    @Override // com.android.graphics.flags.FeatureFlags
    public boolean gradientDrawableShapeArcForRoundedCap() {
        return getValue(Flags.FLAG_GRADIENT_DRAWABLE_SHAPE_ARC_FOR_ROUNDED_CAP, new Predicate() { // from class: com.android.graphics.flags.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).gradientDrawableShapeArcForRoundedCap();
            }
        });
    }

    @Override // com.android.graphics.flags.FeatureFlags
    public boolean iconLoadDrawableReturnNullWhenUriDecodeFails() {
        return getValue(Flags.FLAG_ICON_LOAD_DRAWABLE_RETURN_NULL_WHEN_URI_DECODE_FAILS, new Predicate() { // from class: com.android.graphics.flags.CustomFeatureFlags$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).iconLoadDrawableReturnNullWhenUriDecodeFails();
            }
        });
    }

    @Override // com.android.graphics.flags.FeatureFlags
    public boolean okLabColorspace() {
        return getValue(Flags.FLAG_OK_LAB_COLORSPACE, new Predicate() { // from class: com.android.graphics.flags.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).okLabColorspace();
            }
        });
    }

    @Override // com.android.graphics.flags.FeatureFlags
    public boolean yuvImageCompressToUltraHdr() {
        return getValue(Flags.FLAG_YUV_IMAGE_COMPRESS_TO_ULTRA_HDR, new Predicate() { // from class: com.android.graphics.flags.CustomFeatureFlags$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).yuvImageCompressToUltraHdr();
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
        return Arrays.asList(Flags.FLAG_DISPLAY_BT2020_COLORSPACE, Flags.FLAG_EXACT_COMPUTE_BOUNDS, Flags.FLAG_GRADIENT_DRAWABLE_SHAPE_ARC_FOR_ROUNDED_CAP, Flags.FLAG_ICON_LOAD_DRAWABLE_RETURN_NULL_WHEN_URI_DECODE_FAILS, Flags.FLAG_OK_LAB_COLORSPACE, Flags.FLAG_YUV_IMAGE_COMPRESS_TO_ULTRA_HDR);
    }
}
