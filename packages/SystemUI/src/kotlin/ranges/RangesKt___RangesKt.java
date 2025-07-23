package kotlin.ranges;

import androidx.compose.runtime.snapshots.SnapshotStateObserver$$ExternalSyntheticOutline0;
import kotlin.ranges.IntProgression;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class RangesKt___RangesKt extends RangesKt__RangesKt {
    public static int coerceIn(int i, int i2, int i3) {
        if (i2 <= i3) {
            return i < i2 ? i2 : i > i3 ? i3 : i;
        }
        throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + i3 + " is less than minimum " + i2 + '.');
    }

    public static IntProgression reversed(IntRange intRange) {
        IntProgression.Companion companion = IntProgression.Companion;
        int i = intRange.last;
        int i2 = intRange.first;
        int i3 = -intRange.step;
        companion.getClass();
        return new IntProgression(i, i2, i3);
    }

    public static IntProgression step(IntRange intRange, int i) {
        boolean z = i > 0;
        Integer valueOf = Integer.valueOf(i);
        if (!z) {
            throw new IllegalArgumentException("Step must be positive, was: " + valueOf + '.');
        }
        IntProgression.Companion companion = IntProgression.Companion;
        int i2 = intRange.first;
        int i3 = intRange.last;
        if (intRange.step <= 0) {
            i = -i;
        }
        companion.getClass();
        return new IntProgression(i2, i3, i);
    }

    public static IntRange until(int i, int i2) {
        if (i2 > Integer.MIN_VALUE) {
            return new IntRange(i, i2 - 1);
        }
        IntRange.Companion.getClass();
        return IntRange.EMPTY;
    }

    public static long coerceIn(long j, long j2, long j3) {
        if (j2 <= j3) {
            return j < j2 ? j2 : j > j3 ? j3 : j;
        }
        StringBuilder m = SnapshotStateObserver$$ExternalSyntheticOutline0.m("Cannot coerce value to an empty range: maximum ", j3, " is less than minimum ");
        m.append(j2);
        m.append('.');
        throw new IllegalArgumentException(m.toString());
    }

    public static float coerceIn(float f, float f2, float f3) {
        if (f2 <= f3) {
            return f < f2 ? f2 : f > f3 ? f3 : f;
        }
        throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + f3 + " is less than minimum " + f2 + '.');
    }

    public static double coerceIn(double d, double d2, double d3) {
        if (d2 <= d3) {
            return d < d2 ? d2 : d > d3 ? d3 : d;
        }
        throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + d3 + " is less than minimum " + d2 + '.');
    }

    public static Comparable coerceIn(Comparable comparable, ClosedFloatingPointRange closedFloatingPointRange) {
        ClosedFloatRange closedFloatRange = (ClosedFloatRange) closedFloatingPointRange;
        if (!closedFloatRange.isEmpty()) {
            float f = closedFloatRange._start;
            if (closedFloatRange.lessThanOrEquals(comparable, Float.valueOf(f)) && !closedFloatRange.lessThanOrEquals(Float.valueOf(f), comparable)) {
                return Float.valueOf(f);
            }
            float f2 = closedFloatRange._endInclusive;
            return (!closedFloatRange.lessThanOrEquals(Float.valueOf(f2), comparable) || closedFloatRange.lessThanOrEquals(comparable, Float.valueOf(f2))) ? comparable : Float.valueOf(f2);
        }
        throw new IllegalArgumentException("Cannot coerce value to an empty range: " + closedFloatRange + '.');
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static long coerceIn(long j, LongRange longRange) {
        if (longRange instanceof ClosedFloatingPointRange) {
            return ((Number) coerceIn(Long.valueOf(j), (ClosedFloatingPointRange) longRange)).longValue();
        }
        if (!longRange.isEmpty()) {
            if (j < Long.valueOf(longRange.first).longValue()) {
                return Long.valueOf(longRange.first).longValue();
            }
            return j > Long.valueOf(longRange.last).longValue() ? Long.valueOf(longRange.last).longValue() : j;
        }
        throw new IllegalArgumentException("Cannot coerce value to an empty range: " + longRange + '.');
    }
}
