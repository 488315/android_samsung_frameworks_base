package kotlin.time;

import kotlin.time.Duration;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class LongSaturatedMathKt {
    public static final long infinityOfSign(long j) {
        if (j < 0) {
            Duration.Companion.getClass();
            return Duration.NEG_INFINITE;
        }
        Duration.Companion.getClass();
        return Duration.INFINITE;
    }

    public static final long saturatingFiniteDiff(long j, long j2, DurationUnit durationUnit) {
        long j3 = j - j2;
        if (((j3 ^ j) & (~(j3 ^ j2))) >= 0) {
            return DurationKt.toDuration(j3, durationUnit);
        }
        DurationUnit durationUnit2 = DurationUnit.MILLISECONDS;
        if (durationUnit.compareTo(durationUnit2) >= 0) {
            return Duration.m3447unaryMinusUwyO8pc(infinityOfSign(j3));
        }
        long convert = durationUnit.getTimeUnit$kotlin_stdlib().convert(1L, durationUnit2.getTimeUnit$kotlin_stdlib());
        long j4 = (j / convert) - (j2 / convert);
        long j5 = (j % convert) - (j2 % convert);
        Duration.Companion companion = Duration.Companion;
        return Duration.m3441plusLRDsOJo(DurationKt.toDuration(j4, durationUnit2), DurationKt.toDuration(j5, durationUnit));
    }
}
