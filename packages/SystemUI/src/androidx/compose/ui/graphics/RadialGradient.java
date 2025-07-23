package androidx.compose.ui.graphics;

import android.graphics.Shader;
import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.geometry.SizeKt;
import androidx.compose.ui.graphics.TileMode;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class RadialGradient extends ShaderBrush {
    public final long center;
    public final List colors;
    public final float radius;
    public final List stops;
    public final int tileMode;

    public /* synthetic */ RadialGradient(List list, List list2, long j, float f, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(list, list2, j, f, i);
    }

    @Override // androidx.compose.ui.graphics.ShaderBrush
    /* renamed from: createShader-uvyYCjk */
    public final Shader mo451createShaderuvyYCjk(long j) {
        float intBitsToFloat;
        float intBitsToFloat2;
        long j2 = this.center;
        if ((9223372034707292159L & j2) == 9205357640488583168L) {
            long m420getCenteruvyYCjk = SizeKt.m420getCenteruvyYCjk(j);
            intBitsToFloat = Float.intBitsToFloat((int) (m420getCenteruvyYCjk >> 32));
            intBitsToFloat2 = Float.intBitsToFloat((int) (m420getCenteruvyYCjk & 4294967295L));
        } else {
            intBitsToFloat = Float.intBitsToFloat((int) (Float.intBitsToFloat((int) (j2 >> 32)) == Float.POSITIVE_INFINITY ? j >> 32 : j2 >> 32));
            intBitsToFloat2 = Float.intBitsToFloat((int) (Float.intBitsToFloat((int) (j2 & 4294967295L)) == Float.POSITIVE_INFINITY ? j & 4294967295L : j2 & 4294967295L));
        }
        List list = this.colors;
        List list2 = this.stops;
        long floatToRawIntBits = (Float.floatToRawIntBits(intBitsToFloat) << 32) | (Float.floatToRawIntBits(intBitsToFloat2) & 4294967295L);
        Offset.Companion companion = Offset.Companion;
        float f = this.radius;
        if (f == Float.POSITIVE_INFINITY) {
            f = Size.m416getMinDimensionimpl(j) / 2;
        }
        return ShaderKt.m497RadialGradientShader8uybcMk(list, list2, floatToRawIntBits, f, this.tileMode);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RadialGradient)) {
            return false;
        }
        RadialGradient radialGradient = (RadialGradient) obj;
        if (Intrinsics.areEqual(this.colors, radialGradient.colors) && Intrinsics.areEqual(this.stops, radialGradient.stops) && Offset.m396equalsimpl0(this.center, radialGradient.center) && this.radius == radialGradient.radius) {
            int i = radialGradient.tileMode;
            TileMode.Companion companion = TileMode.Companion;
            if (this.tileMode == i) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        int hashCode = this.colors.hashCode() * 31;
        List list = this.stops;
        int hashCode2 = (hashCode + (list != null ? list.hashCode() : 0)) * 31;
        Offset.Companion companion = Offset.Companion;
        int m = FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.radius, MoveResult$$ExternalSyntheticOutline0.m(hashCode2, 31, this.center), 31);
        TileMode.Companion companion2 = TileMode.Companion;
        return Integer.hashCode(this.tileMode) + m;
    }

    public final String toString() {
        String str;
        long j = this.center;
        String str2 = "";
        if ((9223372034707292159L & j) != 9205357640488583168L) {
            str = "center=" + ((Object) Offset.m403toStringimpl(j)) + ", ";
        } else {
            str = "";
        }
        float f = this.radius;
        if ((Float.floatToRawIntBits(f) & Integer.MAX_VALUE) < 2139095040) {
            str2 = "radius=" + f + ", ";
        }
        return "RadialGradient(colors=" + this.colors + ", stops=" + this.stops + ", " + str + str2 + "tileMode=" + ((Object) TileMode.m500toStringimpl(this.tileMode)) + ')';
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public RadialGradient(java.util.List r9, java.util.List r10, long r11, float r13, int r14, int r15, kotlin.jvm.internal.DefaultConstructorMarker r16) {
        /*
            r8 = this;
            r0 = r15 & 2
            if (r0 == 0) goto L5
            r10 = 0
        L5:
            r2 = r10
            r10 = r15 & 16
            if (r10 == 0) goto L10
            androidx.compose.ui.graphics.TileMode$Companion r10 = androidx.compose.ui.graphics.TileMode.Companion
            r10.getClass()
            r14 = 0
        L10:
            r6 = r14
            r7 = 0
            r0 = r8
            r1 = r9
            r3 = r11
            r5 = r13
            r0.<init>(r1, r2, r3, r5, r6, r7)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.graphics.RadialGradient.<init>(java.util.List, java.util.List, long, float, int, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    private RadialGradient(List<Color> list, List<Float> list2, long j, float f, int i) {
        this.colors = list;
        this.stops = list2;
        this.center = j;
        this.radius = f;
        this.tileMode = i;
    }
}
