package com.android.systemui.plugins.clocks;

import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Half;
import androidx.compose.animation.core.CubicBezierEasing$$ExternalSyntheticOutline0;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class VRectF {
    public static final Companion Companion = new Companion(null);
    private static final long ZERO = m2826constructorimpl(0.0f, 0.0f, 0.0f, 0.0f);
    private final long data;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final float fromBits(short s) {
            return Half.toFloat(Half.intBitsToHalf(s));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final short toBits(float f) {
            return Half.halfToShortBits(Half.toHalf(f));
        }

        /* renamed from: fromCenter-cwyIbD4, reason: not valid java name */
        public final long m2846fromCentercwyIbD4(long j, long j2) {
            float f = 2;
            return VRectF.m2826constructorimpl(VPointF.m2760getXimpl(j) - (VPointF.m2760getXimpl(j2) / f), VPointF.m2761getYimpl(j) - (VPointF.m2761getYimpl(j2) / f), (VPointF.m2760getXimpl(j2) / f) + VPointF.m2760getXimpl(j), (VPointF.m2761getYimpl(j2) / f) + VPointF.m2761getYimpl(j));
        }

        /* renamed from: fromLong-WMibXUk, reason: not valid java name */
        public final long m2847fromLongWMibXUk(long j) {
            int i = ULong.$r8$clinit;
            return VRectF.m2827constructorimpl(j);
        }

        /* renamed from: fromTopLeft-cwyIbD4, reason: not valid java name */
        public final long m2848fromTopLeftcwyIbD4(long j, long j2) {
            return VRectF.m2826constructorimpl(VPointF.m2760getXimpl(j), VPointF.m2761getYimpl(j), VPointF.m2760getXimpl(j2) + VPointF.m2760getXimpl(j), VPointF.m2761getYimpl(j2) + VPointF.m2761getYimpl(j));
        }

        /* renamed from: getZERO-3Hl7r_E, reason: not valid java name */
        public final long m2849getZERO3Hl7r_E() {
            return VRectF.ZERO;
        }

        private Companion() {
        }
    }

    private /* synthetic */ VRectF(long j) {
        this.data = j;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ VRectF m2825boximpl(long j) {
        return new VRectF(j);
    }

    /* renamed from: constructor-impl, reason: not valid java name */
    public static long m2827constructorimpl(long j) {
        return j;
    }

    /* renamed from: equals-impl, reason: not valid java name */
    public static boolean m2830equalsimpl(long j, Object obj) {
        return (obj instanceof VRectF) && j == ((VRectF) obj).m2845unboximpl();
    }

    /* renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m2831equalsimpl0(long j, long j2) {
        return ULong.m3446equalsimpl0(j, j2);
    }

    /* renamed from: getBottom-impl, reason: not valid java name */
    public static final float m2832getBottomimpl(long j) {
        return Companion.fromBits(VRectKt.m2854unpackBottomVKZWuLQ(j));
    }

    /* renamed from: getCenter-Jv7bpU8, reason: not valid java name */
    public static final long m2833getCenterJv7bpU8(long j) {
        return VPointF.m2772plusb2IjXjg(VPointF.m2747constructorimpl(m2835getLeftimpl(j), m2838getTopimpl(j)), VPointF.m2753divAsyRdg(m2837getSizeJv7bpU8(j), 2.0f));
    }

    /* renamed from: getHeight-impl, reason: not valid java name */
    public static final float m2834getHeightimpl(long j) {
        return m2832getBottomimpl(j) - m2838getTopimpl(j);
    }

    /* renamed from: getLeft-impl, reason: not valid java name */
    public static final float m2835getLeftimpl(long j) {
        return Companion.fromBits(VRectKt.m2855unpackLeftVKZWuLQ(j));
    }

    /* renamed from: getRight-impl, reason: not valid java name */
    public static final float m2836getRightimpl(long j) {
        return Companion.fromBits(VRectKt.m2856unpackRightVKZWuLQ(j));
    }

    /* renamed from: getSize-Jv7bpU8, reason: not valid java name */
    public static final long m2837getSizeJv7bpU8(long j) {
        return VPointF.m2747constructorimpl(m2839getWidthimpl(j), m2834getHeightimpl(j));
    }

    /* renamed from: getTop-impl, reason: not valid java name */
    public static final float m2838getTopimpl(long j) {
        return Companion.fromBits(VRectKt.m2857unpackTopVKZWuLQ(j));
    }

    /* renamed from: getWidth-impl, reason: not valid java name */
    public static final float m2839getWidthimpl(long j) {
        return m2836getRightimpl(j) - m2835getLeftimpl(j);
    }

    /* renamed from: hashCode-impl, reason: not valid java name */
    public static int m2840hashCodeimpl(long j) {
        int i = ULong.$r8$clinit;
        return Long.hashCode(j);
    }

    /* renamed from: toRectF-impl, reason: not valid java name */
    public static final RectF m2842toRectFimpl(long j) {
        return new RectF(m2835getLeftimpl(j), m2838getTopimpl(j), m2836getRightimpl(j), m2832getBottomimpl(j));
    }

    /* renamed from: toString-impl, reason: not valid java name */
    public static String m2843toStringimpl(long j) {
        float fM2835getLeftimpl = m2835getLeftimpl(j);
        float fM2838getTopimpl = m2838getTopimpl(j);
        float fM2836getRightimpl = m2836getRightimpl(j);
        float fM2832getBottomimpl = m2832getBottomimpl(j);
        StringBuilder sbM = CubicBezierEasing$$ExternalSyntheticOutline0.m("(", fM2835getLeftimpl, ", ", fM2838getTopimpl, ") -> (");
        sbM.append(fM2836getRightimpl);
        sbM.append(", ");
        sbM.append(fM2832getBottomimpl);
        sbM.append(")");
        return sbM.toString();
    }

    public boolean equals(Object obj) {
        return m2830equalsimpl(this.data, obj);
    }

    /* renamed from: getData-s-VKNKU, reason: not valid java name */
    public final long m2844getDatasVKNKU() {
        return this.data;
    }

    public int hashCode() {
        return m2840hashCodeimpl(this.data);
    }

    public String toString() {
        return m2843toStringimpl(this.data);
    }

    /* renamed from: unbox-impl, reason: not valid java name */
    public final /* synthetic */ long m2845unboximpl() {
        return this.data;
    }

    /* renamed from: constructor-impl, reason: not valid java name */
    public static long m2829constructorimpl(RectF rectF) {
        return m2826constructorimpl(rectF.left, rectF.top, rectF.right, rectF.bottom);
    }

    /* renamed from: constructor-impl, reason: not valid java name */
    public static long m2828constructorimpl(Rect rect) {
        return m2826constructorimpl(rect.left, rect.top, rect.right, rect.bottom);
    }

    /* renamed from: constructor-impl, reason: not valid java name */
    public static long m2826constructorimpl(float f, float f2, float f3, float f4) {
        Companion companion = Companion;
        return m2827constructorimpl(VRectKt.pack(companion.toBits(f), companion.toBits(f2), companion.toBits(f3), companion.toBits(f4)));
    }

    /* renamed from: toLong-impl, reason: not valid java name */
    public static final long m2841toLongimpl(long j) {
        return j;
    }
}
