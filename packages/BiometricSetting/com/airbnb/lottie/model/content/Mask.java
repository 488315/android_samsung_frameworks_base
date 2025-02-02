package com.airbnb.lottie.model.content;

import com.airbnb.lottie.model.animatable.AnimatableIntegerValue;
import com.airbnb.lottie.model.animatable.AnimatableShapeValue;

/* loaded from: classes.dex */
public final class Mask {
    private final boolean inverted;
    private final MaskMode maskMode;
    private final AnimatableShapeValue maskPath;
    private final AnimatableIntegerValue opacity;

    public enum MaskMode {
        MASK_MODE_ADD,
        MASK_MODE_SUBTRACT,
        MASK_MODE_INTERSECT,
        MASK_MODE_NONE;

        MaskMode() {
        }
    }

    public Mask(MaskMode maskMode, AnimatableShapeValue animatableShapeValue, AnimatableIntegerValue animatableIntegerValue, boolean z) {
        this.maskMode = maskMode;
        this.maskPath = animatableShapeValue;
        this.opacity = animatableIntegerValue;
        this.inverted = z;
    }

    public final MaskMode getMaskMode() {
        return this.maskMode;
    }

    public final AnimatableShapeValue getMaskPath() {
        return this.maskPath;
    }

    public final AnimatableIntegerValue getOpacity() {
        return this.opacity;
    }

    public final boolean isInverted() {
        return this.inverted;
    }
}
