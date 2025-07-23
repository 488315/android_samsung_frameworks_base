package androidx.compose.ui.unit;

import androidx.compose.runtime.OpaqueKey$$ExternalSyntheticOutline0;
import kotlin.KotlinNothingValueException;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class Constraints {
    public static final Companion Companion = new Companion(null);
    public final long value;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* renamed from: fitPrioritizingHeight-Zbe2FdA, reason: not valid java name */
        public static long m825fitPrioritizingHeightZbe2FdA(int i, int i2, int i3, int i4) {
            int min = Math.min(i3, 262142);
            int min2 = i4 == Integer.MAX_VALUE ? Integer.MAX_VALUE : Math.min(i4, 262142);
            int i5 = min2 == Integer.MAX_VALUE ? min : min2;
            int numberOfLeadingZeros = Integer.numberOfLeadingZeros(i5 + 1);
            if (numberOfLeadingZeros > 13) {
                int i6 = numberOfLeadingZeros < 19 ? numberOfLeadingZeros >= 17 ? 65534 : numberOfLeadingZeros >= 16 ? 32766 : 8190 : 262142;
                return ConstraintsKt.Constraints(Math.min(i6, i), i2 != Integer.MAX_VALUE ? Math.min(i6, i2) : Integer.MAX_VALUE, min, min2);
            }
            ConstraintsKt.throwInvalidConstraintsSizeException(i5);
            throw new KotlinNothingValueException();
        }

        /* renamed from: fitPrioritizingWidth-Zbe2FdA, reason: not valid java name */
        public static long m826fitPrioritizingWidthZbe2FdA(int i, int i2, int i3, int i4) {
            int min = Math.min(i, 262142);
            int min2 = i2 == Integer.MAX_VALUE ? Integer.MAX_VALUE : Math.min(i2, 262142);
            int i5 = min2 == Integer.MAX_VALUE ? min : min2;
            int numberOfLeadingZeros = Integer.numberOfLeadingZeros(i5 + 1);
            if (numberOfLeadingZeros > 13) {
                int i6 = numberOfLeadingZeros < 19 ? numberOfLeadingZeros >= 17 ? 65534 : numberOfLeadingZeros >= 16 ? 32766 : 8190 : 262142;
                return ConstraintsKt.Constraints(min, min2, Math.min(i6, i3), i4 != Integer.MAX_VALUE ? Math.min(i6, i4) : Integer.MAX_VALUE);
            }
            ConstraintsKt.throwInvalidConstraintsSizeException(i5);
            throw new KotlinNothingValueException();
        }

        /* renamed from: fixed-JhjzzOo, reason: not valid java name */
        public static long m827fixedJhjzzOo(int i, int i2) {
            if (!((i2 >= 0) & (i >= 0))) {
                InlineClassHelperKt.throwIllegalArgumentException("width and height must be >= 0");
            }
            return ConstraintsKt.createConstraints(i, i, i2, i2);
        }

        /* renamed from: fixedWidth-OenEA2s, reason: not valid java name */
        public static long m828fixedWidthOenEA2s(int i) {
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
    public static final /* synthetic */ Constraints m813boximpl(long j) {
        return new Constraints(j);
    }

    /* renamed from: copy-Zbe2FdA$default, reason: not valid java name */
    public static long m814copyZbe2FdA$default(long j, int i, int i2, int i3, int i4, int i5) {
        if ((i5 & 1) != 0) {
            i = m823getMinWidthimpl(j);
        }
        if ((i5 & 2) != 0) {
            i2 = m821getMaxWidthimpl(j);
        }
        if ((i5 & 4) != 0) {
            i3 = m822getMinHeightimpl(j);
        }
        if ((i5 & 8) != 0) {
            i4 = m820getMaxHeightimpl(j);
        }
        if (i2 < i || i4 < i3 || i < 0 || i3 < 0) {
            InlineClassHelperKt.throwIllegalArgumentException("maxWidth must be >= than minWidth,\nmaxHeight must be >= than minHeight,\nminWidth and minHeight must be >= 0");
        }
        return ConstraintsKt.createConstraints(i, i2, i3, i4);
    }

    /* renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m815equalsimpl0(long j, long j2) {
        return j == j2;
    }

    /* renamed from: getHasBoundedHeight-impl, reason: not valid java name */
    public static final boolean m816getHasBoundedHeightimpl(long j) {
        int i = (int) (3 & j);
        int i2 = (((i & 2) >> 1) * 3) + ((i & 1) << 1);
        return (((int) (j >> (i2 + 46))) & ((1 << (18 - i2)) - 1)) != 0;
    }

    /* renamed from: getHasBoundedWidth-impl, reason: not valid java name */
    public static final boolean m817getHasBoundedWidthimpl(long j) {
        int i = (int) (3 & j);
        return (((int) (j >> 33)) & ((1 << (((((i & 2) >> 1) * 3) + ((i & 1) << 1)) + 13)) - 1)) != 0;
    }

    /* renamed from: getHasFixedHeight-impl, reason: not valid java name */
    public static final boolean m818getHasFixedHeightimpl(long j) {
        int i = (int) (3 & j);
        int i2 = (((i & 2) >> 1) * 3) + ((i & 1) << 1);
        int i3 = (1 << (18 - i2)) - 1;
        int i4 = ((int) (j >> (i2 + 15))) & i3;
        int i5 = ((int) (j >> (i2 + 46))) & i3;
        return i4 == (i5 == 0 ? Integer.MAX_VALUE : i5 - 1);
    }

    /* renamed from: getHasFixedWidth-impl, reason: not valid java name */
    public static final boolean m819getHasFixedWidthimpl(long j) {
        int i = (int) (3 & j);
        int i2 = (1 << (((((i & 2) >> 1) * 3) + ((i & 1) << 1)) + 13)) - 1;
        int i3 = ((int) (j >> 2)) & i2;
        int i4 = ((int) (j >> 33)) & i2;
        return i3 == (i4 == 0 ? Integer.MAX_VALUE : i4 - 1);
    }

    /* renamed from: getMaxHeight-impl, reason: not valid java name */
    public static final int m820getMaxHeightimpl(long j) {
        int i = (int) (3 & j);
        int i2 = (((i & 2) >> 1) * 3) + ((i & 1) << 1);
        int i3 = ((int) (j >> (i2 + 46))) & ((1 << (18 - i2)) - 1);
        if (i3 == 0) {
            return Integer.MAX_VALUE;
        }
        return i3 - 1;
    }

    /* renamed from: getMaxWidth-impl, reason: not valid java name */
    public static final int m821getMaxWidthimpl(long j) {
        int i = (int) (3 & j);
        int i2 = (int) (j >> 33);
        int i3 = i2 & ((1 << (((((i & 2) >> 1) * 3) + ((i & 1) << 1)) + 13)) - 1);
        if (i3 == 0) {
            return Integer.MAX_VALUE;
        }
        return i3 - 1;
    }

    /* renamed from: getMinHeight-impl, reason: not valid java name */
    public static final int m822getMinHeightimpl(long j) {
        int i = (int) (3 & j);
        int i2 = (((i & 2) >> 1) * 3) + ((i & 1) << 1);
        return ((int) (j >> (i2 + 15))) & ((1 << (18 - i2)) - 1);
    }

    /* renamed from: getMinWidth-impl, reason: not valid java name */
    public static final int m823getMinWidthimpl(long j) {
        int i = (int) (3 & j);
        return ((int) (j >> 2)) & ((1 << (((((i & 2) >> 1) * 3) + ((i & 1) << 1)) + 13)) - 1);
    }

    /* renamed from: toString-impl, reason: not valid java name */
    public static String m824toStringimpl(long j) {
        int m821getMaxWidthimpl = m821getMaxWidthimpl(j);
        String valueOf = m821getMaxWidthimpl == Integer.MAX_VALUE ? "Infinity" : String.valueOf(m821getMaxWidthimpl);
        int m820getMaxHeightimpl = m820getMaxHeightimpl(j);
        String valueOf2 = m820getMaxHeightimpl != Integer.MAX_VALUE ? String.valueOf(m820getMaxHeightimpl) : "Infinity";
        StringBuilder sb = new StringBuilder("Constraints(minWidth = ");
        sb.append(m823getMinWidthimpl(j));
        sb.append(", maxWidth = ");
        sb.append(valueOf);
        sb.append(", minHeight = ");
        sb.append(m822getMinHeightimpl(j));
        sb.append(", maxHeight = ");
        return OpaqueKey$$ExternalSyntheticOutline0.m(sb, valueOf2, ')');
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
        return m824toStringimpl(this.value);
    }
}
