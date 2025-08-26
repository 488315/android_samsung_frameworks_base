package androidx.compose.ui.graphics;

import android.graphics.Shader;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.graphics.TileMode;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import java.util.List;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class LinearGradient extends ShaderBrush {
    public final List colors;
    public final long end;
    public final long start;
    public final List stops;
    public final int tileMode;

    public /* synthetic */ LinearGradient(List list, List list2, long j, long j2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(list, list2, j, j2, i);
    }

    @Override // androidx.compose.ui.graphics.ShaderBrush
    /* renamed from: createShader-uvyYCjk */
    public final Shader mo453createShaderuvyYCjk(long j) {
        long j2 = this.start;
        int i = (int) (j2 >> 32);
        if (Float.intBitsToFloat(i) == Float.POSITIVE_INFINITY) {
            i = (int) (j >> 32);
        }
        float fIntBitsToFloat = Float.intBitsToFloat(i);
        int i2 = (int) (j2 & 4294967295L);
        if (Float.intBitsToFloat(i2) == Float.POSITIVE_INFINITY) {
            i2 = (int) (j & 4294967295L);
        }
        float fIntBitsToFloat2 = Float.intBitsToFloat(i2);
        long j3 = this.end;
        int i3 = (int) (j3 >> 32);
        if (Float.intBitsToFloat(i3) == Float.POSITIVE_INFINITY) {
            i3 = (int) (j >> 32);
        }
        float fIntBitsToFloat3 = Float.intBitsToFloat(i3);
        int i4 = (int) (j3 & 4294967295L);
        if (Float.intBitsToFloat(i4) == Float.POSITIVE_INFINITY) {
            i4 = (int) (j & 4294967295L);
        }
        float fIntBitsToFloat4 = Float.intBitsToFloat(i4);
        List list = this.colors;
        List list2 = this.stops;
        long jFloatToRawIntBits = (Float.floatToRawIntBits(fIntBitsToFloat) << 32) | (Float.floatToRawIntBits(fIntBitsToFloat2) & 4294967295L);
        Offset.Companion companion = Offset.Companion;
        long jFloatToRawIntBits2 = (Float.floatToRawIntBits(fIntBitsToFloat3) << 32) | (Float.floatToRawIntBits(fIntBitsToFloat4) & 4294967295L);
        AndroidShader_androidKt.validateColorStops(list, list2);
        GradientColorLongVerifier gradientColorLongVerifier = GradientColorLongVerifier.INSTANCE;
        int size = list.size();
        long[] jArr = new long[size];
        for (int i5 = 0; i5 < size; i5++) {
            jArr[i5] = ((Color) list.get(i5)).value;
        }
        return gradientColorLongVerifier.m475createLinearGradientColorLongVjE6UOU(jFloatToRawIntBits, jFloatToRawIntBits2, jArr, list2 != null ? CollectionsKt___CollectionsKt.toFloatArray(list2) : null, this.tileMode);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof LinearGradient)) {
            return false;
        }
        LinearGradient linearGradient = (LinearGradient) obj;
        if (!Intrinsics.areEqual(this.colors, linearGradient.colors) || !Intrinsics.areEqual(this.stops, linearGradient.stops) || !Offset.m398equalsimpl0(this.start, linearGradient.start) || !Offset.m398equalsimpl0(this.end, linearGradient.end)) {
            return false;
        }
        int i = linearGradient.tileMode;
        TileMode.Companion companion = TileMode.Companion;
        return this.tileMode == i;
    }

    public final int hashCode() {
        int iHashCode = this.colors.hashCode() * 31;
        List list = this.stops;
        int iHashCode2 = (iHashCode + (list != null ? list.hashCode() : 0)) * 31;
        Offset.Companion companion = Offset.Companion;
        int iM = MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(iHashCode2, 31, this.start), 31, this.end);
        TileMode.Companion companion2 = TileMode.Companion;
        return Integer.hashCode(this.tileMode) + iM;
    }

    public final String toString() {
        String str;
        long j = this.start;
        String str2 = "";
        if (((((j & 9187343241974906880L) ^ 9187343241974906880L) - 4294967297L) & (-9223372034707292160L)) == 0) {
            str = "start=" + ((Object) Offset.m405toStringimpl(j)) + ", ";
        } else {
            str = "";
        }
        long j2 = this.end;
        if (((((j2 & 9187343241974906880L) ^ 9187343241974906880L) - 4294967297L) & (-9223372034707292160L)) == 0) {
            str2 = "end=" + ((Object) Offset.m405toStringimpl(j2)) + ", ";
        }
        return "LinearGradient(colors=" + this.colors + ", stops=" + this.stops + ", " + str + str2 + "tileMode=" + ((Object) TileMode.m502toStringimpl(this.tileMode)) + ')';
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public LinearGradient(List list, List list2, long j, long j2, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        int i3;
        List list3 = (i2 & 2) != 0 ? null : list2;
        if ((i2 & 16) != 0) {
            TileMode.Companion.getClass();
            i3 = 0;
        } else {
            i3 = i;
        }
        this(list, list3, j, j2, i3, null);
    }

    private LinearGradient(List<Color> list, List<Float> list2, long j, long j2, int i) {
        this.colors = list;
        this.stops = list2;
        this.start = j;
        this.end = j2;
        this.tileMode = i;
    }
}
