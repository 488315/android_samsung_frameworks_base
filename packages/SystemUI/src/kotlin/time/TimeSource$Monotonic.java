package kotlin.time;

/* loaded from: classes4.dex */
public final class TimeSource$Monotonic {
    public static final TimeSource$Monotonic INSTANCE = new TimeSource$Monotonic();

    public final class ValueTimeMark implements Comparable {
        public final long reading;

        /* renamed from: minus-6eNON_k, reason: not valid java name */
        public static final long m3467minus6eNON_k(long j, long j2) {
            MonotonicTimeSource.INSTANCE.getClass();
            DurationUnit durationUnit = DurationUnit.NANOSECONDS;
            if (((j2 - 1) | 1) != Long.MAX_VALUE) {
                return (1 | (j - 1)) == Long.MAX_VALUE ? LongSaturatedMathKt.infinityOfSign(j) : LongSaturatedMathKt.saturatingFiniteDiff(j, j2, durationUnit);
            }
            if (j != j2) {
                return Duration.m3466unaryMinusUwyO8pc(LongSaturatedMathKt.infinityOfSign(j2));
            }
            Duration.Companion.getClass();
            return 0L;
        }

        @Override // java.lang.Comparable
        public final int compareTo(Object obj) {
            ValueTimeMark valueTimeMark = (ValueTimeMark) obj;
            long j = this.reading;
            if (valueTimeMark != null) {
                long jM3467minus6eNON_k = m3467minus6eNON_k(j, valueTimeMark.reading);
                Duration.Companion.getClass();
                return Duration.m3454compareToLRDsOJo(jM3467minus6eNON_k, 0L);
            }
            throw new IllegalArgumentException("Subtracting or comparing time marks from different time sources is not possible: " + ((Object) ("ValueTimeMark(reading=" + j + ')')) + " and " + valueTimeMark);
        }

        public final boolean equals(Object obj) {
            return (obj instanceof ValueTimeMark) && this.reading == ((ValueTimeMark) obj).reading;
        }

        public final int hashCode() {
            return Long.hashCode(this.reading);
        }

        public final String toString() {
            return "ValueTimeMark(reading=" + this.reading + ')';
        }
    }

    private TimeSource$Monotonic() {
    }

    public final String toString() {
        MonotonicTimeSource.INSTANCE.getClass();
        return "TimeSource(System.nanoTime())";
    }
}
