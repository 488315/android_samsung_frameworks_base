package androidx.compose.ui.geometry;

import androidx.activity.result.ActivityResultRegistry$register$3$$ExternalSyntheticOutline0;
import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.ui.geometry.CornerRadius;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class RoundRect {
    public final float bottom;
    public final long bottomLeftCornerRadius;
    public final long bottomRightCornerRadius;
    public final float left;
    public final float right;
    public final float top;
    public final long topLeftCornerRadius;
    public final long topRightCornerRadius;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        CornerRadius.Companion.getClass();
        RoundRectKt.m414RoundRectgG7oq9Y(0.0f, 0.0f, 0.0f, 0.0f, 0L);
    }

    public /* synthetic */ RoundRect(float f, float f2, float f3, float f4, long j, long j2, long j3, long j4, DefaultConstructorMarker defaultConstructorMarker) {
        this(f, f2, f3, f4, j, j2, j3, j4);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RoundRect)) {
            return false;
        }
        RoundRect roundRect = (RoundRect) obj;
        return Float.compare(this.left, roundRect.left) == 0 && Float.compare(this.top, roundRect.top) == 0 && Float.compare(this.right, roundRect.right) == 0 && Float.compare(this.bottom, roundRect.bottom) == 0 && CornerRadius.m393equalsimpl0(this.topLeftCornerRadius, roundRect.topLeftCornerRadius) && CornerRadius.m393equalsimpl0(this.topRightCornerRadius, roundRect.topRightCornerRadius) && CornerRadius.m393equalsimpl0(this.bottomRightCornerRadius, roundRect.bottomRightCornerRadius) && CornerRadius.m393equalsimpl0(this.bottomLeftCornerRadius, roundRect.bottomLeftCornerRadius);
    }

    public final float getHeight() {
        return this.bottom - this.top;
    }

    public final float getWidth() {
        return this.right - this.left;
    }

    public final int hashCode() {
        int iM = FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.bottom, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.right, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.top, Float.hashCode(this.left) * 31, 31), 31), 31);
        CornerRadius.Companion companion = CornerRadius.Companion;
        return Long.hashCode(this.bottomLeftCornerRadius) + MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(iM, 31, this.topLeftCornerRadius), 31, this.topRightCornerRadius), 31, this.bottomRightCornerRadius);
    }

    public final String toString() {
        String str = GeometryUtilsKt.toStringAsFixed(this.left) + ", " + GeometryUtilsKt.toStringAsFixed(this.top) + ", " + GeometryUtilsKt.toStringAsFixed(this.right) + ", " + GeometryUtilsKt.toStringAsFixed(this.bottom);
        long j = this.topLeftCornerRadius;
        long j2 = this.topRightCornerRadius;
        boolean zM393equalsimpl0 = CornerRadius.m393equalsimpl0(j, j2);
        long j3 = this.bottomRightCornerRadius;
        long j4 = this.bottomLeftCornerRadius;
        if (!zM393equalsimpl0 || !CornerRadius.m393equalsimpl0(j2, j3) || !CornerRadius.m393equalsimpl0(j3, j4)) {
            StringBuilder sbM = ActivityResultRegistry$register$3$$ExternalSyntheticOutline0.m("RoundRect(rect=", str, ", topLeft=");
            sbM.append((Object) CornerRadius.m394toStringimpl(j));
            sbM.append(", topRight=");
            sbM.append((Object) CornerRadius.m394toStringimpl(j2));
            sbM.append(", bottomRight=");
            sbM.append((Object) CornerRadius.m394toStringimpl(j3));
            sbM.append(", bottomLeft=");
            sbM.append((Object) CornerRadius.m394toStringimpl(j4));
            sbM.append(')');
            return sbM.toString();
        }
        int i = (int) (j >> 32);
        int i2 = (int) (j & 4294967295L);
        if (Float.intBitsToFloat(i) == Float.intBitsToFloat(i2)) {
            StringBuilder sbM2 = ActivityResultRegistry$register$3$$ExternalSyntheticOutline0.m("RoundRect(rect=", str, ", radius=");
            sbM2.append(GeometryUtilsKt.toStringAsFixed(Float.intBitsToFloat(i)));
            sbM2.append(')');
            return sbM2.toString();
        }
        StringBuilder sbM3 = ActivityResultRegistry$register$3$$ExternalSyntheticOutline0.m("RoundRect(rect=", str, ", x=");
        sbM3.append(GeometryUtilsKt.toStringAsFixed(Float.intBitsToFloat(i)));
        sbM3.append(", y=");
        sbM3.append(GeometryUtilsKt.toStringAsFixed(Float.intBitsToFloat(i2)));
        sbM3.append(')');
        return sbM3.toString();
    }

    private RoundRect(float f, float f2, float f3, float f4, long j, long j2, long j3, long j4) {
        this.left = f;
        this.top = f2;
        this.right = f3;
        this.bottom = f4;
        this.topLeftCornerRadius = j;
        this.topRightCornerRadius = j2;
        this.bottomRightCornerRadius = j3;
        this.bottomLeftCornerRadius = j4;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public RoundRect(float f, float f2, float f3, float f4, long j, long j2, long j3, long j4, int i, DefaultConstructorMarker defaultConstructorMarker) {
        long j5;
        long j6;
        long j7;
        long j8;
        if ((i & 16) != 0) {
            CornerRadius.Companion.getClass();
            j5 = 0;
        } else {
            j5 = j;
        }
        if ((i & 32) != 0) {
            CornerRadius.Companion.getClass();
            j6 = 0;
        } else {
            j6 = j2;
        }
        if ((i & 64) != 0) {
            CornerRadius.Companion.getClass();
            j7 = 0;
        } else {
            j7 = j3;
        }
        if ((i & 128) != 0) {
            CornerRadius.Companion.getClass();
            j8 = 0;
        } else {
            j8 = j4;
        }
        this(f, f2, f3, f4, j5, j6, j7, j8, null);
    }
}
