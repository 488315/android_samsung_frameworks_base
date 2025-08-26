package androidx.compose.ui.unit;

import androidx.compose.ui.unit.Dp;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class DpSize {
    public static final Companion Companion = new Companion(null);
    public static final long Unspecified = 9205357640488583168L;
    public final long packedValue;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    private /* synthetic */ DpSize(long j) {
        this.packedValue = j;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ DpSize m844boximpl(long j) {
        return new DpSize(j);
    }

    /* renamed from: copy-DwJknco$default, reason: not valid java name */
    public static long m845copyDwJknco$default(float f, float f2, long j, int i) {
        if ((i & 1) != 0) {
            f = m847getWidthD9Ej5fM(j);
        }
        if ((i & 2) != 0) {
            f2 = m846getHeightD9Ej5fM(j);
        }
        return (Float.floatToRawIntBits(f2) & 4294967295L) | (Float.floatToRawIntBits(f) << 32);
    }

    /* renamed from: getHeight-D9Ej5fM, reason: not valid java name */
    public static final float m846getHeightD9Ej5fM(long j) {
        float fIntBitsToFloat = Float.intBitsToFloat((int) (j & 4294967295L));
        Dp.Companion companion = Dp.Companion;
        return fIntBitsToFloat;
    }

    /* renamed from: getWidth-D9Ej5fM, reason: not valid java name */
    public static final float m847getWidthD9Ej5fM(long j) {
        float fIntBitsToFloat = Float.intBitsToFloat((int) (j >> 32));
        Dp.Companion companion = Dp.Companion;
        return fIntBitsToFloat;
    }

    /* renamed from: toString-impl, reason: not valid java name */
    public static String m848toStringimpl(long j) {
        if (j == 9205357640488583168L) {
            return "DpSize.Unspecified";
        }
        return ((Object) Dp.m839toStringimpl(m847getWidthD9Ej5fM(j))) + " x " + ((Object) Dp.m839toStringimpl(m846getHeightD9Ej5fM(j)));
    }

    public final boolean equals(Object obj) {
        if (obj instanceof DpSize) {
            return this.packedValue == ((DpSize) obj).packedValue;
        }
        return false;
    }

    public final int hashCode() {
        return Long.hashCode(this.packedValue);
    }

    public final String toString() {
        return m848toStringimpl(this.packedValue);
    }
}
