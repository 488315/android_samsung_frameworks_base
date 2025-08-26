package androidx.compose.ui.unit;

import androidx.compose.runtime.OpaqueKey$$ExternalSyntheticOutline0;
import kotlin.KotlinNothingValueException;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class Constraints {
    public static final Companion Companion = new Companion(null);
    public final long value;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* renamed from: fitPrioritizingHeight-Zbe2FdA, reason: not valid java name */
        public static long m827fitPrioritizingHeightZbe2FdA(int i, int i2, int i3, int i4) {
            int iMin = Math.min(i3, 262142);
            int iMin2 = i4 == Integer.MAX_VALUE ? Integer.MAX_VALUE : Math.min(i4, 262142);
            int i5 = iMin2 == Integer.MAX_VALUE ? iMin : iMin2;
            int iNumberOfLeadingZeros = Integer.numberOfLeadingZeros(i5 + 1);
            if (iNumberOfLeadingZeros > 13) {
                int i6 = iNumberOfLeadingZeros < 19 ? iNumberOfLeadingZeros >= 17 ? 65534 : iNumberOfLeadingZeros >= 16 ? 32766 : 8190 : 262142;
                return ConstraintsKt.Constraints(Math.min(i6, i), i2 != Integer.MAX_VALUE ? Math.min(i6, i2) : Integer.MAX_VALUE, iMin, iMin2);
            }
            ConstraintsKt.throwInvalidConstraintsSizeException(i5);
            throw new KotlinNothingValueException();
        }

        /* renamed from: fitPrioritizingWidth-Zbe2FdA, reason: not valid java name */
        public static long m828fitPrioritizingWidthZbe2FdA(int i, int i2, int i3, int i4) {
            int iMin = Math.min(i, 262142);
            int iMin2 = i2 == Integer.MAX_VALUE ? Integer.MAX_VALUE : Math.min(i2, 262142);
            int i5 = iMin2 == Integer.MAX_VALUE ? iMin : iMin2;
            int iNumberOfLeadingZeros = Integer.numberOfLeadingZeros(i5 + 1);
            if (iNumberOfLeadingZeros > 13) {
                int i6 = iNumberOfLeadingZeros < 19 ? iNumberOfLeadingZeros >= 17 ? 65534 : iNumberOfLeadingZeros >= 16 ? 32766 : 8190 : 262142;
                return ConstraintsKt.Constraints(iMin, iMin2, Math.min(i6, i3), i4 != Integer.MAX_VALUE ? Math.min(i6, i4) : Integer.MAX_VALUE);
            }
            ConstraintsKt.throwInvalidConstraintsSizeException(i5);
            throw new KotlinNothingValueException();
        }

        /* renamed from: fixed-JhjzzOo, reason: not valid java name */
        public static long m829fixedJhjzzOo(int i, int i2) {
            if (!((i2 >= 0) & (i >= 0))) {
                InlineClassHelperKt.throwIllegalArgumentException("width and height must be >= 0");
            }
            return ConstraintsKt.createConstraints(i, i, i2, i2);
        }

        /* renamed from: fixedWidth-OenEA2s, reason: not valid java name */
        public static long m830fixedWidthOenEA2s(int i) {
            if (i < 0) {
                InlineClassHelperKt.throwIllegalArgumentException("width must be >= 0");
            }
            return ConstraintsKt.createConstraints(i, i, 0, Integer.MAX_VALUE);
        }

        private Companion() {
        }
    }

    private /* synthetic */ Constraints(long j) {
        this.value = j;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ Constraints m815boximpl(long j) {
        return new Constraints(j);
    }

    /* renamed from: copy-Zbe2FdA$default, reason: not valid java name */
    public static long m816copyZbe2FdA$default(long j, int i, int i2, int i3, int i4, int i5) {
        if ((i5 & 1) != 0) {
            i = m825getMinWidthimpl(j);
        }
        if ((i5 & 2) != 0) {
            i2 = m823getMaxWidthimpl(j);
        }
        if ((i5 & 4) != 0) {
            i3 = m824getMinHeightimpl(j);
        }
        if ((i5 & 8) != 0) {
            i4 = m822getMaxHeightimpl(j);
        }
        if (i2 < i || i4 < i3 || i < 0 || i3 < 0) {
            InlineClassHelperKt.throwIllegalArgumentException("maxWidth must be >= than minWidth,\nmaxHeight must be >= than minHeight,\nminWidth and minHeight must be >= 0");
        }
        return ConstraintsKt.createConstraints(i, i2, i3, i4);
    }

    /* renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m817equalsimpl0(long j, long j2) {
        return j == j2;
    }

    /* renamed from: getHasBoundedHeight-impl, reason: not valid java name */
    public static final boolean m818getHasBoundedHeightimpl(long j) {
        int i = (int) (3 & j);
        int i2 = (((i & 2) >> 1) * 3) + ((i & 1) << 1);
        return (((int) (j >> (i2 + 46))) & ((1 << (18 - i2)) - 1)) != 0;
    }

    /* renamed from: getHasBoundedWidth-impl, reason: not valid java name */
    public static final boolean m819getHasBoundedWidthimpl(long j) {
        int i = (int) (3 & j);
        return (((int) (j >> 33)) & ((1 << (((((i & 2) >> 1) * 3) + ((i & 1) << 1)) + 13)) - 1)) != 0;
    }

    /* renamed from: getHasFixedHeight-impl, reason: not valid java name */
    public static final boolean m820getHasFixedHeightimpl(long j) {
        int i = (int) (3 & j);
        int i2 = (((i & 2) >> 1) * 3) + ((i & 1) << 1);
        int i3 = (1 << (18 - i2)) - 1;
        int i4 = ((int) (j >> (i2 + 15))) & i3;
        int i5 = ((int) (j >> (i2 + 46))) & i3;
        return i4 == (i5 == 0 ? Integer.MAX_VALUE : i5 - 1);
    }

    /* renamed from: getHasFixedWidth-impl, reason: not valid java name */
    public static final boolean m821getHasFixedWidthimpl(long j) {
        int i = (int) (3 & j);
        int i2 = (1 << (((((i & 2) >> 1) * 3) + ((i & 1) << 1)) + 13)) - 1;
        int i3 = ((int) (j >> 2)) & i2;
        int i4 = ((int) (j >> 33)) & i2;
        return i3 == (i4 == 0 ? Integer.MAX_VALUE : i4 - 1);
    }

    /* renamed from: getMaxHeight-impl, reason: not valid java name */
    public static final int m822getMaxHeightimpl(long j) {
        int i = (int) (3 & j);
        int i2 = (((i & 2) >> 1) * 3) + ((i & 1) << 1);
        int i3 = ((int) (j >> (i2 + 46))) & ((1 << (18 - i2)) - 1);
        if (i3 == 0) {
            return Integer.MAX_VALUE;
        }
        return i3 - 1;
    }

    /* renamed from: getMaxWidth-impl, reason: not valid java name */
    public static final int m823getMaxWidthimpl(long j) {
        int i = (int) (3 & j);
        int i2 = (int) (j >> 33);
        int i3 = i2 & ((1 << (((((i & 2) >> 1) * 3) + ((i & 1) << 1)) + 13)) - 1);
        if (i3 == 0) {
            return Integer.MAX_VALUE;
        }
        return i3 - 1;
    }

    /* renamed from: getMinHeight-impl, reason: not valid java name */
    public static final int m824getMinHeightimpl(long j) {
        int i = (int) (3 & j);
        int i2 = (((i & 2) >> 1) * 3) + ((i & 1) << 1);
        return ((int) (j >> (i2 + 15))) & ((1 << (18 - i2)) - 1);
    }

    /* renamed from: getMinWidth-impl, reason: not valid java name */
    public static final int m825getMinWidthimpl(long j) {
        int i = (int) (3 & j);
        return ((int) (j >> 2)) & ((1 << (((((i & 2) >> 1) * 3) + ((i & 1) << 1)) + 13)) - 1);
    }

    /* renamed from: toString-impl, reason: not valid java name */
    public static String m826toStringimpl(long j) {
        int iM823getMaxWidthimpl = m823getMaxWidthimpl(j);
        String strValueOf = iM823getMaxWidthimpl == Integer.MAX_VALUE ? "Infinity" : String.valueOf(iM823getMaxWidthimpl);
        int iM822getMaxHeightimpl = m822getMaxHeightimpl(j);
        String strValueOf2 = iM822getMaxHeightimpl != Integer.MAX_VALUE ? String.valueOf(iM822getMaxHeightimpl) : "Infinity";
        StringBuilder sb = new StringBuilder("Constraints(minWidth = ");
        sb.append(m825getMinWidthimpl(j));
        sb.append(", maxWidth = ");
        sb.append(strValueOf);
        sb.append(", minHeight = ");
        sb.append(m824getMinHeightimpl(j));
        sb.append(", maxHeight = ");
        return OpaqueKey$$ExternalSyntheticOutline0.m(sb, strValueOf2, ')');
    }

    public final boolean equals(Object obj) {
        if (obj instanceof Constraints) {
            return this.value == ((Constraints) obj).value;
        }
        return false;
    }

    public final int hashCode() {
        return Long.hashCode(this.value);
    }

    public final String toString() {
        return m826toStringimpl(this.value);
    }
}
