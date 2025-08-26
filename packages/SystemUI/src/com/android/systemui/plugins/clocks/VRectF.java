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
    private static final long ZERO = m2828constructorimpl(0.0f, 0.0f, 0.0f, 0.0f);
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
        public final long m2848fromCentercwyIbD4(long j, long j2) {
            float f = 2;
            return VRectF.m2828constructorimpl(VPointF.m2762getXimpl(j) - (VPointF.m2762getXimpl(j2) / f), VPointF.m2763getYimpl(j) - (VPointF.m2763getYimpl(j2) / f), (VPointF.m2762getXimpl(j2) / f) + VPointF.m2762getXimpl(j), (VPointF.m2763getYimpl(j2) / f) + VPointF.m2763getYimpl(j));
        }

        /* renamed from: fromLong-WMibXUk, reason: not valid java name */
        public final long m2849fromLongWMibXUk(long j) {
            int i = ULong.$r8$clinit;
            return VRectF.m2829constructorimpl(j);
        }

        /* renamed from: fromTopLeft-cwyIbD4, reason: not valid java name */
        public final long m2850fromTopLeftcwyIbD4(long j, long j2) {
            return VRectF.m2828constructorimpl(VPointF.m2762getXimpl(j), VPointF.m2763getYimpl(j), VPointF.m2762getXimpl(j2) + VPointF.m2762getXimpl(j), VPointF.m2763getYimpl(j2) + VPointF.m2763getYimpl(j));
        }

        /* renamed from: getZERO-3Hl7r_E, reason: not valid java name */
        public final long m2851getZERO3Hl7r_E() {
            return VRectF.ZERO;
        }

        private Companion() {
        }
    }

    private /* synthetic */ VRectF(long j) {
        this.data = j;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ VRectF m2827boximpl(long j) {
        return new VRectF(j);
    }

    /* renamed from: constructor-impl, reason: not valid java name */
    public static long m2829constructorimpl(long j) {
        return j;
    }

    /* renamed from: equals-impl, reason: not valid java name */
    public static boolean m2832equalsimpl(long j, Object obj) {
        return (obj instanceof VRectF) && j == ((VRectF) obj).m2847unboximpl();
    }

    /* renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m2833equalsimpl0(long j, long j2) {
        return ULong.m3447equalsimpl0(j, j2);
    }

    /* renamed from: getBottom-impl, reason: not valid java name */
    public static final float m2834getBottomimpl(long j) {
        return Companion.fromBits(VRectKt.m2856unpackBottomVKZWuLQ(j));
    }

    /* renamed from: getCenter-Jv7bpU8, reason: not valid java name */
    public static final long m2835getCenterJv7bpU8(long j) {
        return VPointF.m2774plusb2IjXjg(VPointF.m2749constructorimpl(m2837getLeftimpl(j), m2840getTopimpl(j)), VPointF.m2755divAsyRdg(m2839getSizeJv7bpU8(j), 2.0f));
    }

    /* renamed from: getHeight-impl, reason: not valid java name */
    public static final float m2836getHeightimpl(long j) {
        return m2834getBottomimpl(j) - m2840getTopimpl(j);
    }

    /* renamed from: getLeft-impl, reason: not valid java name */
    public static final float m2837getLeftimpl(long j) {
        return Companion.fromBits(VRectKt.m2857unpackLeftVKZWuLQ(j));
    }

    /* renamed from: getRight-impl, reason: not valid java name */
    public static final float m2838getRightimpl(long j) {
        return Companion.fromBits(VRectKt.m2858unpackRightVKZWuLQ(j));
    }

    /* renamed from: getSize-Jv7bpU8, reason: not valid java name */
    public static final long m2839getSizeJv7bpU8(long j) {
        return VPointF.m2749constructorimpl(m2841getWidthimpl(j), m2836getHeightimpl(j));
    }

    /* renamed from: getTop-impl, reason: not valid java name */
    public static final float m2840getTopimpl(long j) {
        return Companion.fromBits(VRectKt.m2859unpackTopVKZWuLQ(j));
    }

    /* renamed from: getWidth-impl, reason: not valid java name */
    public static final float m2841getWidthimpl(long j) {
        return m2838getRightimpl(j) - m2837getLeftimpl(j);
    }

    /* renamed from: hashCode-impl, reason: not valid java name */
    public static int m2842hashCodeimpl(long j) {
        int i = ULong.$r8$clinit;
        return Long.hashCode(j);
    }

    /* renamed from: toRectF-impl, reason: not valid java name */
    public static final RectF m2844toRectFimpl(long j) {
        return new RectF(m2837getLeftimpl(j), m2840getTopimpl(j), m2838getRightimpl(j), m2834getBottomimpl(j));
    }

    /* renamed from: toString-impl, reason: not valid java name */
    public static String m2845toStringimpl(long j) {
        float fM2837getLeftimpl = m2837getLeftimpl(j);
        float fM2840getTopimpl = m2840getTopimpl(j);
        float fM2838getRightimpl = m2838getRightimpl(j);
        float fM2834getBottomimpl = m2834getBottomimpl(j);
        StringBuilder sbM = CubicBezierEasing$$ExternalSyntheticOutline0.m("(", fM2837getLeftimpl, ", ", fM2840getTopimpl, ") -> (");
        sbM.append(fM2838getRightimpl);
        sbM.append(", ");
        sbM.append(fM2834getBottomimpl);
        sbM.append(")");
        return sbM.toString();
    }

    public boolean equals(Object obj) {
        return m2832equalsimpl(this.data, obj);
    }

    /* renamed from: getData-s-VKNKU, reason: not valid java name */
    public final long m2846getDatasVKNKU() {
        return this.data;
    }

    public int hashCode() {
        return m2842hashCodeimpl(this.data);
    }

    public String toString() {
        return m2845toStringimpl(this.data);
    }

    /* renamed from: unbox-impl, reason: not valid java name */
    public final /* synthetic */ long m2847unboximpl() {
        return this.data;
    }

    /* renamed from: constructor-impl, reason: not valid java name */
    public static long m2831constructorimpl(RectF rectF) {
        return m2828constructorimpl(rectF.left, rectF.top, rectF.right, rectF.bottom);
    }

    /* renamed from: constructor-impl, reason: not valid java name */
    public static long m2830constructorimpl(Rect rect) {
        return m2828constructorimpl(rect.left, rect.top, rect.right, rect.bottom);
    }

    /* renamed from: constructor-impl, reason: not valid java name */
    public static long m2828constructorimpl(float f, float f2, float f3, float f4) {
        Companion companion = Companion;
        return m2829constructorimpl(VRectKt.pack(companion.toBits(f), companion.toBits(f2), companion.toBits(f3), companion.toBits(f4)));
    }

    /* renamed from: toLong-impl, reason: not valid java name */
    public static final long m2843toLongimpl(long j) {
        return j;
    }
}
