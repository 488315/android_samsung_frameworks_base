package com.android.systemui.plugins.clocks;

import android.graphics.PointF;
import android.graphics.RectF;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.FloatCompanionObject;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class VPointF {
    public static final Companion Companion = new Companion(null);
    private static final long ZERO = m2734constructorimpl(0, 0);
    private final long data;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* renamed from: div-NvxBqkk, reason: not valid java name */
        public final long m2768divNvxBqkk(int i, long j) {
            float f = i;
            return VPointF.m2731constructorimpl(f / VPointF.m2744getXimpl(j), f / VPointF.m2745getYimpl(j));
        }

        /* renamed from: fromLong--AsyRdg, reason: not valid java name */
        public final long m2769fromLongAsyRdg(long j) {
            int i = ULong.$r8$clinit;
            return VPointF.m2735constructorimpl(j);
        }

        /* renamed from: getCenter--AsyRdg, reason: not valid java name */
        public final long m2770getCenterAsyRdg(RectF rectF) {
            return VPointF.m2731constructorimpl(rectF.centerX(), rectF.centerY());
        }

        /* renamed from: getSize--AsyRdg, reason: not valid java name */
        public final long m2771getSizeAsyRdg(RectF rectF) {
            return VPointF.m2731constructorimpl(rectF.width(), rectF.height());
        }

        /* renamed from: getZERO-Jv7bpU8, reason: not valid java name */
        public final long m2772getZEROJv7bpU8() {
            return VPointF.ZERO;
        }

        /* renamed from: max-5C5yMpM, reason: not valid java name */
        public final long m2773max5C5yMpM(long j, long j2) {
            return VPointF.m2731constructorimpl(Math.max(VPointF.m2744getXimpl(j), VPointF.m2744getXimpl(j2)), Math.max(VPointF.m2745getYimpl(j), VPointF.m2745getYimpl(j2)));
        }

        /* renamed from: min-5C5yMpM, reason: not valid java name */
        public final long m2774min5C5yMpM(long j, long j2) {
            return VPointF.m2731constructorimpl(Math.min(VPointF.m2744getXimpl(j), VPointF.m2744getXimpl(j2)), Math.min(VPointF.m2745getYimpl(j), VPointF.m2745getYimpl(j2)));
        }

        /* renamed from: minus-NvxBqkk, reason: not valid java name */
        public final long m2776minusNvxBqkk(int i, long j) {
            float f = i;
            return VPointF.m2731constructorimpl(f - VPointF.m2744getXimpl(j), f - VPointF.m2745getYimpl(j));
        }

        /* renamed from: plus-NvxBqkk, reason: not valid java name */
        public final long m2777plusNvxBqkk(float f, long j) {
            return VPointF.m2731constructorimpl(VPointF.m2744getXimpl(j) + f, VPointF.m2745getYimpl(j) + f);
        }

        /* renamed from: times-NvxBqkk, reason: not valid java name */
        public final long m2779timesNvxBqkk(int i, long j) {
            float f = i;
            return VPointF.m2731constructorimpl(VPointF.m2744getXimpl(j) * f, VPointF.m2745getYimpl(j) * f);
        }

        private Companion() {
        }

        /* renamed from: div-NvxBqkk, reason: not valid java name */
        public final long m2767divNvxBqkk(float f, long j) {
            return VPointF.m2731constructorimpl(f / VPointF.m2744getXimpl(j), f / VPointF.m2745getYimpl(j));
        }

        /* renamed from: minus-NvxBqkk, reason: not valid java name */
        public final long m2775minusNvxBqkk(float f, long j) {
            return VPointF.m2731constructorimpl(f - VPointF.m2744getXimpl(j), f - VPointF.m2745getYimpl(j));
        }

        /* renamed from: times-NvxBqkk, reason: not valid java name */
        public final long m2778timesNvxBqkk(float f, long j) {
            return VPointF.m2731constructorimpl(VPointF.m2744getXimpl(j) * f, VPointF.m2745getYimpl(j) * f);
        }
    }

    private /* synthetic */ VPointF(long j) {
        this.data = j;
    }

    /* renamed from: abs-Jv7bpU8, reason: not valid java name */
    public static final long m2727absJv7bpU8(long j) {
        return m2731constructorimpl(Math.abs(m2744getXimpl(j)), Math.abs(m2745getYimpl(j)));
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ VPointF m2728boximpl(long j) {
        return new VPointF(j);
    }

    /* renamed from: component1-impl, reason: not valid java name */
    public static final float m2729component1impl(long j) {
        return m2744getXimpl(j);
    }

    /* renamed from: component2-impl, reason: not valid java name */
    public static final float m2730component2impl(long j) {
        return m2745getYimpl(j);
    }

    /* renamed from: constructor-impl, reason: not valid java name */
    public static long m2735constructorimpl(long j) {
        return j;
    }

    /* renamed from: div--AsyRdg, reason: not valid java name */
    public static final long m2738divAsyRdg(long j, int i) {
        float f = i;
        return m2731constructorimpl(m2744getXimpl(j) / f, m2745getYimpl(j) / f);
    }

    /* renamed from: div-b2IjXjg, reason: not valid java name */
    public static final long m2739divb2IjXjg(long j, long j2) {
        return m2731constructorimpl(m2744getXimpl(j) / m2744getXimpl(j2), m2745getYimpl(j) / m2745getYimpl(j2));
    }

    /* renamed from: div-tHZnGzg, reason: not valid java name */
    public static final long m2740divtHZnGzg(long j, long j2) {
        return m2731constructorimpl(m2744getXimpl(j) / VPoint.m2693getXimpl(j2), m2745getYimpl(j) / VPoint.m2694getYimpl(j2));
    }

    /* renamed from: dot-78DuFIo, reason: not valid java name */
    public static final float m2741dot78DuFIo(long j, long j2) {
        return (m2745getYimpl(j2) * m2745getYimpl(j)) + (m2744getXimpl(j2) * m2744getXimpl(j));
    }

    /* renamed from: equals-impl, reason: not valid java name */
    public static boolean m2742equalsimpl(long j, Object obj) {
        return (obj instanceof VPointF) && j == ((VPointF) obj).m2766unboximpl();
    }

    /* renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m2743equalsimpl0(long j, long j2) {
        return ULong.m3427equalsimpl0(j, j2);
    }

    /* renamed from: getX-impl, reason: not valid java name */
    public static final float m2744getXimpl(long j) {
        int m2782unpackXVKZWuLQ;
        FloatCompanionObject floatCompanionObject = FloatCompanionObject.INSTANCE;
        m2782unpackXVKZWuLQ = VPointKt.m2782unpackXVKZWuLQ(j);
        return Float.intBitsToFloat(m2782unpackXVKZWuLQ);
    }

    /* renamed from: getY-impl, reason: not valid java name */
    public static final float m2745getYimpl(long j) {
        int m2783unpackYVKZWuLQ;
        FloatCompanionObject floatCompanionObject = FloatCompanionObject.INSTANCE;
        m2783unpackYVKZWuLQ = VPointKt.m2783unpackYVKZWuLQ(j);
        return Float.intBitsToFloat(m2783unpackYVKZWuLQ);
    }

    /* renamed from: hashCode-impl, reason: not valid java name */
    public static int m2746hashCodeimpl(long j) {
        int i = ULong.$r8$clinit;
        return Long.hashCode(j);
    }

    /* renamed from: length-impl, reason: not valid java name */
    public static final float m2747lengthimpl(long j) {
        return (float) Math.sqrt(m2748lengthSqimpl(j));
    }

    /* renamed from: lengthSq-impl, reason: not valid java name */
    public static final float m2748lengthSqimpl(long j) {
        return (m2745getYimpl(j) * m2745getYimpl(j)) + (m2744getXimpl(j) * m2744getXimpl(j));
    }

    /* renamed from: minus--AsyRdg, reason: not valid java name */
    public static final long m2750minusAsyRdg(long j, int i) {
        float f = i;
        return m2731constructorimpl(m2744getXimpl(j) - f, m2745getYimpl(j) - f);
    }

    /* renamed from: minus-b2IjXjg, reason: not valid java name */
    public static final long m2751minusb2IjXjg(long j, long j2) {
        return m2731constructorimpl(m2744getXimpl(j) - m2744getXimpl(j2), m2745getYimpl(j) - m2745getYimpl(j2));
    }

    /* renamed from: minus-tHZnGzg, reason: not valid java name */
    public static final long m2752minustHZnGzg(long j, long j2) {
        return m2731constructorimpl(m2744getXimpl(j) - VPoint.m2693getXimpl(j2), m2745getYimpl(j) - VPoint.m2694getYimpl(j2));
    }

    /* renamed from: normalize-Jv7bpU8, reason: not valid java name */
    public static final long m2753normalizeJv7bpU8(long j) {
        float m2747lengthimpl = m2747lengthimpl(j);
        return m2731constructorimpl(m2744getXimpl(j) / m2747lengthimpl, m2745getYimpl(j) / m2747lengthimpl);
    }

    /* renamed from: plus--AsyRdg, reason: not valid java name */
    public static final long m2755plusAsyRdg(long j, int i) {
        float f = i;
        return m2731constructorimpl(m2744getXimpl(j) + f, m2745getYimpl(j) + f);
    }

    /* renamed from: plus-b2IjXjg, reason: not valid java name */
    public static final long m2756plusb2IjXjg(long j, long j2) {
        return m2731constructorimpl(m2744getXimpl(j2) + m2744getXimpl(j), m2745getYimpl(j2) + m2745getYimpl(j));
    }

    /* renamed from: plus-tHZnGzg, reason: not valid java name */
    public static final long m2757plustHZnGzg(long j, long j2) {
        return m2731constructorimpl(m2744getXimpl(j) + VPoint.m2693getXimpl(j2), m2745getYimpl(j) + VPoint.m2694getYimpl(j2));
    }

    /* renamed from: times--AsyRdg, reason: not valid java name */
    public static final long m2759timesAsyRdg(long j, int i) {
        float f = i;
        return m2731constructorimpl(m2744getXimpl(j) * f, m2745getYimpl(j) * f);
    }

    /* renamed from: times-b2IjXjg, reason: not valid java name */
    public static final long m2760timesb2IjXjg(long j, long j2) {
        return m2731constructorimpl(m2744getXimpl(j2) * m2744getXimpl(j), m2745getYimpl(j2) * m2745getYimpl(j));
    }

    /* renamed from: times-tHZnGzg, reason: not valid java name */
    public static final long m2761timestHZnGzg(long j, long j2) {
        return m2731constructorimpl(m2744getXimpl(j) * VPoint.m2693getXimpl(j2), m2745getYimpl(j) * VPoint.m2694getYimpl(j2));
    }

    /* renamed from: toPointF-impl, reason: not valid java name */
    public static final PointF m2763toPointFimpl(long j) {
        return new PointF(m2744getXimpl(j), m2745getYimpl(j));
    }

    /* renamed from: toString-impl, reason: not valid java name */
    public static String m2764toStringimpl(long j) {
        return "(" + m2744getXimpl(j) + ", " + m2745getYimpl(j) + ")";
    }

    public boolean equals(Object obj) {
        return m2742equalsimpl(this.data, obj);
    }

    /* renamed from: getData-s-VKNKU, reason: not valid java name */
    public final long m2765getDatasVKNKU() {
        return this.data;
    }

    public int hashCode() {
        return m2746hashCodeimpl(this.data);
    }

    public String toString() {
        return m2764toStringimpl(this.data);
    }

    /* renamed from: unbox-impl, reason: not valid java name */
    public final /* synthetic */ long m2766unboximpl() {
        return this.data;
    }

    /* renamed from: constructor-impl, reason: not valid java name */
    public static long m2736constructorimpl(PointF pointF) {
        return m2731constructorimpl(pointF.x, pointF.y);
    }

    /* renamed from: div--AsyRdg, reason: not valid java name */
    public static final long m2737divAsyRdg(long j, float f) {
        return m2731constructorimpl(m2744getXimpl(j) / f, m2745getYimpl(j) / f);
    }

    /* renamed from: minus--AsyRdg, reason: not valid java name */
    public static final long m2749minusAsyRdg(long j, float f) {
        return m2731constructorimpl(m2744getXimpl(j) - f, m2745getYimpl(j) - f);
    }

    /* renamed from: plus--AsyRdg, reason: not valid java name */
    public static final long m2754plusAsyRdg(long j, float f) {
        return m2731constructorimpl(m2744getXimpl(j) + f, m2745getYimpl(j) + f);
    }

    /* renamed from: times--AsyRdg, reason: not valid java name */
    public static final long m2758timesAsyRdg(long j, float f) {
        return m2731constructorimpl(m2744getXimpl(j) * f, m2745getYimpl(j) * f);
    }

    /* renamed from: constructor-impl, reason: not valid java name */
    public static long m2734constructorimpl(int i, int i2) {
        return m2731constructorimpl(i, i2);
    }

    /* renamed from: constructor-impl, reason: not valid java name */
    public static long m2733constructorimpl(int i, float f) {
        return m2731constructorimpl(i, f);
    }

    /* renamed from: constructor-impl, reason: not valid java name */
    public static long m2732constructorimpl(float f, int i) {
        return m2731constructorimpl(f, i);
    }

    /* renamed from: constructor-impl, reason: not valid java name */
    public static long m2731constructorimpl(float f, float f2) {
        long pack;
        pack = VPointKt.pack(Float.floatToIntBits(f), Float.floatToIntBits(f2));
        return m2735constructorimpl(pack);
    }

    /* renamed from: toLong-impl, reason: not valid java name */
    public static final long m2762toLongimpl(long j) {
        return j;
    }
}
