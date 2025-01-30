package androidx.core.animation;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class IntEvaluator implements TypeEvaluator {
    public static final IntEvaluator sInstance = new IntEvaluator();

    private IntEvaluator() {
    }

    @Override // androidx.core.animation.TypeEvaluator
    public final Object evaluate(float f, Object obj, Object obj2) {
        return Integer.valueOf((int) ((f * (((Integer) obj2).intValue() - r0)) + ((Integer) obj).intValue()));
    }
}
