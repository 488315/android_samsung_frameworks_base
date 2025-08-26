package androidx.core.animation;

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
