package com.android.systemui.plugins.clocks;

import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Half;
import androidx.compose.animation.core.CubicBezierEasing$$ExternalSyntheticOutline0;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class VRectF {
    public static final Companion Companion = new Companion(null);
    private static final long ZERO = m2810constructorimpl(0.0f, 0.0f, 0.0f, 0.0f);
    private final long data;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        public final long m2830fromCentercwyIbD4(long j, long j2) {
            float f = 2;
            return VRectF.m2810constructorimpl(VPointF.m2744getXimpl(j) - (VPointF.m2744getXimpl(j2) / f), VPointF.m2745getYimpl(j) - (VPointF.m2745getYimpl(j2) / f), (VPointF.m2744getXimpl(j2) / f) + VPointF.m2744getXimpl(j), (VPointF.m2745getYimpl(j2) / f) + VPointF.m2745getYimpl(j));
        }

        /* renamed from: fromLong-WMibXUk, reason: not valid java name */
        public final long m2831fromLongWMibXUk(long j) {
            int i = ULong.$r8$clinit;
            return VRectF.m2811constructorimpl(j);
        }

        /* renamed from: fromTopLeft-cwyIbD4, reason: not valid java name */
        public final long m2832fromTopLeftcwyIbD4(long j, long j2) {
            return VRectF.m2810constructorimpl(VPointF.m2744getXimpl(j), VPointF.m2745getYimpl(j), VPointF.m2744getXimpl(j2) + VPointF.m2744getXimpl(j), VPointF.m2745getYimpl(j2) + VPointF.m2745getYimpl(j));
        }

        /* renamed from: getZERO-3Hl7r_E, reason: not valid java name */
        public final long m2833getZERO3Hl7r_E() {
            return VRectF.ZERO;
        }

        private Companion() {
        }
    }

    private /* synthetic */ VRectF(long j) {
        this.data = j;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ VRectF m2809boximpl(long j) {
        return new VRectF(j);
    }

    /* renamed from: constructor-impl, reason: not valid java name */
    public static long m2811constructorimpl(long j) {
        return j;
    }

    /* renamed from: equals-impl, reason: not valid java name */
    public static boolean m2814equalsimpl(long j, Object obj) {
        return (obj instanceof VRectF) && j == ((VRectF) obj).m2829unboximpl();
    }

    /* renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m2815equalsimpl0(long j, long j2) {
        return ULong.m3427equalsimpl0(j, j2);
    }

    /* renamed from: getBottom-impl, reason: not valid java name */
    public static final float m2816getBottomimpl(long j) {
        short m2838unpackBottomVKZWuLQ;
        Companion companion = Companion;
        m2838unpackBottomVKZWuLQ = VRectKt.m2838unpackBottomVKZWuLQ(j);
        return companion.fromBits(m2838unpackBottomVKZWuLQ);
    }

    /* renamed from: getCenter-Jv7bpU8, reason: not valid java name */
    public static final long m2817getCenterJv7bpU8(long j) {
        return VPointF.m2756plusb2IjXjg(VPointF.m2731constructorimpl(m2819getLeftimpl(j), m2822getTopimpl(j)), VPointF.m2737divAsyRdg(m2821getSizeJv7bpU8(j), 2.0f));
    }

    /* renamed from: getHeight-impl, reason: not valid java name */
    public static final float m2818getHeightimpl(long j) {
        return m2816getBottomimpl(j) - m2822getTopimpl(j);
    }

    /* renamed from: getLeft-impl, reason: not valid java name */
    public static final float m2819getLeftimpl(long j) {
        short m2839unpackLeftVKZWuLQ;
        Companion companion = Companion;
        m2839unpackLeftVKZWuLQ = VRectKt.m2839unpackLeftVKZWuLQ(j);
        return companion.fromBits(m2839unpackLeftVKZWuLQ);
    }

    /* renamed from: getRight-impl, reason: not valid java name */
    public static final float m2820getRightimpl(long j) {
        short m2840unpackRightVKZWuLQ;
        Companion companion = Companion;
        m2840unpackRightVKZWuLQ = VRectKt.m2840unpackRightVKZWuLQ(j);
        return companion.fromBits(m2840unpackRightVKZWuLQ);
    }

    /* renamed from: getSize-Jv7bpU8, reason: not valid java name */
    public static final long m2821getSizeJv7bpU8(long j) {
        return VPointF.m2731constructorimpl(m2823getWidthimpl(j), m2818getHeightimpl(j));
    }

    /* renamed from: getTop-impl, reason: not valid java name */
    public static final float m2822getTopimpl(long j) {
        short m2841unpackTopVKZWuLQ;
        Companion companion = Companion;
        m2841unpackTopVKZWuLQ = VRectKt.m2841unpackTopVKZWuLQ(j);
        return companion.fromBits(m2841unpackTopVKZWuLQ);
    }

    /* renamed from: getWidth-impl, reason: not valid java name */
    public static final float m2823getWidthimpl(long j) {
        return m2820getRightimpl(j) - m2819getLeftimpl(j);
    }

    /* renamed from: hashCode-impl, reason: not valid java name */
    public static int m2824hashCodeimpl(long j) {
        int i = ULong.$r8$clinit;
        return Long.hashCode(j);
    }

    /* renamed from: toRectF-impl, reason: not valid java name */
    public static final RectF m2826toRectFimpl(long j) {
        return new RectF(m2819getLeftimpl(j), m2822getTopimpl(j), m2820getRightimpl(j), m2816getBottomimpl(j));
    }

    /* renamed from: toString-impl, reason: not valid java name */
    public static String m2827toStringimpl(long j) {
        float m2819getLeftimpl = m2819getLeftimpl(j);
        float m2822getTopimpl = m2822getTopimpl(j);
        float m2820getRightimpl = m2820getRightimpl(j);
        float m2816getBottomimpl = m2816getBottomimpl(j);
        StringBuilder m = CubicBezierEasing$$ExternalSyntheticOutline0.m("(", m2819getLeftimpl, ", ", m2822getTopimpl, ") -> (");
        m.append(m2820getRightimpl);
        m.append(", ");
        m.append(m2816getBottomimpl);
        m.append(")");
        return m.toString();
    }

    public boolean equals(Object obj) {
        return m2814equalsimpl(this.data, obj);
    }

    /* renamed from: getData-s-VKNKU, reason: not valid java name */
    public final long m2828getDatasVKNKU() {
        return this.data;
    }

    public int hashCode() {
        return m2824hashCodeimpl(this.data);
    }

    public String toString() {
        return m2827toStringimpl(this.data);
    }

    /* renamed from: unbox-impl, reason: not valid java name */
    public final /* synthetic */ long m2829unboximpl() {
        return this.data;
    }

    /* renamed from: constructor-impl, reason: not valid java name */
    public static long m2813constructorimpl(RectF rectF) {
        return m2810constructorimpl(rectF.left, rectF.top, rectF.right, rectF.bottom);
    }

    /* renamed from: constructor-impl, reason: not valid java name */
    public static long m2812constructorimpl(Rect rect) {
        return m2810constructorimpl(rect.left, rect.top, rect.right, rect.bottom);
    }

    /* renamed from: constructor-impl, reason: not valid java name */
    public static long m2810constructorimpl(float f, float f2, float f3, float f4) {
        long pack;
        Companion companion = Companion;
        pack = VRectKt.pack(companion.toBits(f), companion.toBits(f2), companion.toBits(f3), companion.toBits(f4));
        return m2811constructorimpl(pack);
    }

    /* renamed from: toLong-impl, reason: not valid java name */
    public static final long m2825toLongimpl(long j) {
        return j;
    }
}
