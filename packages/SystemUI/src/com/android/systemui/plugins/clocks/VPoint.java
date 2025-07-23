package com.android.systemui.plugins.clocks;

import android.graphics.Point;
import android.graphics.Rect;
import androidx.compose.runtime.collection.MutableVectorKt$$ExternalSyntheticOutline0;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class VPoint {
    public static final Companion Companion = new Companion(null);
    private static final long ZERO = m2685constructorimpl(0, 0);
    private final long data;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* renamed from: div-1WsrENI, reason: not valid java name */
        public final long m2713div1WsrENI(float f, long j) {
            return VPointF.m2731constructorimpl(f / VPoint.m2693getXimpl(j), f / VPoint.m2694getYimpl(j));
        }

        /* renamed from: div-K4Yh7zs, reason: not valid java name */
        public final long m2714divK4Yh7zs(int i, long j) {
            return VPoint.m2685constructorimpl(i / VPoint.m2693getXimpl(j), i / VPoint.m2694getYimpl(j));
        }

        /* renamed from: fromLong-DO4cnVw, reason: not valid java name */
        public final long m2715fromLongDO4cnVw(long j) {
            int i = ULong.$r8$clinit;
            return VPoint.m2686constructorimpl(j);
        }

        /* renamed from: getCenter-DO4cnVw, reason: not valid java name */
        public final long m2716getCenterDO4cnVw(Rect rect) {
            return VPoint.m2685constructorimpl(rect.centerX(), rect.centerY());
        }

        /* renamed from: getSize-DO4cnVw, reason: not valid java name */
        public final long m2717getSizeDO4cnVw(Rect rect) {
            return VPoint.m2685constructorimpl(rect.width(), rect.height());
        }

        /* renamed from: getZERO-VJhY4ng, reason: not valid java name */
        public final long m2718getZEROVJhY4ng() {
            return VPoint.ZERO;
        }

        /* renamed from: max-qc1rFFo, reason: not valid java name */
        public final long m2719maxqc1rFFo(long j, long j2) {
            return VPoint.m2685constructorimpl(Math.max(VPoint.m2693getXimpl(j), VPoint.m2693getXimpl(j2)), Math.max(VPoint.m2694getYimpl(j), VPoint.m2694getYimpl(j2)));
        }

        /* renamed from: min-qc1rFFo, reason: not valid java name */
        public final long m2720minqc1rFFo(long j, long j2) {
            return VPoint.m2685constructorimpl(Math.min(VPoint.m2693getXimpl(j), VPoint.m2693getXimpl(j2)), Math.min(VPoint.m2694getYimpl(j), VPoint.m2694getYimpl(j2)));
        }

        /* renamed from: minus-1WsrENI, reason: not valid java name */
        public final long m2721minus1WsrENI(float f, long j) {
            return VPointF.m2731constructorimpl(f - VPoint.m2693getXimpl(j), f - VPoint.m2694getYimpl(j));
        }

        /* renamed from: minus-K4Yh7zs, reason: not valid java name */
        public final long m2722minusK4Yh7zs(int i, long j) {
            return VPoint.m2685constructorimpl(i - VPoint.m2693getXimpl(j), i - VPoint.m2694getYimpl(j));
        }

        /* renamed from: plus-1WsrENI, reason: not valid java name */
        public final long m2723plus1WsrENI(float f, long j) {
            return VPointF.m2731constructorimpl(VPoint.m2693getXimpl(j) + f, f + VPoint.m2694getYimpl(j));
        }

        /* renamed from: plus-K4Yh7zs, reason: not valid java name */
        public final long m2724plusK4Yh7zs(int i, long j) {
            return VPoint.m2685constructorimpl(VPoint.m2693getXimpl(j) + i, VPoint.m2694getYimpl(j) + i);
        }

        /* renamed from: times-1WsrENI, reason: not valid java name */
        public final long m2725times1WsrENI(float f, long j) {
            return VPointF.m2731constructorimpl(VPoint.m2693getXimpl(j) * f, f * VPoint.m2694getYimpl(j));
        }

        /* renamed from: times-K4Yh7zs, reason: not valid java name */
        public final long m2726timesK4Yh7zs(int i, long j) {
            return VPoint.m2685constructorimpl(VPoint.m2693getXimpl(j) * i, VPoint.m2694getYimpl(j) * i);
        }

        private Companion() {
        }
    }

    private /* synthetic */ VPoint(long j) {
        this.data = j;
    }

    /* renamed from: abs-VJhY4ng, reason: not valid java name */
    public static final long m2681absVJhY4ng(long j) {
        return m2685constructorimpl(Math.abs(m2693getXimpl(j)), Math.abs(m2694getYimpl(j)));
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ VPoint m2682boximpl(long j) {
        return new VPoint(j);
    }

    /* renamed from: component1-impl, reason: not valid java name */
    public static final int m2683component1impl(long j) {
        return m2693getXimpl(j);
    }

    /* renamed from: component2-impl, reason: not valid java name */
    public static final int m2684component2impl(long j) {
        return m2694getYimpl(j);
    }

    /* renamed from: constructor-impl, reason: not valid java name */
    public static long m2686constructorimpl(long j) {
        return j;
    }

    /* renamed from: div--AsyRdg, reason: not valid java name */
    public static final long m2687divAsyRdg(long j, float f) {
        return VPointF.m2731constructorimpl(m2693getXimpl(j) / f, m2694getYimpl(j) / f);
    }

    /* renamed from: div-6OOQ2wA, reason: not valid java name */
    public static final long m2688div6OOQ2wA(long j, long j2) {
        return m2685constructorimpl(m2693getXimpl(j) / m2693getXimpl(j2), m2694getYimpl(j) / m2694getYimpl(j2));
    }

    /* renamed from: div-DO4cnVw, reason: not valid java name */
    public static final long m2689divDO4cnVw(long j, int i) {
        return m2685constructorimpl(m2693getXimpl(j) / i, m2694getYimpl(j) / i);
    }

    /* renamed from: div-b2IjXjg, reason: not valid java name */
    public static final long m2690divb2IjXjg(long j, long j2) {
        return VPointF.m2731constructorimpl(m2693getXimpl(j) / VPointF.m2744getXimpl(j2), m2694getYimpl(j) / VPointF.m2745getYimpl(j2));
    }

    /* renamed from: equals-impl, reason: not valid java name */
    public static boolean m2691equalsimpl(long j, Object obj) {
        return (obj instanceof VPoint) && j == ((VPoint) obj).m2712unboximpl();
    }

    /* renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m2692equalsimpl0(long j, long j2) {
        return ULong.m3427equalsimpl0(j, j2);
    }

    /* renamed from: getX-impl, reason: not valid java name */
    public static final int m2693getXimpl(long j) {
        int m2782unpackXVKZWuLQ;
        m2782unpackXVKZWuLQ = VPointKt.m2782unpackXVKZWuLQ(j);
        return m2782unpackXVKZWuLQ;
    }

    /* renamed from: getY-impl, reason: not valid java name */
    public static final int m2694getYimpl(long j) {
        int m2783unpackYVKZWuLQ;
        m2783unpackYVKZWuLQ = VPointKt.m2783unpackYVKZWuLQ(j);
        return m2783unpackYVKZWuLQ;
    }

    /* renamed from: hashCode-impl, reason: not valid java name */
    public static int m2695hashCodeimpl(long j) {
        int i = ULong.$r8$clinit;
        return Long.hashCode(j);
    }

    /* renamed from: minus--AsyRdg, reason: not valid java name */
    public static final long m2696minusAsyRdg(long j, float f) {
        return VPointF.m2731constructorimpl(m2693getXimpl(j) - f, m2694getYimpl(j) - f);
    }

    /* renamed from: minus-6OOQ2wA, reason: not valid java name */
    public static final long m2697minus6OOQ2wA(long j, long j2) {
        return m2685constructorimpl(m2693getXimpl(j) - m2693getXimpl(j2), m2694getYimpl(j) - m2694getYimpl(j2));
    }

    /* renamed from: minus-DO4cnVw, reason: not valid java name */
    public static final long m2698minusDO4cnVw(long j, int i) {
        return m2685constructorimpl(m2693getXimpl(j) - i, m2694getYimpl(j) - i);
    }

    /* renamed from: minus-b2IjXjg, reason: not valid java name */
    public static final long m2699minusb2IjXjg(long j, long j2) {
        return VPointF.m2731constructorimpl(m2693getXimpl(j) - VPointF.m2744getXimpl(j2), m2694getYimpl(j) - VPointF.m2745getYimpl(j2));
    }

    /* renamed from: plus--AsyRdg, reason: not valid java name */
    public static final long m2700plusAsyRdg(long j, float f) {
        return VPointF.m2731constructorimpl(m2693getXimpl(j) + f, m2694getYimpl(j) + f);
    }

    /* renamed from: plus-6OOQ2wA, reason: not valid java name */
    public static final long m2701plus6OOQ2wA(long j, long j2) {
        return m2685constructorimpl(m2693getXimpl(j2) + m2693getXimpl(j), m2694getYimpl(j2) + m2694getYimpl(j));
    }

    /* renamed from: plus-DO4cnVw, reason: not valid java name */
    public static final long m2702plusDO4cnVw(long j, int i) {
        return m2685constructorimpl(m2693getXimpl(j) + i, m2694getYimpl(j) + i);
    }

    /* renamed from: plus-b2IjXjg, reason: not valid java name */
    public static final long m2703plusb2IjXjg(long j, long j2) {
        return VPointF.m2731constructorimpl(VPointF.m2744getXimpl(j2) + m2693getXimpl(j), VPointF.m2745getYimpl(j2) + m2694getYimpl(j));
    }

    /* renamed from: times--AsyRdg, reason: not valid java name */
    public static final long m2704timesAsyRdg(long j, float f) {
        return VPointF.m2731constructorimpl(m2693getXimpl(j) * f, m2694getYimpl(j) * f);
    }

    /* renamed from: times-6OOQ2wA, reason: not valid java name */
    public static final long m2705times6OOQ2wA(long j, long j2) {
        return m2685constructorimpl(m2693getXimpl(j2) * m2693getXimpl(j), m2694getYimpl(j2) * m2694getYimpl(j));
    }

    /* renamed from: times-DO4cnVw, reason: not valid java name */
    public static final long m2706timesDO4cnVw(long j, int i) {
        return m2685constructorimpl(m2693getXimpl(j) * i, m2694getYimpl(j) * i);
    }

    /* renamed from: times-b2IjXjg, reason: not valid java name */
    public static final long m2707timesb2IjXjg(long j, long j2) {
        return VPointF.m2731constructorimpl(VPointF.m2744getXimpl(j2) * m2693getXimpl(j), VPointF.m2745getYimpl(j2) * m2694getYimpl(j));
    }

    /* renamed from: toPoint-impl, reason: not valid java name */
    public static final Point m2709toPointimpl(long j) {
        return new Point(m2693getXimpl(j), m2694getYimpl(j));
    }

    /* renamed from: toString-impl, reason: not valid java name */
    public static String m2710toStringimpl(long j) {
        return MutableVectorKt$$ExternalSyntheticOutline0.m(m2693getXimpl(j), m2694getYimpl(j), "(", ", ", ")");
    }

    public boolean equals(Object obj) {
        return m2691equalsimpl(this.data, obj);
    }

    /* renamed from: getData-s-VKNKU, reason: not valid java name */
    public final long m2711getDatasVKNKU() {
        return this.data;
    }

    public int hashCode() {
        return m2695hashCodeimpl(this.data);
    }

    public String toString() {
        return m2710toStringimpl(this.data);
    }

    /* renamed from: unbox-impl, reason: not valid java name */
    public final /* synthetic */ long m2712unboximpl() {
        return this.data;
    }

    /* renamed from: constructor-impl, reason: not valid java name */
    public static long m2685constructorimpl(int i, int i2) {
        long pack;
        pack = VPointKt.pack(i, i2);
        return m2686constructorimpl(pack);
    }

    /* renamed from: toLong-impl, reason: not valid java name */
    public static final long m2708toLongimpl(long j) {
        return j;
    }
}
