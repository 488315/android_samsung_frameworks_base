package kotlin.time;

import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.ranges.LongRange;
import kotlin.ranges.RangesKt___RangesKt;

/* loaded from: classes4.dex */
public final class Duration implements Comparable {
    public static final Companion Companion = new Companion(null);
    public static final long INFINITE;
    public static final long NEG_INFINITE;
    public final long rawValue;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        int i = DurationJvmKt.$r8$clinit;
        INFINITE = DurationKt.durationOfMillis(4611686018427387903L);
        NEG_INFINITE = DurationKt.durationOfMillis(-4611686018427387903L);
    }

    private /* synthetic */ Duration(long j) {
        this.rawValue = j;
    }

    /* renamed from: addValuesMixedRanges-UwyO8pc, reason: not valid java name */
    public static final long m3452addValuesMixedRangesUwyO8pc(long j, long j2) {
        long j3 = 1000000;
        long j4 = j2 / j3;
        long j5 = j + j4;
        if (-4611686018426L > j5 || j5 >= 4611686018427L) {
            return DurationKt.durationOfMillis(RangesKt___RangesKt.coerceIn(j5, -4611686018427387903L, 4611686018427387903L));
        }
        return DurationKt.durationOfNanos((j5 * j3) + (j2 - (j4 * j3)));
    }

    /* renamed from: appendFractional-impl, reason: not valid java name */
    public static final void m3453appendFractionalimpl(StringBuilder sb, int i, int i2, int i3, String str, boolean z) {
        CharSequence charSequenceSubSequence;
        sb.append(i);
        if (i2 != 0) {
            sb.append('.');
            String strValueOf = String.valueOf(i2);
            if (i3 < 0) {
                throw new IllegalArgumentException(ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(i3, "Desired length ", " is less than zero."));
            }
            if (i3 <= strValueOf.length()) {
                charSequenceSubSequence = strValueOf.subSequence(0, strValueOf.length());
            } else {
                StringBuilder sb2 = new StringBuilder(i3);
                int length = i3 - strValueOf.length();
                int i4 = 1;
                if (1 <= length) {
                    while (true) {
                        sb2.append('0');
                        if (i4 == length) {
                            break;
                        } else {
                            i4++;
                        }
                    }
                }
                sb2.append((CharSequence) strValueOf);
                charSequenceSubSequence = sb2;
            }
            String string = charSequenceSubSequence.toString();
            int i5 = -1;
            int length2 = string.length() - 1;
            if (length2 >= 0) {
                while (true) {
                    int i6 = length2 - 1;
                    if (string.charAt(length2) != '0') {
                        i5 = length2;
                        break;
                    } else if (i6 < 0) {
                        break;
                    } else {
                        length2 = i6;
                    }
                }
            }
            int i7 = i5 + 1;
            if (z || i7 >= 3) {
                sb.append((CharSequence) string, 0, ((i5 + 3) / 3) * 3);
            } else {
                sb.append((CharSequence) string, 0, i7);
            }
        }
        sb.append(str);
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ Duration m3454boximpl(long j) {
        return new Duration(j);
    }

    /* renamed from: compareTo-LRDsOJo, reason: not valid java name */
    public static int m3455compareToLRDsOJo(long j, long j2) {
        long j3 = j ^ j2;
        if (j3 >= 0 && (((int) j3) & 1) != 0) {
            int i = (((int) j) & 1) - (((int) j2) & 1);
            return j < 0 ? -i : i;
        }
        if (j < j2) {
            return -1;
        }
        return j == j2 ? 0 : 1;
    }

    /* renamed from: div-LRDsOJo, reason: not valid java name */
    public static final double m3456divLRDsOJo(long j, long j2) {
        DurationUnit durationUnitM3459getStorageUnitimpl = m3459getStorageUnitimpl(j);
        DurationUnit durationUnitM3459getStorageUnitimpl2 = m3459getStorageUnitimpl(j2);
        if (durationUnitM3459getStorageUnitimpl.compareTo(durationUnitM3459getStorageUnitimpl2) < 0) {
            durationUnitM3459getStorageUnitimpl = durationUnitM3459getStorageUnitimpl2;
        }
        return m3463toDoubleimpl(j, durationUnitM3459getStorageUnitimpl) / m3463toDoubleimpl(j2, durationUnitM3459getStorageUnitimpl);
    }

    /* renamed from: getInWholeMilliseconds-impl, reason: not valid java name */
    public static final long m3457getInWholeMillisecondsimpl(long j) {
        return ((((int) j) & 1) != 1 || m3460isInfiniteimpl(j)) ? m3465toLongimpl(j, DurationUnit.MILLISECONDS) : j >> 1;
    }

    /* renamed from: getNanosecondsComponent-impl, reason: not valid java name */
    public static final int m3458getNanosecondsComponentimpl(long j) {
        if (m3460isInfiniteimpl(j)) {
            return 0;
        }
        return (((int) j) & 1) == 1 ? (int) (((j >> 1) % 1000) * 1000000) : (int) ((j >> 1) % 1000000000);
    }

    /* renamed from: getStorageUnit-impl, reason: not valid java name */
    public static final DurationUnit m3459getStorageUnitimpl(long j) {
        return (((int) j) & 1) == 0 ? DurationUnit.NANOSECONDS : DurationUnit.MILLISECONDS;
    }

    /* renamed from: isInfinite-impl, reason: not valid java name */
    public static final boolean m3460isInfiniteimpl(long j) {
        return j == INFINITE || j == NEG_INFINITE;
    }

    /* renamed from: plus-LRDsOJo, reason: not valid java name */
    public static final long m3461plusLRDsOJo(long j, long j2) {
        if (m3460isInfiniteimpl(j)) {
            if (!m3460isInfiniteimpl(j2) || (j2 ^ j) >= 0) {
                return j;
            }
            throw new IllegalArgumentException("Summing infinite durations of different signs yields an undefined result.");
        }
        if (m3460isInfiniteimpl(j2)) {
            return j2;
        }
        int i = ((int) j) & 1;
        if (i != (((int) j2) & 1)) {
            return i == 1 ? m3452addValuesMixedRangesUwyO8pc(j >> 1, j2 >> 1) : m3452addValuesMixedRangesUwyO8pc(j2 >> 1, j >> 1);
        }
        long j3 = (j >> 1) + (j2 >> 1);
        return i == 0 ? (-4611686018426999999L > j3 || j3 >= 4611686018427000000L) ? DurationKt.durationOfMillis(j3 / 1000000) : DurationKt.durationOfNanos(j3) : DurationKt.durationOfMillisNormalized(j3);
    }

    /* renamed from: times-UwyO8pc, reason: not valid java name */
    public static final long m3462timesUwyO8pc(int i, long j) {
        if (m3460isInfiniteimpl(j)) {
            if (i != 0) {
                return i > 0 ? j : m3467unaryMinusUwyO8pc(j);
            }
            throw new IllegalArgumentException("Multiplying infinite duration by zero yields an undefined result.");
        }
        if (i == 0) {
            return 0L;
        }
        long j2 = j >> 1;
        long j3 = i;
        long j4 = j2 * j3;
        if (!((((int) j) & 1) == 0)) {
            if (j4 / j3 == j2) {
                return DurationKt.durationOfMillis(RangesKt___RangesKt.coerceIn(j4, new LongRange(-4611686018427387903L, 4611686018427387903L)));
            }
            return Integer.signum(i) * Long.signum(j2) > 0 ? INFINITE : NEG_INFINITE;
        }
        if (-2147483647L <= j2 && j2 < 2147483648L) {
            return DurationKt.durationOfNanos(j4);
        }
        if (j4 / j3 == j2) {
            return (-4611686018426999999L > j4 || j4 >= 4611686018427000000L) ? DurationKt.durationOfMillis(j4 / 1000000) : DurationKt.durationOfNanos(j4);
        }
        long j5 = 1000000;
        long j6 = j2 / j5;
        long j7 = j6 * j3;
        long j8 = (((j2 - (j6 * j5)) * j3) / j5) + j7;
        if (j7 / j3 != j6 || (j8 ^ j7) < 0) {
            return Integer.signum(i) * Long.signum(j2) > 0 ? INFINITE : NEG_INFINITE;
        }
        return DurationKt.durationOfMillis(RangesKt___RangesKt.coerceIn(j8, new LongRange(-4611686018427387903L, 4611686018427387903L)));
    }

    /* renamed from: toDouble-impl, reason: not valid java name */
    public static final double m3463toDoubleimpl(long j, DurationUnit durationUnit) {
        if (j == INFINITE) {
            return Double.POSITIVE_INFINITY;
        }
        if (j == NEG_INFINITE) {
            return Double.NEGATIVE_INFINITY;
        }
        return DurationUnitKt__DurationUnitJvmKt.convertDurationUnit(j >> 1, m3459getStorageUnitimpl(j), durationUnit);
    }

    /* renamed from: toInt-impl, reason: not valid java name */
    public static final int m3464toIntimpl(long j, DurationUnit durationUnit) {
        return (int) RangesKt___RangesKt.coerceIn(m3465toLongimpl(j, durationUnit), -2147483648L, 2147483647L);
    }

    /* renamed from: toLong-impl, reason: not valid java name */
    public static final long m3465toLongimpl(long j, DurationUnit durationUnit) {
        if (j == INFINITE) {
            return Long.MAX_VALUE;
        }
        if (j == NEG_INFINITE) {
            return Long.MIN_VALUE;
        }
        return durationUnit.getTimeUnit$kotlin_stdlib().convert(j >> 1, m3459getStorageUnitimpl(j).getTimeUnit$kotlin_stdlib());
    }

    /* renamed from: toString-impl, reason: not valid java name */
    public static String m3466toStringimpl(long j) {
        long j2;
        int iM3465toLongimpl;
        if (j == 0) {
            return "0s";
        }
        if (j == INFINITE) {
            return "Infinity";
        }
        if (j == NEG_INFINITE) {
            return "-Infinity";
        }
        int i = 0;
        boolean z = j < 0;
        StringBuilder sb = new StringBuilder();
        if (z) {
            sb.append('-');
        }
        long jM3467unaryMinusUwyO8pc = j < 0 ? m3467unaryMinusUwyO8pc(j) : j;
        long jM3465toLongimpl = m3465toLongimpl(jM3467unaryMinusUwyO8pc, DurationUnit.DAYS);
        int iM3465toLongimpl2 = m3460isInfiniteimpl(jM3467unaryMinusUwyO8pc) ? 0 : (int) (m3465toLongimpl(jM3467unaryMinusUwyO8pc, DurationUnit.HOURS) % 24);
        if (m3460isInfiniteimpl(jM3467unaryMinusUwyO8pc)) {
            j2 = 0;
            iM3465toLongimpl = 0;
        } else {
            j2 = 0;
            iM3465toLongimpl = (int) (m3465toLongimpl(jM3467unaryMinusUwyO8pc, DurationUnit.MINUTES) % 60);
        }
        int iM3465toLongimpl3 = m3460isInfiniteimpl(jM3467unaryMinusUwyO8pc) ? 0 : (int) (m3465toLongimpl(jM3467unaryMinusUwyO8pc, DurationUnit.SECONDS) % 60);
        int iM3458getNanosecondsComponentimpl = m3458getNanosecondsComponentimpl(jM3467unaryMinusUwyO8pc);
        boolean z2 = jM3465toLongimpl != j2;
        boolean z3 = iM3465toLongimpl2 != 0;
        boolean z4 = iM3465toLongimpl != 0;
        boolean z5 = (iM3465toLongimpl3 == 0 && iM3458getNanosecondsComponentimpl == 0) ? false : true;
        if (z2) {
            sb.append(jM3465toLongimpl);
            sb.append('d');
            i = 1;
        }
        if (z3 || (z2 && (z4 || z5))) {
            int i2 = i + 1;
            if (i > 0) {
                sb.append(' ');
            }
            sb.append(iM3465toLongimpl2);
            sb.append('h');
            i = i2;
        }
        if (z4 || (z5 && (z3 || z2))) {
            int i3 = i + 1;
            if (i > 0) {
                sb.append(' ');
            }
            sb.append(iM3465toLongimpl);
            sb.append('m');
            i = i3;
        }
        if (z5) {
            int i4 = i + 1;
            if (i > 0) {
                sb.append(' ');
            }
            if (iM3465toLongimpl3 != 0 || z2 || z3 || z4) {
                m3453appendFractionalimpl(sb, iM3465toLongimpl3, iM3458getNanosecondsComponentimpl, 9, "s", false);
            } else if (iM3458getNanosecondsComponentimpl >= 1000000) {
                m3453appendFractionalimpl(sb, iM3458getNanosecondsComponentimpl / 1000000, iM3458getNanosecondsComponentimpl % 1000000, 6, "ms", false);
            } else if (iM3458getNanosecondsComponentimpl >= 1000) {
                m3453appendFractionalimpl(sb, iM3458getNanosecondsComponentimpl / 1000, iM3458getNanosecondsComponentimpl % 1000, 3, "us", false);
            } else {
                sb.append(iM3458getNanosecondsComponentimpl);
                sb.append("ns");
            }
            i = i4;
        }
        if (z && i > 1) {
            sb.insert(1, '(').append(')');
        }
        return sb.toString();
    }

    /* renamed from: unaryMinus-UwyO8pc, reason: not valid java name */
    public static final long m3467unaryMinusUwyO8pc(long j) {
        long j2 = ((-(j >> 1)) << 1) + (((int) j) & 1);
        int i = DurationJvmKt.$r8$clinit;
        return j2;
    }

    @Override // java.lang.Comparable
    public final int compareTo(Object obj) {
        return m3455compareToLRDsOJo(this.rawValue, ((Duration) obj).rawValue);
    }

    public final boolean equals(Object obj) {
        return (obj instanceof Duration) && this.rawValue == ((Duration) obj).rawValue;
    }

    public final int hashCode() {
        return Long.hashCode(this.rawValue);
    }

    public final String toString() {
        return m3466toStringimpl(this.rawValue);
    }
}
