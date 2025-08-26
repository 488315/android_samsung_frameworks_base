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
    public final Shader mo453createShaderuvyYCjk(long j) {
        float fIntBitsToFloat;
        float fIntBitsToFloat2;
        long j2 = this.center;
        if ((9223372034707292159L & j2) == 9205357640488583168L) {
            long jM422getCenteruvyYCjk = SizeKt.m422getCenteruvyYCjk(j);
            fIntBitsToFloat = Float.intBitsToFloat((int) (jM422getCenteruvyYCjk >> 32));
            fIntBitsToFloat2 = Float.intBitsToFloat((int) (jM422getCenteruvyYCjk & 4294967295L));
        } else {
            fIntBitsToFloat = Float.intBitsToFloat((int) (Float.intBitsToFloat((int) (j2 >> 32)) == Float.POSITIVE_INFINITY ? j >> 32 : j2 >> 32));
            fIntBitsToFloat2 = Float.intBitsToFloat((int) (Float.intBitsToFloat((int) (j2 & 4294967295L)) == Float.POSITIVE_INFINITY ? j & 4294967295L : j2 & 4294967295L));
        }
        List list = this.colors;
        List list2 = this.stops;
        long jFloatToRawIntBits = (Float.floatToRawIntBits(fIntBitsToFloat) << 32) | (Float.floatToRawIntBits(fIntBitsToFloat2) & 4294967295L);
        Offset.Companion companion = Offset.Companion;
        float fM418getMinDimensionimpl = this.radius;
        if (fM418getMinDimensionimpl == Float.POSITIVE_INFINITY) {
            fM418getMinDimensionimpl = Size.m418getMinDimensionimpl(j) / 2;
        }
        return ShaderKt.m499RadialGradientShader8uybcMk(list, list2, jFloatToRawIntBits, fM418getMinDimensionimpl, this.tileMode);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RadialGradient)) {
            return false;
        }
        RadialGradient radialGradient = (RadialGradient) obj;
        if (Intrinsics.areEqual(this.colors, radialGradient.colors) && Intrinsics.areEqual(this.stops, radialGradient.stops) && Offset.m398equalsimpl0(this.center, radialGradient.center) && this.radius == radialGradient.radius) {
            int i = radialGradient.tileMode;
            TileMode.Companion companion = TileMode.Companion;
            if (this.tileMode == i) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        int iHashCode = this.colors.hashCode() * 31;
        List list = this.stops;
        int iHashCode2 = (iHashCode + (list != null ? list.hashCode() : 0)) * 31;
        Offset.Companion companion = Offset.Companion;
        int iM = FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.radius, MoveResult$$ExternalSyntheticOutline0.m(iHashCode2, 31, this.center), 31);
        TileMode.Companion companion2 = TileMode.Companion;
        return Integer.hashCode(this.tileMode) + iM;
    }

    public final String toString() {
        String str;
        long j = this.center;
        String str2 = "";
        if ((9223372034707292159L & j) != 9205357640488583168L) {
            str = "center=" + ((Object) Offset.m405toStringimpl(j)) + ", ";
        } else {
            str = "";
        }
        float f = this.radius;
        if ((Float.floatToRawIntBits(f) & Integer.MAX_VALUE) < 2139095040) {
            str2 = "radius=" + f + ", ";
        }
        return "RadialGradient(colors=" + this.colors + ", stops=" + this.stops + ", " + str + str2 + "tileMode=" + ((Object) TileMode.m502toStringimpl(this.tileMode)) + ')';
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public RadialGradient(List list, List list2, long j, float f, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        List list3 = (i2 & 2) != 0 ? null : list2;
        if ((i2 & 16) != 0) {
            TileMode.Companion.getClass();
            i = 0;
        }
        this(list, list3, j, f, i, null);
    }

    private RadialGradient(List<Color> list, List<Float> list2, long j, float f, int i) {
        this.colors = list;
        this.stops = list2;
        this.center = j;
        this.radius = f;
        this.tileMode = i;
    }
}
