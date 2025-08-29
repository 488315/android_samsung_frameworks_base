package com.android.systemui.plugins.clocks;

import android.graphics.PointF;
import android.graphics.RectF;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.FloatCompanionObject;

/* loaded from: classes2.dex */
public final class VPointF {
    public static final Companion Companion = new Companion(null);
    private static final long ZERO = m2750constructorimpl(0, 0);
    private final long data;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* renamed from: div-NvxBqkk, reason: not valid java name */
        public final long m2784divNvxBqkk(int i, long j) {
            float f = i;
            return VPointF.m2747constructorimpl(f / VPointF.m2760getXimpl(j), f / VPointF.m2761getYimpl(j));
        }

        /* renamed from: fromLong--AsyRdg, reason: not valid java name */
        public final long m2785fromLongAsyRdg(long j) {
            int i = ULong.$r8$clinit;
            return VPointF.m2751constructorimpl(j);
        }

        /* renamed from: getCenter--AsyRdg, reason: not valid java name */
        public final long m2786getCenterAsyRdg(RectF rectF) {
            return VPointF.m2747constructorimpl(rectF.centerX(), rectF.centerY());
        }

        /* renamed from: getSize--AsyRdg, reason: not valid java name */
        public final long m2787getSizeAsyRdg(RectF rectF) {
            return VPointF.m2747constructorimpl(rectF.width(), rectF.height());
        }

        /* renamed from: getZERO-Jv7bpU8, reason: not valid java name */
        public final long m2788getZEROJv7bpU8() {
            return VPointF.ZERO;
        }

        /* renamed from: max-5C5yMpM, reason: not valid java name */
        public final long m2789max5C5yMpM(long j, long j2) {
            return VPointF.m2747constructorimpl(Math.max(VPointF.m2760getXimpl(j), VPointF.m2760getXimpl(j2)), Math.max(VPointF.m2761getYimpl(j), VPointF.m2761getYimpl(j2)));
        }

        /* renamed from: min-5C5yMpM, reason: not valid java name */
        public final long m2790min5C5yMpM(long j, long j2) {
            return VPointF.m2747constructorimpl(Math.min(VPointF.m2760getXimpl(j), VPointF.m2760getXimpl(j2)), Math.min(VPointF.m2761getYimpl(j), VPointF.m2761getYimpl(j2)));
        }

        /* renamed from: minus-NvxBqkk, reason: not valid java name */
        public final long m2792minusNvxBqkk(int i, long j) {
            float f = i;
            return VPointF.m2747constructorimpl(f - VPointF.m2760getXimpl(j), f - VPointF.m2761getYimpl(j));
        }

        /* renamed from: plus-NvxBqkk, reason: not valid java name */
        public final long m2793plusNvxBqkk(float f, long j) {
            return VPointF.m2747constructorimpl(VPointF.m2760getXimpl(j) + f, VPointF.m2761getYimpl(j) + f);
        }

        /* renamed from: times-NvxBqkk, reason: not valid java name */
        public final long m2795timesNvxBqkk(int i, long j) {
            float f = i;
            return VPointF.m2747constructorimpl(VPointF.m2760getXimpl(j) * f, VPointF.m2761getYimpl(j) * f);
        }

        private Companion() {
        }

        /* renamed from: div-NvxBqkk, reason: not valid java name */
        public final long m2783divNvxBqkk(float f, long j) {
            return VPointF.m2747constructorimpl(f / VPointF.m2760getXimpl(j), f / VPointF.m2761getYimpl(j));
        }

        /* renamed from: minus-NvxBqkk, reason: not valid java name */
        public final long m2791minusNvxBqkk(float f, long j) {
            return VPointF.m2747constructorimpl(f - VPointF.m2760getXimpl(j), f - VPointF.m2761getYimpl(j));
        }

        /* renamed from: times-NvxBqkk, reason: not valid java name */
        public final long m2794timesNvxBqkk(float f, long j) {
            return VPointF.m2747constructorimpl(VPointF.m2760getXimpl(j) * f, VPointF.m2761getYimpl(j) * f);
        }
    }

    private /* synthetic */ VPointF(long j) {
        this.data = j;
    }

    /* renamed from: abs-Jv7bpU8, reason: not valid java name */
    public static final long m2743absJv7bpU8(long j) {
        return m2747constructorimpl(Math.abs(m2760getXimpl(j)), Math.abs(m2761getYimpl(j)));
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ VPointF m2744boximpl(long j) {
        return new VPointF(j);
    }

    /* renamed from: component1-impl, reason: not valid java name */
    public static final float m2745component1impl(long j) {
        return m2760getXimpl(j);
    }

    /* renamed from: component2-impl, reason: not valid java name */
    public static final float m2746component2impl(long j) {
        return m2761getYimpl(j);
    }

    /* renamed from: constructor-impl, reason: not valid java name */
    public static long m2751constructorimpl(long j) {
        return j;
    }

    /* renamed from: div--AsyRdg, reason: not valid java name */
    public static final long m2754divAsyRdg(long j, int i) {
        float f = i;
        return m2747constructorimpl(m2760getXimpl(j) / f, m2761getYimpl(j) / f);
    }

    /* renamed from: div-b2IjXjg, reason: not valid java name */
    public static final long m2755divb2IjXjg(long j, long j2) {
        return m2747constructorimpl(m2760getXimpl(j) / m2760getXimpl(j2), m2761getYimpl(j) / m2761getYimpl(j2));
    }

    /* renamed from: div-tHZnGzg, reason: not valid java name */
    public static final long m2756divtHZnGzg(long j, long j2) {
        return m2747constructorimpl(m2760getXimpl(j) / VPoint.m2709getXimpl(j2), m2761getYimpl(j) / VPoint.m2710getYimpl(j2));
    }

    /* renamed from: dot-78DuFIo, reason: not valid java name */
    public static final float m2757dot78DuFIo(long j, long j2) {
        return (m2761getYimpl(j2) * m2761getYimpl(j)) + (m2760getXimpl(j2) * m2760getXimpl(j));
    }

    /* renamed from: equals-impl, reason: not valid java name */
    public static boolean m2758equalsimpl(long j, Object obj) {
        return (obj instanceof VPointF) && j == ((VPointF) obj).m2782unboximpl();
    }

    /* renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m2759equalsimpl0(long j, long j2) {
        return ULong.m3446equalsimpl0(j, j2);
    }

    /* renamed from: getX-impl, reason: not valid java name */
    public static final float m2760getXimpl(long j) {
        FloatCompanionObject floatCompanionObject = FloatCompanionObject.INSTANCE;
        return Float.intBitsToFloat(VPointKt.m2798unpackXVKZWuLQ(j));
    }

    /* renamed from: getY-impl, reason: not valid java name */
    public static final float m2761getYimpl(long j) {
        FloatCompanionObject floatCompanionObject = FloatCompanionObject.INSTANCE;
        return Float.intBitsToFloat(VPointKt.m2799unpackYVKZWuLQ(j));
    }

    /* renamed from: hashCode-impl, reason: not valid java name */
    public static int m2762hashCodeimpl(long j) {
        int i = ULong.$r8$clinit;
        return Long.hashCode(j);
    }

    /* renamed from: length-impl, reason: not valid java name */
    public static final float m2763lengthimpl(long j) {
        return (float) Math.sqrt(m2764lengthSqimpl(j));
    }

    /* renamed from: lengthSq-impl, reason: not valid java name */
    public static final float m2764lengthSqimpl(long j) {
        return (m2761getYimpl(j) * m2761getYimpl(j)) + (m2760getXimpl(j) * m2760getXimpl(j));
    }

    /* renamed from: minus--AsyRdg, reason: not valid java name */
    public static final long m2766minusAsyRdg(long j, int i) {
        float f = i;
        return m2747constructorimpl(m2760getXimpl(j) - f, m2761getYimpl(j) - f);
    }

    /* renamed from: minus-b2IjXjg, reason: not valid java name */
    public static final long m2767minusb2IjXjg(long j, long j2) {
        return m2747constructorimpl(m2760getXimpl(j) - m2760getXimpl(j2), m2761getYimpl(j) - m2761getYimpl(j2));
    }

    /* renamed from: minus-tHZnGzg, reason: not valid java name */
    public static final long m2768minustHZnGzg(long j, long j2) {
        return m2747constructorimpl(m2760getXimpl(j) - VPoint.m2709getXimpl(j2), m2761getYimpl(j) - VPoint.m2710getYimpl(j2));
    }

    /* renamed from: normalize-Jv7bpU8, reason: not valid java name */
    public static final long m2769normalizeJv7bpU8(long j) {
        float fM2763lengthimpl = m2763lengthimpl(j);
        return m2747constructorimpl(m2760getXimpl(j) / fM2763lengthimpl, m2761getYimpl(j) / fM2763lengthimpl);
    }

    /* renamed from: plus--AsyRdg, reason: not valid java name */
    public static final long m2771plusAsyRdg(long j, int i) {
        float f = i;
        return m2747constructorimpl(m2760getXimpl(j) + f, m2761getYimpl(j) + f);
    }

    /* renamed from: plus-b2IjXjg, reason: not valid java name */
    public static final long m2772plusb2IjXjg(long j, long j2) {
        return m2747constructorimpl(m2760getXimpl(j2) + m2760getXimpl(j), m2761getYimpl(j2) + m2761getYimpl(j));
    }

    /* renamed from: plus-tHZnGzg, reason: not valid java name */
    public static final long m2773plustHZnGzg(long j, long j2) {
        return m2747constructorimpl(m2760getXimpl(j) + VPoint.m2709getXimpl(j2), m2761getYimpl(j) + VPoint.m2710getYimpl(j2));
    }

    /* renamed from: times--AsyRdg, reason: not valid java name */
    public static final long m2775timesAsyRdg(long j, int i) {
        float f = i;
        return m2747constructorimpl(m2760getXimpl(j) * f, m2761getYimpl(j) * f);
    }

    /* renamed from: times-b2IjXjg, reason: not valid java name */
    public static final long m2776timesb2IjXjg(long j, long j2) {
        return m2747constructorimpl(m2760getXimpl(j2) * m2760getXimpl(j), m2761getYimpl(j2) * m2761getYimpl(j));
    }

    /* renamed from: times-tHZnGzg, reason: not valid java name */
    public static final long m2777timestHZnGzg(long j, long j2) {
        return m2747constructorimpl(m2760getXimpl(j) * VPoint.m2709getXimpl(j2), m2761getYimpl(j) * VPoint.m2710getYimpl(j2));
    }

    /* renamed from: toPointF-impl, reason: not valid java name */
    public static final PointF m2779toPointFimpl(long j) {
        return new PointF(m2760getXimpl(j), m2761getYimpl(j));
    }

    /* renamed from: toString-impl, reason: not valid java name */
    public static String m2780toStringimpl(long j) {
        return "(" + m2760getXimpl(j) + ", " + m2761getYimpl(j) + ")";
    }

    public boolean equals(Object obj) {
        return m2758equalsimpl(this.data, obj);
    }

    /* renamed from: getData-s-VKNKU, reason: not valid java name */
    public final long m2781getDatasVKNKU() {
        return this.data;
    }

    public int hashCode() {
        return m2762hashCodeimpl(this.data);
    }

    public String toString() {
        return m2780toStringimpl(this.data);
    }

    /* renamed from: unbox-impl, reason: not valid java name */
    public final /* synthetic */ long m2782unboximpl() {
        return this.data;
    }

    /* renamed from: constructor-impl, reason: not valid java name */
    public static long m2752constructorimpl(PointF pointF) {
        return m2747constructorimpl(pointF.x, pointF.y);
    }

    /* renamed from: div--AsyRdg, reason: not valid java name */
    public static final long m2753divAsyRdg(long j, float f) {
        return m2747constructorimpl(m2760getXimpl(j) / f, m2761getYimpl(j) / f);
    }

    /* renamed from: minus--AsyRdg, reason: not valid java name */
    public static final long m2765minusAsyRdg(long j, float f) {
        return m2747constructorimpl(m2760getXimpl(j) - f, m2761getYimpl(j) - f);
    }

    /* renamed from: plus--AsyRdg, reason: not valid java name */
    public static final long m2770plusAsyRdg(long j, float f) {
        return m2747constructorimpl(m2760getXimpl(j) + f, m2761getYimpl(j) + f);
    }

    /* renamed from: times--AsyRdg, reason: not valid java name */
    public static final long m2774timesAsyRdg(long j, float f) {
        return m2747constructorimpl(m2760getXimpl(j) * f, m2761getYimpl(j) * f);
    }

    /* renamed from: constructor-impl, reason: not valid java name */
    public static long m2750constructorimpl(int i, int i2) {
        return m2747constructorimpl(i, i2);
    }

    /* renamed from: constructor-impl, reason: not valid java name */
    public static long m2749constructorimpl(int i, float f) {
        return m2747constructorimpl(i, f);
    }

    /* renamed from: constructor-impl, reason: not valid java name */
    public static long m2748constructorimpl(float f, int i) {
        return m2747constructorimpl(f, i);
    }

    /* renamed from: constructor-impl, reason: not valid java name */
    public static long m2747constructorimpl(float f, float f2) {
        return m2751constructorimpl(VPointKt.pack(Float.floatToIntBits(f), Float.floatToIntBits(f2)));
    }

    /* renamed from: toLong-impl, reason: not valid java name */
    public static final long m2778toLongimpl(long j) {
        return j;
    }
}
