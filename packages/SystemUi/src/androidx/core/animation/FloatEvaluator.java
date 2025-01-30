package androidx.core.animation;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
