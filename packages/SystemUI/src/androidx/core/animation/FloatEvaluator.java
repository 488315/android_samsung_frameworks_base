package androidx.core.animation;

/* loaded from: classes.dex */
public final class FloatEvaluator implements TypeEvaluator {
    public static final FloatEvaluator sInstance = new FloatEvaluator();

    private FloatEvaluator() {
    }

    @Override // androidx.core.animation.TypeEvaluator
    public final Object evaluate(float f, Object obj, Object obj2) {
        float fFloatValue = ((Float) obj).floatValue();
        return Float.valueOf(((((Float) obj2).floatValue() - fFloatValue) * f) + fFloatValue);
    }
}
