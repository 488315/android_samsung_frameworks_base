package androidx.compose.ui.unit;

import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import androidx.compose.runtime.collection.MutableVectorKt$$ExternalSyntheticOutline0;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.IntSize;

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
        int iNumberOfLeadingZeros = Integer.numberOfLeadingZeros(i + 1);
        if (iNumberOfLeadingZeros >= 19) {
            return 13;
        }
        if (iNumberOfLeadingZeros >= 17) {
            return 15;
        }
        if (iNumberOfLeadingZeros >= 16) {
            return 16;
        }
        return iNumberOfLeadingZeros >= 14 ? 18 : 255;
    }

    /* renamed from: constrain-4WqzIAM, reason: not valid java name */
    public static final long m831constrain4WqzIAM(long j, long j2) {
        int i = (int) (j2 >> 32);
        int iM825getMinWidthimpl = Constraints.m825getMinWidthimpl(j);
        int iM823getMaxWidthimpl = Constraints.m823getMaxWidthimpl(j);
        if (i < iM825getMinWidthimpl) {
            i = iM825getMinWidthimpl;
        }
        if (i <= iM823getMaxWidthimpl) {
            iM823getMaxWidthimpl = i;
        }
        int i2 = (int) (j2 & 4294967295L);
        int iM824getMinHeightimpl = Constraints.m824getMinHeightimpl(j);
        int iM822getMaxHeightimpl = Constraints.m822getMaxHeightimpl(j);
        if (i2 < iM824getMinHeightimpl) {
            i2 = iM824getMinHeightimpl;
        }
        if (i2 <= iM822getMaxHeightimpl) {
            iM822getMaxHeightimpl = i2;
        }
        long j3 = (iM823getMaxWidthimpl << 32) | (iM822getMaxHeightimpl & 4294967295L);
        IntSize.Companion companion = IntSize.Companion;
        return j3;
    }

    /* renamed from: constrain-N9IONVI, reason: not valid java name */
    public static final long m832constrainN9IONVI(long j, long j2) {
        int iM825getMinWidthimpl = Constraints.m825getMinWidthimpl(j);
        int iM823getMaxWidthimpl = Constraints.m823getMaxWidthimpl(j);
        int iM824getMinHeightimpl = Constraints.m824getMinHeightimpl(j);
        int iM822getMaxHeightimpl = Constraints.m822getMaxHeightimpl(j);
        int iM825getMinWidthimpl2 = Constraints.m825getMinWidthimpl(j2);
        if (iM825getMinWidthimpl2 < iM825getMinWidthimpl) {
            iM825getMinWidthimpl2 = iM825getMinWidthimpl;
        }
        if (iM825getMinWidthimpl2 > iM823getMaxWidthimpl) {
            iM825getMinWidthimpl2 = iM823getMaxWidthimpl;
        }
        int iM823getMaxWidthimpl2 = Constraints.m823getMaxWidthimpl(j2);
        if (iM823getMaxWidthimpl2 >= iM825getMinWidthimpl) {
            iM825getMinWidthimpl = iM823getMaxWidthimpl2;
        }
        if (iM825getMinWidthimpl <= iM823getMaxWidthimpl) {
            iM823getMaxWidthimpl = iM825getMinWidthimpl;
        }
        int iM824getMinHeightimpl2 = Constraints.m824getMinHeightimpl(j2);
        if (iM824getMinHeightimpl2 < iM824getMinHeightimpl) {
            iM824getMinHeightimpl2 = iM824getMinHeightimpl;
        }
        if (iM824getMinHeightimpl2 > iM822getMaxHeightimpl) {
            iM824getMinHeightimpl2 = iM822getMaxHeightimpl;
        }
        int iM822getMaxHeightimpl2 = Constraints.m822getMaxHeightimpl(j2);
        if (iM822getMaxHeightimpl2 >= iM824getMinHeightimpl) {
            iM824getMinHeightimpl = iM822getMaxHeightimpl2;
        }
        if (iM824getMinHeightimpl <= iM822getMaxHeightimpl) {
            iM822getMaxHeightimpl = iM824getMinHeightimpl;
        }
        return Constraints(iM825getMinWidthimpl2, iM823getMaxWidthimpl, iM824getMinHeightimpl2, iM822getMaxHeightimpl);
    }

    /* renamed from: constrainHeight-K40F9xA, reason: not valid java name */
    public static final int m833constrainHeightK40F9xA(int i, long j) {
        int iM824getMinHeightimpl = Constraints.m824getMinHeightimpl(j);
        int iM822getMaxHeightimpl = Constraints.m822getMaxHeightimpl(j);
        if (i < iM824getMinHeightimpl) {
            i = iM824getMinHeightimpl;
        }
        return i > iM822getMaxHeightimpl ? iM822getMaxHeightimpl : i;
    }

    /* renamed from: constrainWidth-K40F9xA, reason: not valid java name */
    public static final int m834constrainWidthK40F9xA(int i, long j) {
        int iM825getMinWidthimpl = Constraints.m825getMinWidthimpl(j);
        int iM823getMaxWidthimpl = Constraints.m823getMaxWidthimpl(j);
        if (i < iM825getMinWidthimpl) {
            i = iM825getMinWidthimpl;
        }
        return i > iM823getMaxWidthimpl ? iM823getMaxWidthimpl : i;
    }

    public static final long createConstraints(int i, int i2, int i3, int i4) {
        int i5 = i4 == Integer.MAX_VALUE ? i3 : i4;
        int iBitsNeedForSizeUnchecked = bitsNeedForSizeUnchecked(i5);
        int i6 = i2 == Integer.MAX_VALUE ? i : i2;
        int iBitsNeedForSizeUnchecked2 = bitsNeedForSizeUnchecked(i6);
        if (iBitsNeedForSizeUnchecked + iBitsNeedForSizeUnchecked2 > 31) {
            throwInvalidConstraintException(i6, i5);
        }
        int i7 = i2 + 1;
        int i8 = i4 + 1;
        int i9 = iBitsNeedForSizeUnchecked2 - 13;
        long j = ((i7 & (~(i7 >> 31))) << 33) | ((i9 >> 1) + (i9 & 1)) | (i << 2) | (i3 << (iBitsNeedForSizeUnchecked2 + 2)) | ((i8 & (~(i8 >> 31))) << (iBitsNeedForSizeUnchecked2 + 33));
        Constraints.Companion companion = Constraints.Companion;
        return j;
    }

    /* renamed from: offset-NN6Ew-U, reason: not valid java name */
    public static final long m835offsetNN6EwU(int i, int i2, long j) {
        int iM825getMinWidthimpl = Constraints.m825getMinWidthimpl(j) + i;
        if (iM825getMinWidthimpl < 0) {
            iM825getMinWidthimpl = 0;
        }
        int iM823getMaxWidthimpl = Constraints.m823getMaxWidthimpl(j);
        if (iM823getMaxWidthimpl != Integer.MAX_VALUE && (iM823getMaxWidthimpl = iM823getMaxWidthimpl + i) < 0) {
            iM823getMaxWidthimpl = 0;
        }
        int iM824getMinHeightimpl = Constraints.m824getMinHeightimpl(j) + i2;
        if (iM824getMinHeightimpl < 0) {
            iM824getMinHeightimpl = 0;
        }
        int iM822getMaxHeightimpl = Constraints.m822getMaxHeightimpl(j);
        return Constraints(iM825getMinWidthimpl, iM823getMaxWidthimpl, iM824getMinHeightimpl, (iM822getMaxHeightimpl == Integer.MAX_VALUE || (iM822getMaxHeightimpl = iM822getMaxHeightimpl + i2) >= 0) ? iM822getMaxHeightimpl : 0);
    }

    /* renamed from: offset-NN6Ew-U$default, reason: not valid java name */
    public static /* synthetic */ long m836offsetNN6EwU$default(int i, int i2, int i3, long j) {
        if ((i3 & 1) != 0) {
            i = 0;
        }
        if ((i3 & 2) != 0) {
            i2 = 0;
        }
        return m835offsetNN6EwU(i, i2, j);
    }

    public static final void throwInvalidConstraintException(int i, int i2) {
        throw new IllegalArgumentException(MutableVectorKt$$ExternalSyntheticOutline0.m(i, i2, "Can't represent a width of ", " and height of ", " in Constraints"));
    }

    public static final Void throwInvalidConstraintsSizeException(int i) {
        throw new IllegalArgumentException(ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(i, "Can't represent a size of ", " in Constraints"));
    }
}
