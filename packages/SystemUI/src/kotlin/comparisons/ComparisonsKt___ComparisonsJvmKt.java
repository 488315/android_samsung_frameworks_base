package kotlin.comparisons;

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
