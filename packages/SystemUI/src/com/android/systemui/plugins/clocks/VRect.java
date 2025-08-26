package com.android.systemui.plugins.clocks;

import android.graphics.Rect;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class VRect {
    public static final Companion Companion = new Companion(null);
    private static final long ZERO = m2803constructorimpl(0, 0, 0, 0);
    private final long data;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* renamed from: fromCenter-bR_NODY, reason: not valid java name */
        public final long m2823fromCenterbR_NODY(long j, long j2) {
            return VRect.m2806constructorimpl((short) (VPoint.m2711getXimpl(j) - (VPoint.m2711getXimpl(j2) / 2)), (short) (VPoint.m2712getYimpl(j) - (VPoint.m2712getYimpl(j2) / 2)), (short) ((VPoint.m2711getXimpl(j2) / 2) + VPoint.m2711getXimpl(j)), (short) ((VPoint.m2712getYimpl(j2) / 2) + VPoint.m2712getYimpl(j)));
        }

        /* renamed from: fromLong-qYjogQA, reason: not valid java name */
        public final long m2824fromLongqYjogQA(long j) {
            int i = ULong.$r8$clinit;
            return VRect.m2804constructorimpl(j);
        }

        /* renamed from: fromTopLeft-bR_NODY, reason: not valid java name */
        public final long m2825fromTopLeftbR_NODY(long j, long j2) {
            return VRect.m2806constructorimpl((short) VPoint.m2711getXimpl(j), (short) VPoint.m2712getYimpl(j), (short) (VPoint.m2711getXimpl(j2) + VPoint.m2711getXimpl(j)), (short) (VPoint.m2712getYimpl(j2) + VPoint.m2712getYimpl(j)));
        }

        /* renamed from: getZERO-luuqa1s, reason: not valid java name */
        public final long m2826getZEROluuqa1s() {
            return VRect.ZERO;
        }

        private Companion() {
        }
    }

    private /* synthetic */ VRect(long j) {
        this.data = j;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ VRect m2802boximpl(long j) {
        return new VRect(j);
    }

    /* renamed from: constructor-impl, reason: not valid java name */
    public static long m2804constructorimpl(long j) {
        return j;
    }

    /* renamed from: equals-impl, reason: not valid java name */
    public static boolean m2807equalsimpl(long j, Object obj) {
        return (obj instanceof VRect) && j == ((VRect) obj).m2822unboximpl();
    }

    /* renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m2808equalsimpl0(long j, long j2) {
        return ULong.m3447equalsimpl0(j, j2);
    }

    /* renamed from: getBottom-impl, reason: not valid java name */
    public static final int m2809getBottomimpl(long j) {
        return VRectKt.m2856unpackBottomVKZWuLQ(j);
    }

    /* renamed from: getCenter-VJhY4ng, reason: not valid java name */
    public static final long m2810getCenterVJhY4ng(long j) {
        return VPoint.m2719plus6OOQ2wA(VPoint.m2703constructorimpl(m2812getLeftimpl(j), m2815getTopimpl(j)), VPoint.m2707divDO4cnVw(m2814getSizeVJhY4ng(j), 2));
    }

    /* renamed from: getHeight-impl, reason: not valid java name */
    public static final int m2811getHeightimpl(long j) {
        return m2809getBottomimpl(j) - m2815getTopimpl(j);
    }

    /* renamed from: getLeft-impl, reason: not valid java name */
    public static final int m2812getLeftimpl(long j) {
        return VRectKt.m2857unpackLeftVKZWuLQ(j);
    }

    /* renamed from: getRight-impl, reason: not valid java name */
    public static final int m2813getRightimpl(long j) {
        return VRectKt.m2858unpackRightVKZWuLQ(j);
    }

    /* renamed from: getSize-VJhY4ng, reason: not valid java name */
    public static final long m2814getSizeVJhY4ng(long j) {
        return VPoint.m2703constructorimpl(m2816getWidthimpl(j), m2811getHeightimpl(j));
    }

    /* renamed from: getTop-impl, reason: not valid java name */
    public static final int m2815getTopimpl(long j) {
        return VRectKt.m2859unpackTopVKZWuLQ(j);
    }

    /* renamed from: getWidth-impl, reason: not valid java name */
    public static final int m2816getWidthimpl(long j) {
        return m2813getRightimpl(j) - m2812getLeftimpl(j);
    }

    /* renamed from: hashCode-impl, reason: not valid java name */
    public static int m2817hashCodeimpl(long j) {
        int i = ULong.$r8$clinit;
        return Long.hashCode(j);
    }

    /* renamed from: toRect-impl, reason: not valid java name */
    public static final Rect m2819toRectimpl(long j) {
        return new Rect(m2812getLeftimpl(j), m2815getTopimpl(j), m2813getRightimpl(j), m2809getBottomimpl(j));
    }

    /* renamed from: toString-impl, reason: not valid java name */
    public static String m2820toStringimpl(long j) {
        int iM2812getLeftimpl = m2812getLeftimpl(j);
        int iM2815getTopimpl = m2815getTopimpl(j);
        int iM2813getRightimpl = m2813getRightimpl(j);
        int iM2809getBottomimpl = m2809getBottomimpl(j);
        StringBuilder sbM = MutableObjectList$$ExternalSyntheticOutline0.m(iM2812getLeftimpl, iM2815getTopimpl, "(", ", ", ") -> (");
        sbM.append(iM2813getRightimpl);
        sbM.append(", ");
        sbM.append(iM2809getBottomimpl);
        sbM.append(")");
        return sbM.toString();
    }

    public boolean equals(Object obj) {
        return m2807equalsimpl(this.data, obj);
    }

    /* renamed from: getData-s-VKNKU, reason: not valid java name */
    public final long m2821getDatasVKNKU() {
        return this.data;
    }

    public int hashCode() {
        return m2817hashCodeimpl(this.data);
    }

    public String toString() {
        return m2820toStringimpl(this.data);
    }

    /* renamed from: unbox-impl, reason: not valid java name */
    public final /* synthetic */ long m2822unboximpl() {
        return this.data;
    }

    /* renamed from: constructor-impl, reason: not valid java name */
    public static long m2805constructorimpl(Rect rect) {
        return m2806constructorimpl((short) rect.left, (short) rect.top, (short) rect.right, (short) rect.bottom);
    }

    /* renamed from: constructor-impl, reason: not valid java name */
    public static long m2803constructorimpl(int i, int i2, int i3, int i4) {
        return m2806constructorimpl((short) i, (short) i2, (short) i3, (short) i4);
    }

    /* renamed from: constructor-impl, reason: not valid java name */
    public static long m2806constructorimpl(short s, short s2, short s3, short s4) {
        return m2804constructorimpl(VRectKt.pack(s, s2, s3, s4));
    }

    /* renamed from: toLong-impl, reason: not valid java name */
    public static final long m2818toLongimpl(long j) {
        return j;
    }
}
