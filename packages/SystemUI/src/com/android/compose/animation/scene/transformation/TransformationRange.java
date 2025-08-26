package com.android.compose.animation.scene.transformation;

import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.Easing;
import androidx.compose.animation.core.EasingKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class TransformationRange {
    public final Easing easing;
    public final float end;
    public final float start;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public TransformationRange(Float f, Float f2, Easing easing, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : f, (i & 2) != 0 ? null : f2, (i & 4) != 0 ? EasingKt.LinearEasing : easing);
    }

    public static boolean isSpecified(float f) {
        return !(f == Float.MIN_VALUE);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TransformationRange)) {
            return false;
        }
        TransformationRange transformationRange = (TransformationRange) obj;
        return Float.compare(this.start, transformationRange.start) == 0 && Float.compare(this.end, transformationRange.end) == 0 && Intrinsics.areEqual(this.easing, transformationRange.easing);
    }

    public final int hashCode() {
        return this.easing.hashCode() + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.end, Float.hashCode(this.start) * 31, 31);
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0039  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0042 A[PHI: r2
      0x0042: PHI (r2v1 float) = (r2v0 float), (r2v2 float) binds: [B:24:0x0040, B:11:0x001e] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final float progress(float f) {
        float f2 = this.start;
        boolean zIsSpecified = isSpecified(f2);
        float f3 = this.end;
        if (zIsSpecified && isSpecified(f3)) {
            float f4 = (f - f2) / (f3 - f2);
            f = f4 >= 0.0f ? f4 : 0.0f;
            if (f > 1.0f) {
            }
        } else if (isSpecified(f2) || isSpecified(f3)) {
            if (isSpecified(f3)) {
                f /= f3;
                if (f > 1.0f) {
                    f = 1.0f;
                }
            } else {
                f = (f - f2) / (1.0f - f2);
                if (f < 0.0f) {
                    f = f;
                }
            }
        }
        return this.easing.transform(f);
    }

    public final String toString() {
        return "TransformationRange(start=" + this.start + ", end=" + this.end + ", easing=" + this.easing + ")";
    }

    public TransformationRange(float f, float f2, Easing easing) {
        this.start = f;
        this.end = f2;
        this.easing = easing;
        if (isSpecified(f) && (0.0f > f || f > 1.0f)) {
            throw new IllegalArgumentException("Failed requirement.");
        }
        if (isSpecified(f2) && (0.0f > f2 || f2 > 1.0f)) {
            throw new IllegalArgumentException("Failed requirement.");
        }
        if (isSpecified(f) && isSpecified(f2) && f > f2) {
            throw new IllegalArgumentException("Failed requirement.");
        }
    }

    public TransformationRange(Float f, Float f2, Easing easing) {
        this(f != null ? f.floatValue() : Float.MIN_VALUE, f2 != null ? f2.floatValue() : Float.MIN_VALUE, easing);
    }
}
