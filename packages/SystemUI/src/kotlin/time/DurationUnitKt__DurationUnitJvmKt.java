package kotlin.time;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class DurationUnitKt__DurationUnitJvmKt {
    public static final double convertDurationUnit(double d, DurationUnit durationUnit, DurationUnit durationUnit2) {
        long convert = durationUnit2.getTimeUnit$kotlin_stdlib().convert(1L, durationUnit.getTimeUnit$kotlin_stdlib());
        return convert > 0 ? d * convert : d / durationUnit.getTimeUnit$kotlin_stdlib().convert(1L, durationUnit2.getTimeUnit$kotlin_stdlib());
    }
}
