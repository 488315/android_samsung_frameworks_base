package androidx.core.animation;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class IntEvaluator implements TypeEvaluator {
    public static final IntEvaluator sInstance = new IntEvaluator();

    private IntEvaluator() {
    }

    @Override // androidx.core.animation.TypeEvaluator
    public final Object evaluate(float f, Object obj, Object obj2) {
        return Integer.valueOf((int) ((f * (((Integer) obj2).intValue() - r0)) + ((Integer) obj).intValue()));
    }
}
