package com.android.compose.animation.scene.transformation;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class TransformationWithRange {
    public final TransformationRange range;
    public final Transformation transformation;

    public TransformationWithRange(Transformation transformation, TransformationRange transformationRange) {
        this.transformation = transformation;
        this.range = transformationRange;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TransformationWithRange)) {
            return false;
        }
        TransformationWithRange transformationWithRange = (TransformationWithRange) obj;
        return Intrinsics.areEqual(this.transformation, transformationWithRange.transformation) && Intrinsics.areEqual(this.range, transformationWithRange.range);
    }

    public final int hashCode() {
        int hashCode = this.transformation.hashCode() * 31;
        TransformationRange transformationRange = this.range;
        return hashCode + (transformationRange == null ? 0 : transformationRange.hashCode());
    }

    public final String toString() {
        return "TransformationWithRange(transformation=" + this.transformation + ", range=" + this.range + ")";
    }
}
