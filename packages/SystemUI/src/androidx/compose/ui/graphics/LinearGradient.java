package androidx.compose.ui.graphics;

import android.graphics.Shader;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.graphics.TileMode;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import java.util.List;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
    public final Shader mo451createShaderuvyYCjk(long j) {
        long j2 = this.start;
        int i = (int) (j2 >> 32);
        if (Float.intBitsToFloat(i) == Float.POSITIVE_INFINITY) {
            i = (int) (j >> 32);
        }
        float intBitsToFloat = Float.intBitsToFloat(i);
        int i2 = (int) (j2 & 4294967295L);
        if (Float.intBitsToFloat(i2) == Float.POSITIVE_INFINITY) {
            i2 = (int) (j & 4294967295L);
        }
        float intBitsToFloat2 = Float.intBitsToFloat(i2);
        long j3 = this.end;
        int i3 = (int) (j3 >> 32);
        if (Float.intBitsToFloat(i3) == Float.POSITIVE_INFINITY) {
            i3 = (int) (j >> 32);
        }
        float intBitsToFloat3 = Float.intBitsToFloat(i3);
        int i4 = (int) (j3 & 4294967295L);
        if (Float.intBitsToFloat(i4) == Float.POSITIVE_INFINITY) {
            i4 = (int) (j & 4294967295L);
        }
        float intBitsToFloat4 = Float.intBitsToFloat(i4);
        List list = this.colors;
        List list2 = this.stops;
        long floatToRawIntBits = (Float.floatToRawIntBits(intBitsToFloat) << 32) | (Float.floatToRawIntBits(intBitsToFloat2) & 4294967295L);
        Offset.Companion companion = Offset.Companion;
        long floatToRawIntBits2 = (Float.floatToRawIntBits(intBitsToFloat3) << 32) | (Float.floatToRawIntBits(intBitsToFloat4) & 4294967295L);
        AndroidShader_androidKt.validateColorStops(list, list2);
        GradientColorLongVerifier gradientColorLongVerifier = GradientColorLongVerifier.INSTANCE;
        int size = list.size();
        long[] jArr = new long[size];
        for (int i5 = 0; i5 < size; i5++) {
            jArr[i5] = ((Color) list.get(i5)).value;
        }
        return gradientColorLongVerifier.m473createLinearGradientColorLongVjE6UOU(floatToRawIntBits, floatToRawIntBits2, jArr, list2 != null ? CollectionsKt___CollectionsKt.toFloatArray(list2) : null, this.tileMode);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof LinearGradient)) {
            return false;
        }
        LinearGradient linearGradient = (LinearGradient) obj;
        if (!Intrinsics.areEqual(this.colors, linearGradient.colors) || !Intrinsics.areEqual(this.stops, linearGradient.stops) || !Offset.m396equalsimpl0(this.start, linearGradient.start) || !Offset.m396equalsimpl0(this.end, linearGradient.end)) {
            return false;
        }
        int i = linearGradient.tileMode;
        TileMode.Companion companion = TileMode.Companion;
        return this.tileMode == i;
    }

    public final int hashCode() {
        int hashCode = this.colors.hashCode() * 31;
        List list = this.stops;
        int hashCode2 = (hashCode + (list != null ? list.hashCode() : 0)) * 31;
        Offset.Companion companion = Offset.Companion;
        int m = MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(hashCode2, 31, this.start), 31, this.end);
        TileMode.Companion companion2 = TileMode.Companion;
        return Integer.hashCode(this.tileMode) + m;
    }

    public final String toString() {
        String str;
        long j = this.start;
        String str2 = "";
        if (((((j & 9187343241974906880L) ^ 9187343241974906880L) - 4294967297L) & (-9223372034707292160L)) == 0) {
            str = "start=" + ((Object) Offset.m403toStringimpl(j)) + ", ";
        } else {
            str = "";
        }
        long j2 = this.end;
        if (((((j2 & 9187343241974906880L) ^ 9187343241974906880L) - 4294967297L) & (-9223372034707292160L)) == 0) {
            str2 = "end=" + ((Object) Offset.m403toStringimpl(j2)) + ", ";
        }
        return "LinearGradient(colors=" + this.colors + ", stops=" + this.stops + ", " + str + str2 + "tileMode=" + ((Object) TileMode.m500toStringimpl(this.tileMode)) + ')';
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public LinearGradient(java.util.List r10, java.util.List r11, long r12, long r14, int r16, int r17, kotlin.jvm.internal.DefaultConstructorMarker r18) {
        /*
            r9 = this;
            r0 = r17 & 2
            if (r0 == 0) goto L5
            r11 = 0
        L5:
            r2 = r11
            r11 = r17 & 16
            if (r11 == 0) goto L12
            androidx.compose.ui.graphics.TileMode$Companion r11 = androidx.compose.ui.graphics.TileMode.Companion
            r11.getClass()
            r11 = 0
            r7 = r11
            goto L14
        L12:
            r7 = r16
        L14:
            r8 = 0
            r0 = r9
            r1 = r10
            r3 = r12
            r5 = r14
            r0.<init>(r1, r2, r3, r5, r7, r8)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.graphics.LinearGradient.<init>(java.util.List, java.util.List, long, long, int, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    private LinearGradient(List<Color> list, List<Float> list2, long j, long j2, int i) {
        this.colors = list;
        this.stops = list2;
        this.start = j;
        this.end = j2;
        this.tileMode = i;
    }
}
