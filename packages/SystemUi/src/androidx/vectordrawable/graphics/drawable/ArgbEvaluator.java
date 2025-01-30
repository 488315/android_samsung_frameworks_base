package androidx.vectordrawable.graphics.drawable;

import android.animation.TypeEvaluator;
import androidx.constraintlayout.core.widgets.analyzer.DependencyGraph$$ExternalSyntheticOutline0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ArgbEvaluator implements TypeEvaluator {
    public static final ArgbEvaluator sInstance = new ArgbEvaluator();

    @Override // android.animation.TypeEvaluator
    public final Object evaluate(float f, Object obj, Object obj2) {
        int intValue = ((Integer) obj).intValue();
        float f2 = ((intValue >> 24) & 255) / 255.0f;
        int intValue2 = ((Integer) obj2).intValue();
        float f3 = ((intValue2 >> 24) & 255) / 255.0f;
        float pow = (float) Math.pow(((intValue >> 16) & 255) / 255.0f, 2.2d);
        float pow2 = (float) Math.pow(((intValue >> 8) & 255) / 255.0f, 2.2d);
        float pow3 = (float) Math.pow((intValue & 255) / 255.0f, 2.2d);
        float pow4 = (float) Math.pow(((intValue2 >> 16) & 255) / 255.0f, 2.2d);
        float pow5 = (float) Math.pow(((intValue2 >> 8) & 255) / 255.0f, 2.2d);
        float pow6 = (float) Math.pow((intValue2 & 255) / 255.0f, 2.2d);
        float m20m = DependencyGraph$$ExternalSyntheticOutline0.m20m(f3, f2, f, f2);
        float m20m2 = DependencyGraph$$ExternalSyntheticOutline0.m20m(pow4, pow, f, pow);
        float m20m3 = DependencyGraph$$ExternalSyntheticOutline0.m20m(pow5, pow2, f, pow2);
        float m20m4 = DependencyGraph$$ExternalSyntheticOutline0.m20m(pow6, pow3, f, pow3);
        float pow7 = ((float) Math.pow(m20m2, 0.45454545454545453d)) * 255.0f;
        float pow8 = ((float) Math.pow(m20m3, 0.45454545454545453d)) * 255.0f;
        return Integer.valueOf(Math.round(((float) Math.pow(m20m4, 0.45454545454545453d)) * 255.0f) | (Math.round(pow7) << 16) | (Math.round(m20m * 255.0f) << 24) | (Math.round(pow8) << 8));
    }
}
