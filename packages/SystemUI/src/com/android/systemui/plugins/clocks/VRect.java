package com.android.systemui.plugins.clocks;

import android.graphics.Rect;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class VRect {
    public static final Companion Companion = new Companion(null);
    private static final long ZERO = m2785constructorimpl(0, 0, 0, 0);
    private final long data;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* renamed from: fromCenter-bR_NODY, reason: not valid java name */
        public final long m2805fromCenterbR_NODY(long j, long j2) {
            return VRect.m2788constructorimpl((short) (VPoint.m2693getXimpl(j) - (VPoint.m2693getXimpl(j2) / 2)), (short) (VPoint.m2694getYimpl(j) - (VPoint.m2694getYimpl(j2) / 2)), (short) ((VPoint.m2693getXimpl(j2) / 2) + VPoint.m2693getXimpl(j)), (short) ((VPoint.m2694getYimpl(j2) / 2) + VPoint.m2694getYimpl(j)));
        }

        /* renamed from: fromLong-qYjogQA, reason: not valid java name */
        public final long m2806fromLongqYjogQA(long j) {
            int i = ULong.$r8$clinit;
            return VRect.m2786constructorimpl(j);
        }

        /* renamed from: fromTopLeft-bR_NODY, reason: not valid java name */
        public final long m2807fromTopLeftbR_NODY(long j, long j2) {
            return VRect.m2788constructorimpl((short) VPoint.m2693getXimpl(j), (short) VPoint.m2694getYimpl(j), (short) (VPoint.m2693getXimpl(j2) + VPoint.m2693getXimpl(j)), (short) (VPoint.m2694getYimpl(j2) + VPoint.m2694getYimpl(j)));
        }

        /* renamed from: getZERO-luuqa1s, reason: not valid java name */
        public final long m2808getZEROluuqa1s() {
            return VRect.ZERO;
        }

        private Companion() {
        }
    }

    private /* synthetic */ VRect(long j) {
        this.data = j;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ VRect m2784boximpl(long j) {
        return new VRect(j);
    }

    /* renamed from: constructor-impl, reason: not valid java name */
    public static long m2786constructorimpl(long j) {
        return j;
    }

    /* renamed from: equals-impl, reason: not valid java name */
    public static boolean m2789equalsimpl(long j, Object obj) {
        return (obj instanceof VRect) && j == ((VRect) obj).m2804unboximpl();
    }

    /* renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m2790equalsimpl0(long j, long j2) {
        return ULong.m3427equalsimpl0(j, j2);
    }

    /* renamed from: getBottom-impl, reason: not valid java name */
    public static final int m2791getBottomimpl(long j) {
        short m2838unpackBottomVKZWuLQ;
        m2838unpackBottomVKZWuLQ = VRectKt.m2838unpackBottomVKZWuLQ(j);
        return m2838unpackBottomVKZWuLQ;
    }

    /* renamed from: getCenter-VJhY4ng, reason: not valid java name */
    public static final long m2792getCenterVJhY4ng(long j) {
        return VPoint.m2701plus6OOQ2wA(VPoint.m2685constructorimpl(m2794getLeftimpl(j), m2797getTopimpl(j)), VPoint.m2689divDO4cnVw(m2796getSizeVJhY4ng(j), 2));
    }

    /* renamed from: getHeight-impl, reason: not valid java name */
    public static final int m2793getHeightimpl(long j) {
        return m2791getBottomimpl(j) - m2797getTopimpl(j);
    }

    /* renamed from: getLeft-impl, reason: not valid java name */
    public static final int m2794getLeftimpl(long j) {
        short m2839unpackLeftVKZWuLQ;
        m2839unpackLeftVKZWuLQ = VRectKt.m2839unpackLeftVKZWuLQ(j);
        return m2839unpackLeftVKZWuLQ;
    }

    /* renamed from: getRight-impl, reason: not valid java name */
    public static final int m2795getRightimpl(long j) {
        short m2840unpackRightVKZWuLQ;
        m2840unpackRightVKZWuLQ = VRectKt.m2840unpackRightVKZWuLQ(j);
        return m2840unpackRightVKZWuLQ;
    }

    /* renamed from: getSize-VJhY4ng, reason: not valid java name */
    public static final long m2796getSizeVJhY4ng(long j) {
        return VPoint.m2685constructorimpl(m2798getWidthimpl(j), m2793getHeightimpl(j));
    }

    /* renamed from: getTop-impl, reason: not valid java name */
    public static final int m2797getTopimpl(long j) {
        short m2841unpackTopVKZWuLQ;
        m2841unpackTopVKZWuLQ = VRectKt.m2841unpackTopVKZWuLQ(j);
        return m2841unpackTopVKZWuLQ;
    }

    /* renamed from: getWidth-impl, reason: not valid java name */
    public static final int m2798getWidthimpl(long j) {
        return m2795getRightimpl(j) - m2794getLeftimpl(j);
    }

    /* renamed from: hashCode-impl, reason: not valid java name */
    public static int m2799hashCodeimpl(long j) {
        int i = ULong.$r8$clinit;
        return Long.hashCode(j);
    }

    /* renamed from: toRect-impl, reason: not valid java name */
    public static final Rect m2801toRectimpl(long j) {
        return new Rect(m2794getLeftimpl(j), m2797getTopimpl(j), m2795getRightimpl(j), m2791getBottomimpl(j));
    }

    /* renamed from: toString-impl, reason: not valid java name */
    public static String m2802toStringimpl(long j) {
        int m2794getLeftimpl = m2794getLeftimpl(j);
        int m2797getTopimpl = m2797getTopimpl(j);
        int m2795getRightimpl = m2795getRightimpl(j);
        int m2791getBottomimpl = m2791getBottomimpl(j);
        StringBuilder m = MutableObjectList$$ExternalSyntheticOutline0.m(m2794getLeftimpl, m2797getTopimpl, "(", ", ", ") -> (");
        m.append(m2795getRightimpl);
        m.append(", ");
        m.append(m2791getBottomimpl);
        m.append(")");
        return m.toString();
    }

    public boolean equals(Object obj) {
        return m2789equalsimpl(this.data, obj);
    }

    /* renamed from: getData-s-VKNKU, reason: not valid java name */
    public final long m2803getDatasVKNKU() {
        return this.data;
    }

    public int hashCode() {
        return m2799hashCodeimpl(this.data);
    }

    public String toString() {
        return m2802toStringimpl(this.data);
    }

    /* renamed from: unbox-impl, reason: not valid java name */
    public final /* synthetic */ long m2804unboximpl() {
        return this.data;
    }

    /* renamed from: constructor-impl, reason: not valid java name */
    public static long m2787constructorimpl(Rect rect) {
        return m2788constructorimpl((short) rect.left, (short) rect.top, (short) rect.right, (short) rect.bottom);
    }

    /* renamed from: constructor-impl, reason: not valid java name */
    public static long m2785constructorimpl(int i, int i2, int i3, int i4) {
        return m2788constructorimpl((short) i, (short) i2, (short) i3, (short) i4);
    }

    /* renamed from: constructor-impl, reason: not valid java name */
    public static long m2788constructorimpl(short s, short s2, short s3, short s4) {
        long pack;
        pack = VRectKt.pack(s, s2, s3, s4);
        return m2786constructorimpl(pack);
    }

    /* renamed from: toLong-impl, reason: not valid java name */
    public static final long m2800toLongimpl(long j) {
        return j;
    }
}
