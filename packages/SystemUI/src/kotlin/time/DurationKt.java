package kotlin.time;

import kotlin.math.MathKt__MathJVMKt;
import kotlin.ranges.RangesKt___RangesKt;
import kotlin.time.Duration;

/* loaded from: classes4.dex */
public abstract class DurationKt {
    public static final long durationOfMillis(long j) {
        long j2 = (j << 1) + 1;
        Duration.Companion companion = Duration.Companion;
        int i = DurationJvmKt.$r8$clinit;
        return j2;
    }

    public static final long durationOfMillisNormalized(long j) {
        return (-4611686018426L > j || j >= 4611686018427L) ? durationOfMillis(RangesKt___RangesKt.coerceIn(j, -4611686018427387903L, 4611686018427387903L)) : durationOfNanos(j * 1000000);
    }

    public static final long durationOfNanos(long j) {
        long j2 = j << 1;
        Duration.Companion companion = Duration.Companion;
        int i = DurationJvmKt.$r8$clinit;
        return j2;
    }

    public static final long toDuration(int i, DurationUnit durationUnit) {
        if (durationUnit.compareTo(DurationUnit.SECONDS) > 0) {
            return toDuration(i, durationUnit);
        }
        return durationOfNanos(DurationUnit.NANOSECONDS.getTimeUnit$kotlin_stdlib().convert(i, durationUnit.getTimeUnit$kotlin_stdlib()));
    }

    public static final long toDuration(long j, DurationUnit durationUnit) {
        DurationUnit durationUnit2 = DurationUnit.NANOSECONDS;
        long jConvert = durationUnit.getTimeUnit$kotlin_stdlib().convert(4611686018426999999L, durationUnit2.getTimeUnit$kotlin_stdlib());
        if ((-jConvert) <= j && j <= jConvert) {
            return durationOfNanos(durationUnit2.getTimeUnit$kotlin_stdlib().convert(j, durationUnit.getTimeUnit$kotlin_stdlib()));
        }
        return durationOfMillis(RangesKt___RangesKt.coerceIn(DurationUnit.MILLISECONDS.getTimeUnit$kotlin_stdlib().convert(j, durationUnit.getTimeUnit$kotlin_stdlib()), -4611686018427387903L, 4611686018427387903L));
    }

    public static final long toDuration(double d, DurationUnit durationUnit) {
        double dConvertDurationUnit = DurationUnitKt__DurationUnitJvmKt.convertDurationUnit(d, durationUnit, DurationUnit.NANOSECONDS);
        if (!Double.isNaN(dConvertDurationUnit)) {
            long jRoundToLong = MathKt__MathJVMKt.roundToLong(dConvertDurationUnit);
            if (-4611686018426999999L <= jRoundToLong && jRoundToLong < 4611686018427000000L) {
                return durationOfNanos(jRoundToLong);
            }
            return durationOfMillisNormalized(MathKt__MathJVMKt.roundToLong(DurationUnitKt__DurationUnitJvmKt.convertDurationUnit(d, durationUnit, DurationUnit.MILLISECONDS)));
        }
        throw new IllegalArgumentException("Duration value cannot be NaN.");
    }
}
