package androidx.compose.ui.unit;

import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import androidx.compose.runtime.collection.MutableVectorKt$$ExternalSyntheticOutline0;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.IntSize;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class ConstraintsKt {
    public static final long Constraints(int i, int i2, int i3, int i4) {
        if (!((i3 >= 0) & (i2 >= i) & (i4 >= i3) & (i >= 0))) {
            InlineClassHelperKt.throwIllegalArgumentException("maxWidth must be >= than minWidth,\nmaxHeight must be >= than minHeight,\nminWidth and minHeight must be >= 0");
        }
        return createConstraints(i, i2, i3, i4);
    }

    public static /* synthetic */ long Constraints$default(int i, int i2, int i3, int i4, int i5) {
        if ((i5 & 1) != 0) {
            i = 0;
        }
        if ((i5 & 2) != 0) {
            i2 = Integer.MAX_VALUE;
        }
        if ((i5 & 4) != 0) {
            i3 = 0;
        }
        if ((i5 & 8) != 0) {
            i4 = Integer.MAX_VALUE;
        }
        return Constraints(i, i2, i3, i4);
    }

    public static final int bitsNeedForSizeUnchecked(int i) {
        int numberOfLeadingZeros = Integer.numberOfLeadingZeros(i + 1);
        if (numberOfLeadingZeros >= 19) {
            return 13;
        }
        if (numberOfLeadingZeros >= 17) {
            return 15;
        }
        if (numberOfLeadingZeros >= 16) {
            return 16;
        }
        return numberOfLeadingZeros >= 14 ? 18 : 255;
    }

    /* renamed from: constrain-4WqzIAM, reason: not valid java name */
    public static final long m829constrain4WqzIAM(long j, long j2) {
        int i = (int) (j2 >> 32);
        int m823getMinWidthimpl = Constraints.m823getMinWidthimpl(j);
        int m821getMaxWidthimpl = Constraints.m821getMaxWidthimpl(j);
        if (i < m823getMinWidthimpl) {
            i = m823getMinWidthimpl;
        }
        if (i <= m821getMaxWidthimpl) {
            m821getMaxWidthimpl = i;
        }
        int i2 = (int) (j2 & 4294967295L);
        int m822getMinHeightimpl = Constraints.m822getMinHeightimpl(j);
        int m820getMaxHeightimpl = Constraints.m820getMaxHeightimpl(j);
        if (i2 < m822getMinHeightimpl) {
            i2 = m822getMinHeightimpl;
        }
        if (i2 <= m820getMaxHeightimpl) {
            m820getMaxHeightimpl = i2;
        }
        long j3 = (m821getMaxWidthimpl << 32) | (m820getMaxHeightimpl & 4294967295L);
        IntSize.Companion companion = IntSize.Companion;
        return j3;
    }

    /* renamed from: constrain-N9IONVI, reason: not valid java name */
    public static final long m830constrainN9IONVI(long j, long j2) {
        int m823getMinWidthimpl = Constraints.m823getMinWidthimpl(j);
        int m821getMaxWidthimpl = Constraints.m821getMaxWidthimpl(j);
        int m822getMinHeightimpl = Constraints.m822getMinHeightimpl(j);
        int m820getMaxHeightimpl = Constraints.m820getMaxHeightimpl(j);
        int m823getMinWidthimpl2 = Constraints.m823getMinWidthimpl(j2);
        if (m823getMinWidthimpl2 < m823getMinWidthimpl) {
            m823getMinWidthimpl2 = m823getMinWidthimpl;
        }
        if (m823getMinWidthimpl2 > m821getMaxWidthimpl) {
            m823getMinWidthimpl2 = m821getMaxWidthimpl;
        }
        int m821getMaxWidthimpl2 = Constraints.m821getMaxWidthimpl(j2);
        if (m821getMaxWidthimpl2 >= m823getMinWidthimpl) {
            m823getMinWidthimpl = m821getMaxWidthimpl2;
        }
        if (m823getMinWidthimpl <= m821getMaxWidthimpl) {
            m821getMaxWidthimpl = m823getMinWidthimpl;
        }
        int m822getMinHeightimpl2 = Constraints.m822getMinHeightimpl(j2);
        if (m822getMinHeightimpl2 < m822getMinHeightimpl) {
            m822getMinHeightimpl2 = m822getMinHeightimpl;
        }
        if (m822getMinHeightimpl2 > m820getMaxHeightimpl) {
            m822getMinHeightimpl2 = m820getMaxHeightimpl;
        }
        int m820getMaxHeightimpl2 = Constraints.m820getMaxHeightimpl(j2);
        if (m820getMaxHeightimpl2 >= m822getMinHeightimpl) {
            m822getMinHeightimpl = m820getMaxHeightimpl2;
        }
        if (m822getMinHeightimpl <= m820getMaxHeightimpl) {
            m820getMaxHeightimpl = m822getMinHeightimpl;
        }
        return Constraints(m823getMinWidthimpl2, m821getMaxWidthimpl, m822getMinHeightimpl2, m820getMaxHeightimpl);
    }

    /* renamed from: constrainHeight-K40F9xA, reason: not valid java name */
    public static final int m831constrainHeightK40F9xA(int i, long j) {
        int m822getMinHeightimpl = Constraints.m822getMinHeightimpl(j);
        int m820getMaxHeightimpl = Constraints.m820getMaxHeightimpl(j);
        if (i < m822getMinHeightimpl) {
            i = m822getMinHeightimpl;
        }
        return i > m820getMaxHeightimpl ? m820getMaxHeightimpl : i;
    }

    /* renamed from: constrainWidth-K40F9xA, reason: not valid java name */
    public static final int m832constrainWidthK40F9xA(int i, long j) {
        int m823getMinWidthimpl = Constraints.m823getMinWidthimpl(j);
        int m821getMaxWidthimpl = Constraints.m821getMaxWidthimpl(j);
        if (i < m823getMinWidthimpl) {
            i = m823getMinWidthimpl;
        }
        return i > m821getMaxWidthimpl ? m821getMaxWidthimpl : i;
    }

    public static final long createConstraints(int i, int i2, int i3, int i4) {
        int i5 = i4 == Integer.MAX_VALUE ? i3 : i4;
        int bitsNeedForSizeUnchecked = bitsNeedForSizeUnchecked(i5);
        int i6 = i2 == Integer.MAX_VALUE ? i : i2;
        int bitsNeedForSizeUnchecked2 = bitsNeedForSizeUnchecked(i6);
        if (bitsNeedForSizeUnchecked + bitsNeedForSizeUnchecked2 > 31) {
            throwInvalidConstraintException(i6, i5);
        }
        int i7 = i2 + 1;
        int i8 = i4 + 1;
        int i9 = bitsNeedForSizeUnchecked2 - 13;
        long j = ((i7 & (~(i7 >> 31))) << 33) | ((i9 >> 1) + (i9 & 1)) | (i << 2) | (i3 << (bitsNeedForSizeUnchecked2 + 2)) | ((i8 & (~(i8 >> 31))) << (bitsNeedForSizeUnchecked2 + 33));
        Constraints.Companion companion = Constraints.Companion;
        return j;
    }

    /* renamed from: offset-NN6Ew-U, reason: not valid java name */
    public static final long m833offsetNN6EwU(int i, int i2, long j) {
        int m823getMinWidthimpl = Constraints.m823getMinWidthimpl(j) + i;
        if (m823getMinWidthimpl < 0) {
            m823getMinWidthimpl = 0;
        }
        int m821getMaxWidthimpl = Constraints.m821getMaxWidthimpl(j);
        if (m821getMaxWidthimpl != Integer.MAX_VALUE && (m821getMaxWidthimpl = m821getMaxWidthimpl + i) < 0) {
            m821getMaxWidthimpl = 0;
        }
        int m822getMinHeightimpl = Constraints.m822getMinHeightimpl(j) + i2;
        if (m822getMinHeightimpl < 0) {
            m822getMinHeightimpl = 0;
        }
        int m820getMaxHeightimpl = Constraints.m820getMaxHeightimpl(j);
        return Constraints(m823getMinWidthimpl, m821getMaxWidthimpl, m822getMinHeightimpl, (m820getMaxHeightimpl == Integer.MAX_VALUE || (m820getMaxHeightimpl = m820getMaxHeightimpl + i2) >= 0) ? m820getMaxHeightimpl : 0);
    }

    /* renamed from: offset-NN6Ew-U$default, reason: not valid java name */
    public static /* synthetic */ long m834offsetNN6EwU$default(int i, int i2, int i3, long j) {
        if ((i3 & 1) != 0) {
            i = 0;
        }
        if ((i3 & 2) != 0) {
            i2 = 0;
        }
        return m833offsetNN6EwU(i, i2, j);
    }

    public static final void throwInvalidConstraintException(int i, int i2) {
        throw new IllegalArgumentException(MutableVectorKt$$ExternalSyntheticOutline0.m(i, i2, "Can't represent a width of ", " and height of ", " in Constraints"));
    }

    public static final Void throwInvalidConstraintsSizeException(int i) {
        throw new IllegalArgumentException(ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(i, "Can't represent a size of ", " in Constraints"));
    }
}
