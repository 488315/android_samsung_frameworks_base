package androidx.core.animation;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class FloatEvaluator implements TypeEvaluator {
    public static final FloatEvaluator sInstance = new FloatEvaluator();

    private FloatEvaluator() {
    }

    @Override // androidx.core.animation.TypeEvaluator
    public final Object evaluate(float f, Object obj, Object obj2) {
        float floatValue = ((Float) obj).floatValue();
        return Float.valueOf(((((Float) obj2).floatValue() - floatValue) * f) + floatValue);
    }
}
