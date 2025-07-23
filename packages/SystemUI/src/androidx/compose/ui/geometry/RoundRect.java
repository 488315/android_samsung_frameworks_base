package androidx.compose.ui.geometry;

import androidx.activity.result.ActivityResultRegistry$register$3$$ExternalSyntheticOutline0;
import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.ui.geometry.CornerRadius;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        RoundRectKt.m412RoundRectgG7oq9Y(0.0f, 0.0f, 0.0f, 0.0f, 0L);
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
        return Float.compare(this.left, roundRect.left) == 0 && Float.compare(this.top, roundRect.top) == 0 && Float.compare(this.right, roundRect.right) == 0 && Float.compare(this.bottom, roundRect.bottom) == 0 && CornerRadius.m391equalsimpl0(this.topLeftCornerRadius, roundRect.topLeftCornerRadius) && CornerRadius.m391equalsimpl0(this.topRightCornerRadius, roundRect.topRightCornerRadius) && CornerRadius.m391equalsimpl0(this.bottomRightCornerRadius, roundRect.bottomRightCornerRadius) && CornerRadius.m391equalsimpl0(this.bottomLeftCornerRadius, roundRect.bottomLeftCornerRadius);
    }

    public final float getHeight() {
        return this.bottom - this.top;
    }

    public final float getWidth() {
        return this.right - this.left;
    }

    public final int hashCode() {
        int m = FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.bottom, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.right, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.top, Float.hashCode(this.left) * 31, 31), 31), 31);
        CornerRadius.Companion companion = CornerRadius.Companion;
        return Long.hashCode(this.bottomLeftCornerRadius) + MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(m, 31, this.topLeftCornerRadius), 31, this.topRightCornerRadius), 31, this.bottomRightCornerRadius);
    }

    public final String toString() {
        String str = GeometryUtilsKt.toStringAsFixed(this.left) + ", " + GeometryUtilsKt.toStringAsFixed(this.top) + ", " + GeometryUtilsKt.toStringAsFixed(this.right) + ", " + GeometryUtilsKt.toStringAsFixed(this.bottom);
        long j = this.topLeftCornerRadius;
        long j2 = this.topRightCornerRadius;
        boolean m391equalsimpl0 = CornerRadius.m391equalsimpl0(j, j2);
        long j3 = this.bottomRightCornerRadius;
        long j4 = this.bottomLeftCornerRadius;
        if (!m391equalsimpl0 || !CornerRadius.m391equalsimpl0(j2, j3) || !CornerRadius.m391equalsimpl0(j3, j4)) {
            StringBuilder m = ActivityResultRegistry$register$3$$ExternalSyntheticOutline0.m("RoundRect(rect=", str, ", topLeft=");
            m.append((Object) CornerRadius.m392toStringimpl(j));
            m.append(", topRight=");
            m.append((Object) CornerRadius.m392toStringimpl(j2));
            m.append(", bottomRight=");
            m.append((Object) CornerRadius.m392toStringimpl(j3));
            m.append(", bottomLeft=");
            m.append((Object) CornerRadius.m392toStringimpl(j4));
            m.append(')');
            return m.toString();
        }
        int i = (int) (j >> 32);
        int i2 = (int) (j & 4294967295L);
        if (Float.intBitsToFloat(i) == Float.intBitsToFloat(i2)) {
            StringBuilder m2 = ActivityResultRegistry$register$3$$ExternalSyntheticOutline0.m("RoundRect(rect=", str, ", radius=");
            m2.append(GeometryUtilsKt.toStringAsFixed(Float.intBitsToFloat(i)));
            m2.append(')');
            return m2.toString();
        }
        StringBuilder m3 = ActivityResultRegistry$register$3$$ExternalSyntheticOutline0.m("RoundRect(rect=", str, ", x=");
        m3.append(GeometryUtilsKt.toStringAsFixed(Float.intBitsToFloat(i)));
        m3.append(", y=");
        m3.append(GeometryUtilsKt.toStringAsFixed(Float.intBitsToFloat(i2)));
        m3.append(')');
        return m3.toString();
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
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public RoundRect(float r19, float r20, float r21, float r22, long r23, long r25, long r27, long r29, int r31, kotlin.jvm.internal.DefaultConstructorMarker r32) {
        /*
            r18 = this;
            r0 = r31
            r1 = r0 & 16
            r2 = 0
            if (r1 == 0) goto Lf
            androidx.compose.ui.geometry.CornerRadius$Companion r1 = androidx.compose.ui.geometry.CornerRadius.Companion
            r1.getClass()
            r9 = r2
            goto L11
        Lf:
            r9 = r23
        L11:
            r1 = r0 & 32
            if (r1 == 0) goto L1c
            androidx.compose.ui.geometry.CornerRadius$Companion r1 = androidx.compose.ui.geometry.CornerRadius.Companion
            r1.getClass()
            r11 = r2
            goto L1e
        L1c:
            r11 = r25
        L1e:
            r1 = r0 & 64
            if (r1 == 0) goto L29
            androidx.compose.ui.geometry.CornerRadius$Companion r1 = androidx.compose.ui.geometry.CornerRadius.Companion
            r1.getClass()
            r13 = r2
            goto L2b
        L29:
            r13 = r27
        L2b:
            r0 = r0 & 128(0x80, float:1.8E-43)
            if (r0 == 0) goto L36
            androidx.compose.ui.geometry.CornerRadius$Companion r0 = androidx.compose.ui.geometry.CornerRadius.Companion
            r0.getClass()
            r15 = r2
            goto L38
        L36:
            r15 = r29
        L38:
            r17 = 0
            r4 = r18
            r5 = r19
            r6 = r20
            r7 = r21
            r8 = r22
            r4.<init>(r5, r6, r7, r8, r9, r11, r13, r15, r17)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.geometry.RoundRect.<init>(float, float, float, float, long, long, long, long, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }
}
