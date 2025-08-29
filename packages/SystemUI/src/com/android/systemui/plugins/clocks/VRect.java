package com.android.systemui.plugins.clocks;

import android.graphics.Rect;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class VRect {
    public static final Companion Companion = new Companion(null);
    private static final long ZERO = m2801constructorimpl(0, 0, 0, 0);
    private final long data;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* renamed from: fromCenter-bR_NODY, reason: not valid java name */
        public final long m2821fromCenterbR_NODY(long j, long j2) {
            return VRect.m2804constructorimpl((short) (VPoint.m2709getXimpl(j) - (VPoint.m2709getXimpl(j2) / 2)), (short) (VPoint.m2710getYimpl(j) - (VPoint.m2710getYimpl(j2) / 2)), (short) ((VPoint.m2709getXimpl(j2) / 2) + VPoint.m2709getXimpl(j)), (short) ((VPoint.m2710getYimpl(j2) / 2) + VPoint.m2710getYimpl(j)));
        }

        /* renamed from: fromLong-qYjogQA, reason: not valid java name */
        public final long m2822fromLongqYjogQA(long j) {
            int i = ULong.$r8$clinit;
            return VRect.m2802constructorimpl(j);
        }

        /* renamed from: fromTopLeft-bR_NODY, reason: not valid java name */
        public final long m2823fromTopLeftbR_NODY(long j, long j2) {
            return VRect.m2804constructorimpl((short) VPoint.m2709getXimpl(j), (short) VPoint.m2710getYimpl(j), (short) (VPoint.m2709getXimpl(j2) + VPoint.m2709getXimpl(j)), (short) (VPoint.m2710getYimpl(j2) + VPoint.m2710getYimpl(j)));
        }

        /* renamed from: getZERO-luuqa1s, reason: not valid java name */
        public final long m2824getZEROluuqa1s() {
            return VRect.ZERO;
        }

        private Companion() {
        }
    }

    private /* synthetic */ VRect(long j) {
        this.data = j;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ VRect m2800boximpl(long j) {
        return new VRect(j);
    }

    /* renamed from: constructor-impl, reason: not valid java name */
    public static long m2802constructorimpl(long j) {
        return j;
    }

    /* renamed from: equals-impl, reason: not valid java name */
    public static boolean m2805equalsimpl(long j, Object obj) {
        return (obj instanceof VRect) && j == ((VRect) obj).m2820unboximpl();
    }

    /* renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m2806equalsimpl0(long j, long j2) {
        return ULong.m3446equalsimpl0(j, j2);
    }

    /* renamed from: getBottom-impl, reason: not valid java name */
    public static final int m2807getBottomimpl(long j) {
        return VRectKt.m2854unpackBottomVKZWuLQ(j);
    }

    /* renamed from: getCenter-VJhY4ng, reason: not valid java name */
    public static final long m2808getCenterVJhY4ng(long j) {
        return VPoint.m2717plus6OOQ2wA(VPoint.m2701constructorimpl(m2810getLeftimpl(j), m2813getTopimpl(j)), VPoint.m2705divDO4cnVw(m2812getSizeVJhY4ng(j), 2));
    }

    /* renamed from: getHeight-impl, reason: not valid java name */
    public static final int m2809getHeightimpl(long j) {
        return m2807getBottomimpl(j) - m2813getTopimpl(j);
    }

    /* renamed from: getLeft-impl, reason: not valid java name */
    public static final int m2810getLeftimpl(long j) {
        return VRectKt.m2855unpackLeftVKZWuLQ(j);
    }

    /* renamed from: getRight-impl, reason: not valid java name */
    public static final int m2811getRightimpl(long j) {
        return VRectKt.m2856unpackRightVKZWuLQ(j);
    }

    /* renamed from: getSize-VJhY4ng, reason: not valid java name */
    public static final long m2812getSizeVJhY4ng(long j) {
        return VPoint.m2701constructorimpl(m2814getWidthimpl(j), m2809getHeightimpl(j));
    }

    /* renamed from: getTop-impl, reason: not valid java name */
    public static final int m2813getTopimpl(long j) {
        return VRectKt.m2857unpackTopVKZWuLQ(j);
    }

    /* renamed from: getWidth-impl, reason: not valid java name */
    public static final int m2814getWidthimpl(long j) {
        return m2811getRightimpl(j) - m2810getLeftimpl(j);
    }

    /* renamed from: hashCode-impl, reason: not valid java name */
    public static int m2815hashCodeimpl(long j) {
        int i = ULong.$r8$clinit;
        return Long.hashCode(j);
    }

    /* renamed from: toRect-impl, reason: not valid java name */
    public static final Rect m2817toRectimpl(long j) {
        return new Rect(m2810getLeftimpl(j), m2813getTopimpl(j), m2811getRightimpl(j), m2807getBottomimpl(j));
    }

    /* renamed from: toString-impl, reason: not valid java name */
    public static String m2818toStringimpl(long j) {
        int iM2810getLeftimpl = m2810getLeftimpl(j);
        int iM2813getTopimpl = m2813getTopimpl(j);
        int iM2811getRightimpl = m2811getRightimpl(j);
        int iM2807getBottomimpl = m2807getBottomimpl(j);
        StringBuilder sbM = MutableObjectList$$ExternalSyntheticOutline0.m(iM2810getLeftimpl, iM2813getTopimpl, "(", ", ", ") -> (");
        sbM.append(iM2811getRightimpl);
        sbM.append(", ");
        sbM.append(iM2807getBottomimpl);
        sbM.append(")");
        return sbM.toString();
    }

    public boolean equals(Object obj) {
        return m2805equalsimpl(this.data, obj);
    }

    /* renamed from: getData-s-VKNKU, reason: not valid java name */
    public final long m2819getDatasVKNKU() {
        return this.data;
    }

    public int hashCode() {
        return m2815hashCodeimpl(this.data);
    }

    public String toString() {
        return m2818toStringimpl(this.data);
    }

    /* renamed from: unbox-impl, reason: not valid java name */
    public final /* synthetic */ long m2820unboximpl() {
        return this.data;
    }

    /* renamed from: constructor-impl, reason: not valid java name */
    public static long m2803constructorimpl(Rect rect) {
        return m2804constructorimpl((short) rect.left, (short) rect.top, (short) rect.right, (short) rect.bottom);
    }

    /* renamed from: constructor-impl, reason: not valid java name */
    public static long m2801constructorimpl(int i, int i2, int i3, int i4) {
        return m2804constructorimpl((short) i, (short) i2, (short) i3, (short) i4);
    }

    /* renamed from: constructor-impl, reason: not valid java name */
    public static long m2804constructorimpl(short s, short s2, short s3, short s4) {
        return m2802constructorimpl(VRectKt.pack(s, s2, s3, s4));
    }

    /* renamed from: toLong-impl, reason: not valid java name */
    public static final long m2816toLongimpl(long j) {
        return j;
    }
}
