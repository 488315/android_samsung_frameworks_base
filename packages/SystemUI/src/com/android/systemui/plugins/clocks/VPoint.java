package com.android.systemui.plugins.clocks;

import android.graphics.Point;
import android.graphics.Rect;
import androidx.compose.runtime.collection.MutableVectorKt$$ExternalSyntheticOutline0;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class VPoint {
    public static final Companion Companion = new Companion(null);
    private static final long ZERO = m2701constructorimpl(0, 0);
    private final long data;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* renamed from: div-1WsrENI, reason: not valid java name */
        public final long m2729div1WsrENI(float f, long j) {
            return VPointF.m2747constructorimpl(f / VPoint.m2709getXimpl(j), f / VPoint.m2710getYimpl(j));
        }

        /* renamed from: div-K4Yh7zs, reason: not valid java name */
        public final long m2730divK4Yh7zs(int i, long j) {
            return VPoint.m2701constructorimpl(i / VPoint.m2709getXimpl(j), i / VPoint.m2710getYimpl(j));
        }

        /* renamed from: fromLong-DO4cnVw, reason: not valid java name */
        public final long m2731fromLongDO4cnVw(long j) {
            int i = ULong.$r8$clinit;
            return VPoint.m2702constructorimpl(j);
        }

        /* renamed from: getCenter-DO4cnVw, reason: not valid java name */
        public final long m2732getCenterDO4cnVw(Rect rect) {
            return VPoint.m2701constructorimpl(rect.centerX(), rect.centerY());
        }

        /* renamed from: getSize-DO4cnVw, reason: not valid java name */
        public final long m2733getSizeDO4cnVw(Rect rect) {
            return VPoint.m2701constructorimpl(rect.width(), rect.height());
        }

        /* renamed from: getZERO-VJhY4ng, reason: not valid java name */
        public final long m2734getZEROVJhY4ng() {
            return VPoint.ZERO;
        }

        /* renamed from: max-qc1rFFo, reason: not valid java name */
        public final long m2735maxqc1rFFo(long j, long j2) {
            return VPoint.m2701constructorimpl(Math.max(VPoint.m2709getXimpl(j), VPoint.m2709getXimpl(j2)), Math.max(VPoint.m2710getYimpl(j), VPoint.m2710getYimpl(j2)));
        }

        /* renamed from: min-qc1rFFo, reason: not valid java name */
        public final long m2736minqc1rFFo(long j, long j2) {
            return VPoint.m2701constructorimpl(Math.min(VPoint.m2709getXimpl(j), VPoint.m2709getXimpl(j2)), Math.min(VPoint.m2710getYimpl(j), VPoint.m2710getYimpl(j2)));
        }

        /* renamed from: minus-1WsrENI, reason: not valid java name */
        public final long m2737minus1WsrENI(float f, long j) {
            return VPointF.m2747constructorimpl(f - VPoint.m2709getXimpl(j), f - VPoint.m2710getYimpl(j));
        }

        /* renamed from: minus-K4Yh7zs, reason: not valid java name */
        public final long m2738minusK4Yh7zs(int i, long j) {
            return VPoint.m2701constructorimpl(i - VPoint.m2709getXimpl(j), i - VPoint.m2710getYimpl(j));
        }

        /* renamed from: plus-1WsrENI, reason: not valid java name */
        public final long m2739plus1WsrENI(float f, long j) {
            return VPointF.m2747constructorimpl(VPoint.m2709getXimpl(j) + f, f + VPoint.m2710getYimpl(j));
        }

        /* renamed from: plus-K4Yh7zs, reason: not valid java name */
        public final long m2740plusK4Yh7zs(int i, long j) {
            return VPoint.m2701constructorimpl(VPoint.m2709getXimpl(j) + i, VPoint.m2710getYimpl(j) + i);
        }

        /* renamed from: times-1WsrENI, reason: not valid java name */
        public final long m2741times1WsrENI(float f, long j) {
            return VPointF.m2747constructorimpl(VPoint.m2709getXimpl(j) * f, f * VPoint.m2710getYimpl(j));
        }

        /* renamed from: times-K4Yh7zs, reason: not valid java name */
        public final long m2742timesK4Yh7zs(int i, long j) {
            return VPoint.m2701constructorimpl(VPoint.m2709getXimpl(j) * i, VPoint.m2710getYimpl(j) * i);
        }

        private Companion() {
        }
    }

    private /* synthetic */ VPoint(long j) {
        this.data = j;
    }

    /* renamed from: abs-VJhY4ng, reason: not valid java name */
    public static final long m2697absVJhY4ng(long j) {
        return m2701constructorimpl(Math.abs(m2709getXimpl(j)), Math.abs(m2710getYimpl(j)));
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ VPoint m2698boximpl(long j) {
        return new VPoint(j);
    }

    /* renamed from: component1-impl, reason: not valid java name */
    public static final int m2699component1impl(long j) {
        return m2709getXimpl(j);
    }

    /* renamed from: component2-impl, reason: not valid java name */
    public static final int m2700component2impl(long j) {
        return m2710getYimpl(j);
    }

    /* renamed from: constructor-impl, reason: not valid java name */
    public static long m2702constructorimpl(long j) {
        return j;
    }

    /* renamed from: div--AsyRdg, reason: not valid java name */
    public static final long m2703divAsyRdg(long j, float f) {
        return VPointF.m2747constructorimpl(m2709getXimpl(j) / f, m2710getYimpl(j) / f);
    }

    /* renamed from: div-6OOQ2wA, reason: not valid java name */
    public static final long m2704div6OOQ2wA(long j, long j2) {
        return m2701constructorimpl(m2709getXimpl(j) / m2709getXimpl(j2), m2710getYimpl(j) / m2710getYimpl(j2));
    }

    /* renamed from: div-DO4cnVw, reason: not valid java name */
    public static final long m2705divDO4cnVw(long j, int i) {
        return m2701constructorimpl(m2709getXimpl(j) / i, m2710getYimpl(j) / i);
    }

    /* renamed from: div-b2IjXjg, reason: not valid java name */
    public static final long m2706divb2IjXjg(long j, long j2) {
        return VPointF.m2747constructorimpl(m2709getXimpl(j) / VPointF.m2760getXimpl(j2), m2710getYimpl(j) / VPointF.m2761getYimpl(j2));
    }

    /* renamed from: equals-impl, reason: not valid java name */
    public static boolean m2707equalsimpl(long j, Object obj) {
        return (obj instanceof VPoint) && j == ((VPoint) obj).m2728unboximpl();
    }

    /* renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m2708equalsimpl0(long j, long j2) {
        return ULong.m3446equalsimpl0(j, j2);
    }

    /* renamed from: getX-impl, reason: not valid java name */
    public static final int m2709getXimpl(long j) {
        return VPointKt.m2798unpackXVKZWuLQ(j);
    }

    /* renamed from: getY-impl, reason: not valid java name */
    public static final int m2710getYimpl(long j) {
        return VPointKt.m2799unpackYVKZWuLQ(j);
    }

    /* renamed from: hashCode-impl, reason: not valid java name */
    public static int m2711hashCodeimpl(long j) {
        int i = ULong.$r8$clinit;
        return Long.hashCode(j);
    }

    /* renamed from: minus--AsyRdg, reason: not valid java name */
    public static final long m2712minusAsyRdg(long j, float f) {
        return VPointF.m2747constructorimpl(m2709getXimpl(j) - f, m2710getYimpl(j) - f);
    }

    /* renamed from: minus-6OOQ2wA, reason: not valid java name */
    public static final long m2713minus6OOQ2wA(long j, long j2) {
        return m2701constructorimpl(m2709getXimpl(j) - m2709getXimpl(j2), m2710getYimpl(j) - m2710getYimpl(j2));
    }

    /* renamed from: minus-DO4cnVw, reason: not valid java name */
    public static final long m2714minusDO4cnVw(long j, int i) {
        return m2701constructorimpl(m2709getXimpl(j) - i, m2710getYimpl(j) - i);
    }

    /* renamed from: minus-b2IjXjg, reason: not valid java name */
    public static final long m2715minusb2IjXjg(long j, long j2) {
        return VPointF.m2747constructorimpl(m2709getXimpl(j) - VPointF.m2760getXimpl(j2), m2710getYimpl(j) - VPointF.m2761getYimpl(j2));
    }

    /* renamed from: plus--AsyRdg, reason: not valid java name */
    public static final long m2716plusAsyRdg(long j, float f) {
        return VPointF.m2747constructorimpl(m2709getXimpl(j) + f, m2710getYimpl(j) + f);
    }

    /* renamed from: plus-6OOQ2wA, reason: not valid java name */
    public static final long m2717plus6OOQ2wA(long j, long j2) {
        return m2701constructorimpl(m2709getXimpl(j2) + m2709getXimpl(j), m2710getYimpl(j2) + m2710getYimpl(j));
    }

    /* renamed from: plus-DO4cnVw, reason: not valid java name */
    public static final long m2718plusDO4cnVw(long j, int i) {
        return m2701constructorimpl(m2709getXimpl(j) + i, m2710getYimpl(j) + i);
    }

    /* renamed from: plus-b2IjXjg, reason: not valid java name */
    public static final long m2719plusb2IjXjg(long j, long j2) {
        return VPointF.m2747constructorimpl(VPointF.m2760getXimpl(j2) + m2709getXimpl(j), VPointF.m2761getYimpl(j2) + m2710getYimpl(j));
    }

    /* renamed from: times--AsyRdg, reason: not valid java name */
    public static final long m2720timesAsyRdg(long j, float f) {
        return VPointF.m2747constructorimpl(m2709getXimpl(j) * f, m2710getYimpl(j) * f);
    }

    /* renamed from: times-6OOQ2wA, reason: not valid java name */
    public static final long m2721times6OOQ2wA(long j, long j2) {
        return m2701constructorimpl(m2709getXimpl(j2) * m2709getXimpl(j), m2710getYimpl(j2) * m2710getYimpl(j));
    }

    /* renamed from: times-DO4cnVw, reason: not valid java name */
    public static final long m2722timesDO4cnVw(long j, int i) {
        return m2701constructorimpl(m2709getXimpl(j) * i, m2710getYimpl(j) * i);
    }

    /* renamed from: times-b2IjXjg, reason: not valid java name */
    public static final long m2723timesb2IjXjg(long j, long j2) {
        return VPointF.m2747constructorimpl(VPointF.m2760getXimpl(j2) * m2709getXimpl(j), VPointF.m2761getYimpl(j2) * m2710getYimpl(j));
    }

    /* renamed from: toPoint-impl, reason: not valid java name */
    public static final Point m2725toPointimpl(long j) {
        return new Point(m2709getXimpl(j), m2710getYimpl(j));
    }

    /* renamed from: toString-impl, reason: not valid java name */
    public static String m2726toStringimpl(long j) {
        return MutableVectorKt$$ExternalSyntheticOutline0.m(m2709getXimpl(j), m2710getYimpl(j), "(", ", ", ")");
    }

    public boolean equals(Object obj) {
        return m2707equalsimpl(this.data, obj);
    }

    /* renamed from: getData-s-VKNKU, reason: not valid java name */
    public final long m2727getDatasVKNKU() {
        return this.data;
    }

    public int hashCode() {
        return m2711hashCodeimpl(this.data);
    }

    public String toString() {
        return m2726toStringimpl(this.data);
    }

    /* renamed from: unbox-impl, reason: not valid java name */
    public final /* synthetic */ long m2728unboximpl() {
        return this.data;
    }

    /* renamed from: constructor-impl, reason: not valid java name */
    public static long m2701constructorimpl(int i, int i2) {
        return m2702constructorimpl(VPointKt.pack(i, i2));
    }

    /* renamed from: toLong-impl, reason: not valid java name */
    public static final long m2724toLongimpl(long j) {
        return j;
    }
}
