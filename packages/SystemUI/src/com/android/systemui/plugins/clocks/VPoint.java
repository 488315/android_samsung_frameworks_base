package com.android.systemui.plugins.clocks;

import android.graphics.Point;
import android.graphics.Rect;
import androidx.compose.runtime.collection.MutableVectorKt$$ExternalSyntheticOutline0;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class VPoint {
    public static final Companion Companion = new Companion(null);
    private static final long ZERO = m2703constructorimpl(0, 0);
    private final long data;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* renamed from: div-1WsrENI, reason: not valid java name */
        public final long m2731div1WsrENI(float f, long j) {
            return VPointF.m2749constructorimpl(f / VPoint.m2711getXimpl(j), f / VPoint.m2712getYimpl(j));
        }

        /* renamed from: div-K4Yh7zs, reason: not valid java name */
        public final long m2732divK4Yh7zs(int i, long j) {
            return VPoint.m2703constructorimpl(i / VPoint.m2711getXimpl(j), i / VPoint.m2712getYimpl(j));
        }

        /* renamed from: fromLong-DO4cnVw, reason: not valid java name */
        public final long m2733fromLongDO4cnVw(long j) {
            int i = ULong.$r8$clinit;
            return VPoint.m2704constructorimpl(j);
        }

        /* renamed from: getCenter-DO4cnVw, reason: not valid java name */
        public final long m2734getCenterDO4cnVw(Rect rect) {
            return VPoint.m2703constructorimpl(rect.centerX(), rect.centerY());
        }

        /* renamed from: getSize-DO4cnVw, reason: not valid java name */
        public final long m2735getSizeDO4cnVw(Rect rect) {
            return VPoint.m2703constructorimpl(rect.width(), rect.height());
        }

        /* renamed from: getZERO-VJhY4ng, reason: not valid java name */
        public final long m2736getZEROVJhY4ng() {
            return VPoint.ZERO;
        }

        /* renamed from: max-qc1rFFo, reason: not valid java name */
        public final long m2737maxqc1rFFo(long j, long j2) {
            return VPoint.m2703constructorimpl(Math.max(VPoint.m2711getXimpl(j), VPoint.m2711getXimpl(j2)), Math.max(VPoint.m2712getYimpl(j), VPoint.m2712getYimpl(j2)));
        }

        /* renamed from: min-qc1rFFo, reason: not valid java name */
        public final long m2738minqc1rFFo(long j, long j2) {
            return VPoint.m2703constructorimpl(Math.min(VPoint.m2711getXimpl(j), VPoint.m2711getXimpl(j2)), Math.min(VPoint.m2712getYimpl(j), VPoint.m2712getYimpl(j2)));
        }

        /* renamed from: minus-1WsrENI, reason: not valid java name */
        public final long m2739minus1WsrENI(float f, long j) {
            return VPointF.m2749constructorimpl(f - VPoint.m2711getXimpl(j), f - VPoint.m2712getYimpl(j));
        }

        /* renamed from: minus-K4Yh7zs, reason: not valid java name */
        public final long m2740minusK4Yh7zs(int i, long j) {
            return VPoint.m2703constructorimpl(i - VPoint.m2711getXimpl(j), i - VPoint.m2712getYimpl(j));
        }

        /* renamed from: plus-1WsrENI, reason: not valid java name */
        public final long m2741plus1WsrENI(float f, long j) {
            return VPointF.m2749constructorimpl(VPoint.m2711getXimpl(j) + f, f + VPoint.m2712getYimpl(j));
        }

        /* renamed from: plus-K4Yh7zs, reason: not valid java name */
        public final long m2742plusK4Yh7zs(int i, long j) {
            return VPoint.m2703constructorimpl(VPoint.m2711getXimpl(j) + i, VPoint.m2712getYimpl(j) + i);
        }

        /* renamed from: times-1WsrENI, reason: not valid java name */
        public final long m2743times1WsrENI(float f, long j) {
            return VPointF.m2749constructorimpl(VPoint.m2711getXimpl(j) * f, f * VPoint.m2712getYimpl(j));
        }

        /* renamed from: times-K4Yh7zs, reason: not valid java name */
        public final long m2744timesK4Yh7zs(int i, long j) {
            return VPoint.m2703constructorimpl(VPoint.m2711getXimpl(j) * i, VPoint.m2712getYimpl(j) * i);
        }

        private Companion() {
        }
    }

    private /* synthetic */ VPoint(long j) {
        this.data = j;
    }

    /* renamed from: abs-VJhY4ng, reason: not valid java name */
    public static final long m2699absVJhY4ng(long j) {
        return m2703constructorimpl(Math.abs(m2711getXimpl(j)), Math.abs(m2712getYimpl(j)));
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ VPoint m2700boximpl(long j) {
        return new VPoint(j);
    }

    /* renamed from: component1-impl, reason: not valid java name */
    public static final int m2701component1impl(long j) {
        return m2711getXimpl(j);
    }

    /* renamed from: component2-impl, reason: not valid java name */
    public static final int m2702component2impl(long j) {
        return m2712getYimpl(j);
    }

    /* renamed from: constructor-impl, reason: not valid java name */
    public static long m2704constructorimpl(long j) {
        return j;
    }

    /* renamed from: div--AsyRdg, reason: not valid java name */
    public static final long m2705divAsyRdg(long j, float f) {
        return VPointF.m2749constructorimpl(m2711getXimpl(j) / f, m2712getYimpl(j) / f);
    }

    /* renamed from: div-6OOQ2wA, reason: not valid java name */
    public static final long m2706div6OOQ2wA(long j, long j2) {
        return m2703constructorimpl(m2711getXimpl(j) / m2711getXimpl(j2), m2712getYimpl(j) / m2712getYimpl(j2));
    }

    /* renamed from: div-DO4cnVw, reason: not valid java name */
    public static final long m2707divDO4cnVw(long j, int i) {
        return m2703constructorimpl(m2711getXimpl(j) / i, m2712getYimpl(j) / i);
    }

    /* renamed from: div-b2IjXjg, reason: not valid java name */
    public static final long m2708divb2IjXjg(long j, long j2) {
        return VPointF.m2749constructorimpl(m2711getXimpl(j) / VPointF.m2762getXimpl(j2), m2712getYimpl(j) / VPointF.m2763getYimpl(j2));
    }

    /* renamed from: equals-impl, reason: not valid java name */
    public static boolean m2709equalsimpl(long j, Object obj) {
        return (obj instanceof VPoint) && j == ((VPoint) obj).m2730unboximpl();
    }

    /* renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m2710equalsimpl0(long j, long j2) {
        return ULong.m3447equalsimpl0(j, j2);
    }

    /* renamed from: getX-impl, reason: not valid java name */
    public static final int m2711getXimpl(long j) {
        return VPointKt.m2800unpackXVKZWuLQ(j);
    }

    /* renamed from: getY-impl, reason: not valid java name */
    public static final int m2712getYimpl(long j) {
        return VPointKt.m2801unpackYVKZWuLQ(j);
    }

    /* renamed from: hashCode-impl, reason: not valid java name */
    public static int m2713hashCodeimpl(long j) {
        int i = ULong.$r8$clinit;
        return Long.hashCode(j);
    }

    /* renamed from: minus--AsyRdg, reason: not valid java name */
    public static final long m2714minusAsyRdg(long j, float f) {
        return VPointF.m2749constructorimpl(m2711getXimpl(j) - f, m2712getYimpl(j) - f);
    }

    /* renamed from: minus-6OOQ2wA, reason: not valid java name */
    public static final long m2715minus6OOQ2wA(long j, long j2) {
        return m2703constructorimpl(m2711getXimpl(j) - m2711getXimpl(j2), m2712getYimpl(j) - m2712getYimpl(j2));
    }

    /* renamed from: minus-DO4cnVw, reason: not valid java name */
    public static final long m2716minusDO4cnVw(long j, int i) {
        return m2703constructorimpl(m2711getXimpl(j) - i, m2712getYimpl(j) - i);
    }

    /* renamed from: minus-b2IjXjg, reason: not valid java name */
    public static final long m2717minusb2IjXjg(long j, long j2) {
        return VPointF.m2749constructorimpl(m2711getXimpl(j) - VPointF.m2762getXimpl(j2), m2712getYimpl(j) - VPointF.m2763getYimpl(j2));
    }

    /* renamed from: plus--AsyRdg, reason: not valid java name */
    public static final long m2718plusAsyRdg(long j, float f) {
        return VPointF.m2749constructorimpl(m2711getXimpl(j) + f, m2712getYimpl(j) + f);
    }

    /* renamed from: plus-6OOQ2wA, reason: not valid java name */
    public static final long m2719plus6OOQ2wA(long j, long j2) {
        return m2703constructorimpl(m2711getXimpl(j2) + m2711getXimpl(j), m2712getYimpl(j2) + m2712getYimpl(j));
    }

    /* renamed from: plus-DO4cnVw, reason: not valid java name */
    public static final long m2720plusDO4cnVw(long j, int i) {
        return m2703constructorimpl(m2711getXimpl(j) + i, m2712getYimpl(j) + i);
    }

    /* renamed from: plus-b2IjXjg, reason: not valid java name */
    public static final long m2721plusb2IjXjg(long j, long j2) {
        return VPointF.m2749constructorimpl(VPointF.m2762getXimpl(j2) + m2711getXimpl(j), VPointF.m2763getYimpl(j2) + m2712getYimpl(j));
    }

    /* renamed from: times--AsyRdg, reason: not valid java name */
    public static final long m2722timesAsyRdg(long j, float f) {
        return VPointF.m2749constructorimpl(m2711getXimpl(j) * f, m2712getYimpl(j) * f);
    }

    /* renamed from: times-6OOQ2wA, reason: not valid java name */
    public static final long m2723times6OOQ2wA(long j, long j2) {
        return m2703constructorimpl(m2711getXimpl(j2) * m2711getXimpl(j), m2712getYimpl(j2) * m2712getYimpl(j));
    }

    /* renamed from: times-DO4cnVw, reason: not valid java name */
    public static final long m2724timesDO4cnVw(long j, int i) {
        return m2703constructorimpl(m2711getXimpl(j) * i, m2712getYimpl(j) * i);
    }

    /* renamed from: times-b2IjXjg, reason: not valid java name */
    public static final long m2725timesb2IjXjg(long j, long j2) {
        return VPointF.m2749constructorimpl(VPointF.m2762getXimpl(j2) * m2711getXimpl(j), VPointF.m2763getYimpl(j2) * m2712getYimpl(j));
    }

    /* renamed from: toPoint-impl, reason: not valid java name */
    public static final Point m2727toPointimpl(long j) {
        return new Point(m2711getXimpl(j), m2712getYimpl(j));
    }

    /* renamed from: toString-impl, reason: not valid java name */
    public static String m2728toStringimpl(long j) {
        return MutableVectorKt$$ExternalSyntheticOutline0.m(m2711getXimpl(j), m2712getYimpl(j), "(", ", ", ")");
    }

    public boolean equals(Object obj) {
        return m2709equalsimpl(this.data, obj);
    }

    /* renamed from: getData-s-VKNKU, reason: not valid java name */
    public final long m2729getDatasVKNKU() {
        return this.data;
    }

    public int hashCode() {
        return m2713hashCodeimpl(this.data);
    }

    public String toString() {
        return m2728toStringimpl(this.data);
    }

    /* renamed from: unbox-impl, reason: not valid java name */
    public final /* synthetic */ long m2730unboximpl() {
        return this.data;
    }

    /* renamed from: constructor-impl, reason: not valid java name */
    public static long m2703constructorimpl(int i, int i2) {
        return m2704constructorimpl(VPointKt.pack(i, i2));
    }

    /* renamed from: toLong-impl, reason: not valid java name */
    public static final long m2726toLongimpl(long j) {
        return j;
    }
}
