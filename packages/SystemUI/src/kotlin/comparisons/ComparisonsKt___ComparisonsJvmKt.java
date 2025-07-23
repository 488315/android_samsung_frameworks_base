package kotlin.comparisons;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class ComparisonsKt___ComparisonsJvmKt extends ComparisonsKt__ComparisonsKt {
    public static int maxOf(int i, int... iArr) {
        for (int i2 : iArr) {
            i = Math.max(i, i2);
        }
        return i;
    }

    public static float maxOf(float f, float... fArr) {
        for (float f2 : fArr) {
            f = Math.max(f, f2);
        }
        return f;
    }
}
