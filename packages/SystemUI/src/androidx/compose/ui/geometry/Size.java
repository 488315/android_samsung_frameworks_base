package androidx.compose.ui.geometry;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class Size {
    public static final Companion Companion = new Companion(null);
    public static final long Unspecified = 9205357640488583168L;
    public final long packedValue;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    private /* synthetic */ Size(long j) {
        this.packedValue = j;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ Size m413boximpl(long j) {
        return new Size(j);
    }

    /* renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m414equalsimpl0(long j, long j2) {
        return j == j2;
    }

    /* renamed from: getHeight-impl, reason: not valid java name */
    public static final float m415getHeightimpl(long j) {
        return Float.intBitsToFloat((int) (j & 4294967295L));
    }

    /* renamed from: getMinDimension-impl, reason: not valid java name */
    public static final float m416getMinDimensionimpl(long j) {
        return Math.min(Float.intBitsToFloat((int) ((j >> 32) & 2147483647L)), Float.intBitsToFloat((int) (j & 2147483647L)));
    }

    /* renamed from: getWidth-impl, reason: not valid java name */
    public static final float m417getWidthimpl(long j) {
        return Float.intBitsToFloat((int) (j >> 32));
    }

    /* renamed from: isEmpty-impl, reason: not valid java name */
    public static final boolean m418isEmptyimpl(long j) {
        long j2 = (~((((-9223372034707292160L) & j) >>> 31) * (-1))) & j;
        return (((j2 & 4294967295L) & (j2 >>> 32)) == 0) | (j == 9205357640488583168L);
    }

    /* renamed from: toString-impl, reason: not valid java name */
    public static String m419toStringimpl(long j) {
        if (j == 9205357640488583168L) {
            return "Size.Unspecified";
        }
        return "Size(" + GeometryUtilsKt.toStringAsFixed(Float.intBitsToFloat((int) (j >> 32))) + ", " + GeometryUtilsKt.toStringAsFixed(Float.intBitsToFloat((int) (j & 4294967295L))) + ')';
    }

    public final boolean equals(Object obj) {
        if (obj instanceof Size) {
            return this.packedValue == ((Size) obj).packedValue;
        }
        return false;
    }

    public final int hashCode() {
        return Long.hashCode(this.packedValue);
    }

    public final String toString() {
        return m419toStringimpl(this.packedValue);
    }
}
