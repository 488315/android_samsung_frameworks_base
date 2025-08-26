package com.android.systemui.plugins.clocks;

import android.graphics.PointF;
import android.graphics.RectF;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.FloatCompanionObject;

/* loaded from: classes2.dex */
public final class VPointF {
    public static final Companion Companion = new Companion(null);
    private static final long ZERO = m2752constructorimpl(0, 0);
    private final long data;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* renamed from: div-NvxBqkk, reason: not valid java name */
        public final long m2786divNvxBqkk(int i, long j) {
            float f = i;
            return VPointF.m2749constructorimpl(f / VPointF.m2762getXimpl(j), f / VPointF.m2763getYimpl(j));
        }

        /* renamed from: fromLong--AsyRdg, reason: not valid java name */
        public final long m2787fromLongAsyRdg(long j) {
            int i = ULong.$r8$clinit;
            return VPointF.m2753constructorimpl(j);
        }

        /* renamed from: getCenter--AsyRdg, reason: not valid java name */
        public final long m2788getCenterAsyRdg(RectF rectF) {
            return VPointF.m2749constructorimpl(rectF.centerX(), rectF.centerY());
        }

        /* renamed from: getSize--AsyRdg, reason: not valid java name */
        public final long m2789getSizeAsyRdg(RectF rectF) {
            return VPointF.m2749constructorimpl(rectF.width(), rectF.height());
        }

        /* renamed from: getZERO-Jv7bpU8, reason: not valid java name */
        public final long m2790getZEROJv7bpU8() {
            return VPointF.ZERO;
        }

        /* renamed from: max-5C5yMpM, reason: not valid java name */
        public final long m2791max5C5yMpM(long j, long j2) {
            return VPointF.m2749constructorimpl(Math.max(VPointF.m2762getXimpl(j), VPointF.m2762getXimpl(j2)), Math.max(VPointF.m2763getYimpl(j), VPointF.m2763getYimpl(j2)));
        }

        /* renamed from: min-5C5yMpM, reason: not valid java name */
        public final long m2792min5C5yMpM(long j, long j2) {
            return VPointF.m2749constructorimpl(Math.min(VPointF.m2762getXimpl(j), VPointF.m2762getXimpl(j2)), Math.min(VPointF.m2763getYimpl(j), VPointF.m2763getYimpl(j2)));
        }

        /* renamed from: minus-NvxBqkk, reason: not valid java name */
        public final long m2794minusNvxBqkk(int i, long j) {
            float f = i;
            return VPointF.m2749constructorimpl(f - VPointF.m2762getXimpl(j), f - VPointF.m2763getYimpl(j));
        }

        /* renamed from: plus-NvxBqkk, reason: not valid java name */
        public final long m2795plusNvxBqkk(float f, long j) {
            return VPointF.m2749constructorimpl(VPointF.m2762getXimpl(j) + f, VPointF.m2763getYimpl(j) + f);
        }

        /* renamed from: times-NvxBqkk, reason: not valid java name */
        public final long m2797timesNvxBqkk(int i, long j) {
            float f = i;
            return VPointF.m2749constructorimpl(VPointF.m2762getXimpl(j) * f, VPointF.m2763getYimpl(j) * f);
        }

        private Companion() {
        }

        /* renamed from: div-NvxBqkk, reason: not valid java name */
        public final long m2785divNvxBqkk(float f, long j) {
            return VPointF.m2749constructorimpl(f / VPointF.m2762getXimpl(j), f / VPointF.m2763getYimpl(j));
        }

        /* renamed from: minus-NvxBqkk, reason: not valid java name */
        public final long m2793minusNvxBqkk(float f, long j) {
            return VPointF.m2749constructorimpl(f - VPointF.m2762getXimpl(j), f - VPointF.m2763getYimpl(j));
        }

        /* renamed from: times-NvxBqkk, reason: not valid java name */
        public final long m2796timesNvxBqkk(float f, long j) {
            return VPointF.m2749constructorimpl(VPointF.m2762getXimpl(j) * f, VPointF.m2763getYimpl(j) * f);
        }
    }

    private /* synthetic */ VPointF(long j) {
        this.data = j;
    }

    /* renamed from: abs-Jv7bpU8, reason: not valid java name */
    public static final long m2745absJv7bpU8(long j) {
        return m2749constructorimpl(Math.abs(m2762getXimpl(j)), Math.abs(m2763getYimpl(j)));
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ VPointF m2746boximpl(long j) {
        return new VPointF(j);
    }

    /* renamed from: component1-impl, reason: not valid java name */
    public static final float m2747component1impl(long j) {
        return m2762getXimpl(j);
    }

    /* renamed from: component2-impl, reason: not valid java name */
    public static final float m2748component2impl(long j) {
        return m2763getYimpl(j);
    }

    /* renamed from: constructor-impl, reason: not valid java name */
    public static long m2753constructorimpl(long j) {
        return j;
    }

    /* renamed from: div--AsyRdg, reason: not valid java name */
    public static final long m2756divAsyRdg(long j, int i) {
        float f = i;
        return m2749constructorimpl(m2762getXimpl(j) / f, m2763getYimpl(j) / f);
    }

    /* renamed from: div-b2IjXjg, reason: not valid java name */
    public static final long m2757divb2IjXjg(long j, long j2) {
        return m2749constructorimpl(m2762getXimpl(j) / m2762getXimpl(j2), m2763getYimpl(j) / m2763getYimpl(j2));
    }

    /* renamed from: div-tHZnGzg, reason: not valid java name */
    public static final long m2758divtHZnGzg(long j, long j2) {
        return m2749constructorimpl(m2762getXimpl(j) / VPoint.m2711getXimpl(j2), m2763getYimpl(j) / VPoint.m2712getYimpl(j2));
    }

    /* renamed from: dot-78DuFIo, reason: not valid java name */
    public static final float m2759dot78DuFIo(long j, long j2) {
        return (m2763getYimpl(j2) * m2763getYimpl(j)) + (m2762getXimpl(j2) * m2762getXimpl(j));
    }

    /* renamed from: equals-impl, reason: not valid java name */
    public static boolean m2760equalsimpl(long j, Object obj) {
        return (obj instanceof VPointF) && j == ((VPointF) obj).m2784unboximpl();
    }

    /* renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m2761equalsimpl0(long j, long j2) {
        return ULong.m3447equalsimpl0(j, j2);
    }

    /* renamed from: getX-impl, reason: not valid java name */
    public static final float m2762getXimpl(long j) {
        FloatCompanionObject floatCompanionObject = FloatCompanionObject.INSTANCE;
        return Float.intBitsToFloat(VPointKt.m2800unpackXVKZWuLQ(j));
    }

    /* renamed from: getY-impl, reason: not valid java name */
    public static final float m2763getYimpl(long j) {
        FloatCompanionObject floatCompanionObject = FloatCompanionObject.INSTANCE;
        return Float.intBitsToFloat(VPointKt.m2801unpackYVKZWuLQ(j));
    }

    /* renamed from: hashCode-impl, reason: not valid java name */
    public static int m2764hashCodeimpl(long j) {
        int i = ULong.$r8$clinit;
        return Long.hashCode(j);
    }

    /* renamed from: length-impl, reason: not valid java name */
    public static final float m2765lengthimpl(long j) {
        return (float) Math.sqrt(m2766lengthSqimpl(j));
    }

    /* renamed from: lengthSq-impl, reason: not valid java name */
    public static final float m2766lengthSqimpl(long j) {
        return (m2763getYimpl(j) * m2763getYimpl(j)) + (m2762getXimpl(j) * m2762getXimpl(j));
    }

    /* renamed from: minus--AsyRdg, reason: not valid java name */
    public static final long m2768minusAsyRdg(long j, int i) {
        float f = i;
        return m2749constructorimpl(m2762getXimpl(j) - f, m2763getYimpl(j) - f);
    }

    /* renamed from: minus-b2IjXjg, reason: not valid java name */
    public static final long m2769minusb2IjXjg(long j, long j2) {
        return m2749constructorimpl(m2762getXimpl(j) - m2762getXimpl(j2), m2763getYimpl(j) - m2763getYimpl(j2));
    }

    /* renamed from: minus-tHZnGzg, reason: not valid java name */
    public static final long m2770minustHZnGzg(long j, long j2) {
        return m2749constructorimpl(m2762getXimpl(j) - VPoint.m2711getXimpl(j2), m2763getYimpl(j) - VPoint.m2712getYimpl(j2));
    }

    /* renamed from: normalize-Jv7bpU8, reason: not valid java name */
    public static final long m2771normalizeJv7bpU8(long j) {
        float fM2765lengthimpl = m2765lengthimpl(j);
        return m2749constructorimpl(m2762getXimpl(j) / fM2765lengthimpl, m2763getYimpl(j) / fM2765lengthimpl);
    }

    /* renamed from: plus--AsyRdg, reason: not valid java name */
    public static final long m2773plusAsyRdg(long j, int i) {
        float f = i;
        return m2749constructorimpl(m2762getXimpl(j) + f, m2763getYimpl(j) + f);
    }

    /* renamed from: plus-b2IjXjg, reason: not valid java name */
    public static final long m2774plusb2IjXjg(long j, long j2) {
        return m2749constructorimpl(m2762getXimpl(j2) + m2762getXimpl(j), m2763getYimpl(j2) + m2763getYimpl(j));
    }

    /* renamed from: plus-tHZnGzg, reason: not valid java name */
    public static final long m2775plustHZnGzg(long j, long j2) {
        return m2749constructorimpl(m2762getXimpl(j) + VPoint.m2711getXimpl(j2), m2763getYimpl(j) + VPoint.m2712getYimpl(j2));
    }

    /* renamed from: times--AsyRdg, reason: not valid java name */
    public static final long m2777timesAsyRdg(long j, int i) {
        float f = i;
        return m2749constructorimpl(m2762getXimpl(j) * f, m2763getYimpl(j) * f);
    }

    /* renamed from: times-b2IjXjg, reason: not valid java name */
    public static final long m2778timesb2IjXjg(long j, long j2) {
        return m2749constructorimpl(m2762getXimpl(j2) * m2762getXimpl(j), m2763getYimpl(j2) * m2763getYimpl(j));
    }

    /* renamed from: times-tHZnGzg, reason: not valid java name */
    public static final long m2779timestHZnGzg(long j, long j2) {
        return m2749constructorimpl(m2762getXimpl(j) * VPoint.m2711getXimpl(j2), m2763getYimpl(j) * VPoint.m2712getYimpl(j2));
    }

    /* renamed from: toPointF-impl, reason: not valid java name */
    public static final PointF m2781toPointFimpl(long j) {
        return new PointF(m2762getXimpl(j), m2763getYimpl(j));
    }

    /* renamed from: toString-impl, reason: not valid java name */
    public static String m2782toStringimpl(long j) {
        return "(" + m2762getXimpl(j) + ", " + m2763getYimpl(j) + ")";
    }

    public boolean equals(Object obj) {
        return m2760equalsimpl(this.data, obj);
    }

    /* renamed from: getData-s-VKNKU, reason: not valid java name */
    public final long m2783getDatasVKNKU() {
        return this.data;
    }

    public int hashCode() {
        return m2764hashCodeimpl(this.data);
    }

    public String toString() {
        return m2782toStringimpl(this.data);
    }

    /* renamed from: unbox-impl, reason: not valid java name */
    public final /* synthetic */ long m2784unboximpl() {
        return this.data;
    }

    /* renamed from: constructor-impl, reason: not valid java name */
    public static long m2754constructorimpl(PointF pointF) {
        return m2749constructorimpl(pointF.x, pointF.y);
    }

    /* renamed from: div--AsyRdg, reason: not valid java name */
    public static final long m2755divAsyRdg(long j, float f) {
        return m2749constructorimpl(m2762getXimpl(j) / f, m2763getYimpl(j) / f);
    }

    /* renamed from: minus--AsyRdg, reason: not valid java name */
    public static final long m2767minusAsyRdg(long j, float f) {
        return m2749constructorimpl(m2762getXimpl(j) - f, m2763getYimpl(j) - f);
    }

    /* renamed from: plus--AsyRdg, reason: not valid java name */
    public static final long m2772plusAsyRdg(long j, float f) {
        return m2749constructorimpl(m2762getXimpl(j) + f, m2763getYimpl(j) + f);
    }

    /* renamed from: times--AsyRdg, reason: not valid java name */
    public static final long m2776timesAsyRdg(long j, float f) {
        return m2749constructorimpl(m2762getXimpl(j) * f, m2763getYimpl(j) * f);
    }

    /* renamed from: constructor-impl, reason: not valid java name */
    public static long m2752constructorimpl(int i, int i2) {
        return m2749constructorimpl(i, i2);
    }

    /* renamed from: constructor-impl, reason: not valid java name */
    public static long m2751constructorimpl(int i, float f) {
        return m2749constructorimpl(i, f);
    }

    /* renamed from: constructor-impl, reason: not valid java name */
    public static long m2750constructorimpl(float f, int i) {
        return m2749constructorimpl(f, i);
    }

    /* renamed from: constructor-impl, reason: not valid java name */
    public static long m2749constructorimpl(float f, float f2) {
        return m2753constructorimpl(VPointKt.pack(Float.floatToIntBits(f), Float.floatToIntBits(f2)));
    }

    /* renamed from: toLong-impl, reason: not valid java name */
    public static final long m2780toLongimpl(long j) {
        return j;
    }
}
