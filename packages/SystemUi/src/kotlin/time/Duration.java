package kotlin.time;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.ranges.LongRange;
import kotlin.ranges.RangesKt___RangesKt;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class Duration implements Comparable {
    public static final Companion Companion = new Companion(null);
    public static final long INFINITE;
    public static final long NEG_INFINITE;
    public final long rawValue;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        int i = DurationJvmKt.$r8$clinit;
        INFINITE = DurationKt.durationOfMillis(4611686018427387903L);
        NEG_INFINITE = DurationKt.durationOfMillis(-4611686018427387903L);
    }

    /* renamed from: addValuesMixedRanges-UwyO8pc, reason: not valid java name */
    public static final long m2860addValuesMixedRangesUwyO8pc(long j, long j2) {
        long j3 = 1000000;
        long j4 = j2 / j3;
        long j5 = j + j4;
        if (!new LongRange(-4611686018426L, 4611686018426L).contains(j5)) {
            return DurationKt.durationOfMillis(RangesKt___RangesKt.coerceIn(j5));
        }
        long j6 = ((j5 * j3) + (j2 - (j4 * j3))) << 1;
        int i = DurationJvmKt.$r8$clinit;
        return j6;
    }

    /* renamed from: appendFractional-impl, reason: not valid java name */
    public static final void m2861appendFractionalimpl(StringBuilder sb, int i, int i2, int i3, String str) {
        sb.append(i);
        if (i2 != 0) {
            sb.append('.');
            String padStart = StringsKt__StringsKt.padStart(String.valueOf(i2), i3);
            int i4 = -1;
            int length = padStart.length() - 1;
            if (length >= 0) {
                while (true) {
                    int i5 = length - 1;
                    if (padStart.charAt(length) != '0') {
                        i4 = length;
                        break;
                    } else if (i5 < 0) {
                        break;
                    } else {
                        length = i5;
                    }
                }
            }
            int i6 = i4 + 1;
            if (i6 < 3) {
                sb.append((CharSequence) padStart, 0, i6);
            } else {
                sb.append((CharSequence) padStart, 0, ((i6 + 2) / 3) * 3);
            }
        }
        sb.append(str);
    }

    /* renamed from: compareTo-LRDsOJo, reason: not valid java name */
    public static int m2862compareToLRDsOJo(long j, long j2) {
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

    /* renamed from: getInWholeMilliseconds-impl, reason: not valid java name */
    public static final long m2863getInWholeMillisecondsimpl(long j) {
        return (((((int) j) & 1) == 1) && (m2864isInfiniteimpl(j) ^ true)) ? j >> 1 : m2866toLongimpl(j, DurationUnit.MILLISECONDS);
    }

    /* renamed from: isInfinite-impl, reason: not valid java name */
    public static final boolean m2864isInfiniteimpl(long j) {
        return j == INFINITE || j == NEG_INFINITE;
    }

    /* renamed from: toDouble-impl, reason: not valid java name */
    public static final double m2865toDoubleimpl(long j, DurationUnit durationUnit) {
        if (j == INFINITE) {
            return Double.POSITIVE_INFINITY;
        }
        if (j == NEG_INFINITE) {
            return Double.NEGATIVE_INFINITY;
        }
        double d = j >> 1;
        long convert = durationUnit.getTimeUnit$kotlin_stdlib().convert(1L, ((((int) j) & 1) == 0 ? DurationUnit.NANOSECONDS : DurationUnit.MILLISECONDS).getTimeUnit$kotlin_stdlib());
        return convert > 0 ? d * convert : d / r9.getTimeUnit$kotlin_stdlib().convert(1L, durationUnit.getTimeUnit$kotlin_stdlib());
    }

    /* renamed from: toLong-impl, reason: not valid java name */
    public static final long m2866toLongimpl(long j, DurationUnit durationUnit) {
        if (j == INFINITE) {
            return Long.MAX_VALUE;
        }
        if (j == NEG_INFINITE) {
            return Long.MIN_VALUE;
        }
        return durationUnit.getTimeUnit$kotlin_stdlib().convert(j >> 1, ((((int) j) & 1) == 0 ? DurationUnit.NANOSECONDS : DurationUnit.MILLISECONDS).getTimeUnit$kotlin_stdlib());
    }

    /* renamed from: toString-impl, reason: not valid java name */
    public static String m2867toStringimpl(long j) {
        int i;
        int i2;
        long j2 = j;
        if (j2 == 0) {
            return "0s";
        }
        if (j2 == INFINITE) {
            return "Infinity";
        }
        if (j2 == NEG_INFINITE) {
            return "-Infinity";
        }
        boolean z = j2 < 0;
        StringBuilder sb = new StringBuilder();
        if (z) {
            sb.append('-');
        }
        if (j2 < 0) {
            j2 = (((int) j2) & 1) + ((-(j2 >> 1)) << 1);
            int i3 = DurationJvmKt.$r8$clinit;
        }
        long m2866toLongimpl = m2866toLongimpl(j2, DurationUnit.DAYS);
        int m2866toLongimpl2 = m2864isInfiniteimpl(j2) ? 0 : (int) (m2866toLongimpl(j2, DurationUnit.HOURS) % 24);
        int m2866toLongimpl3 = m2864isInfiniteimpl(j2) ? 0 : (int) (m2866toLongimpl(j2, DurationUnit.MINUTES) % 60);
        int m2866toLongimpl4 = m2864isInfiniteimpl(j2) ? 0 : (int) (m2866toLongimpl(j2, DurationUnit.SECONDS) % 60);
        if (m2864isInfiniteimpl(j2)) {
            i = 0;
        } else {
            i = (int) ((((int) j2) & 1) == 1 ? ((j2 >> 1) % 1000) * 1000000 : (j2 >> 1) % 1000000000);
        }
        boolean z2 = m2866toLongimpl != 0;
        boolean z3 = m2866toLongimpl2 != 0;
        boolean z4 = m2866toLongimpl3 != 0;
        boolean z5 = (m2866toLongimpl4 == 0 && i == 0) ? false : true;
        if (z2) {
            sb.append(m2866toLongimpl);
            sb.append('d');
            i2 = 1;
        } else {
            i2 = 0;
        }
        if (z3 || (z2 && (z4 || z5))) {
            int i4 = i2 + 1;
            if (i2 > 0) {
                sb.append(' ');
            }
            sb.append(m2866toLongimpl2);
            sb.append('h');
            i2 = i4;
        }
        if (z4 || (z5 && (z3 || z2))) {
            int i5 = i2 + 1;
            if (i2 > 0) {
                sb.append(' ');
            }
            sb.append(m2866toLongimpl3);
            sb.append('m');
            i2 = i5;
        }
        if (z5) {
            int i6 = i2 + 1;
            if (i2 > 0) {
                sb.append(' ');
            }
            if (m2866toLongimpl4 != 0 || z2 || z3 || z4) {
                m2861appendFractionalimpl(sb, m2866toLongimpl4, i, 9, "s");
            } else if (i >= 1000000) {
                m2861appendFractionalimpl(sb, i / 1000000, i % 1000000, 6, "ms");
            } else if (i >= 1000) {
                m2861appendFractionalimpl(sb, i / 1000, i % 1000, 3, "us");
            } else {
                sb.append(i);
                sb.append("ns");
            }
            i2 = i6;
        }
        if (z && i2 > 1) {
            sb.insert(1, '(').append(')');
        }
        return sb.toString();
    }

    @Override // java.lang.Comparable
    public final int compareTo(Object obj) {
        return m2862compareToLRDsOJo(this.rawValue, ((Duration) obj).rawValue);
    }

    public final boolean equals(Object obj) {
        return (obj instanceof Duration) && this.rawValue == ((Duration) obj).rawValue;
    }

    public final int hashCode() {
        return Long.hashCode(this.rawValue);
    }

    public final String toString() {
        return m2867toStringimpl(this.rawValue);
    }
}
