package androidx.core.animation;

import android.content.Context;
import android.util.AttributeSet;

/* loaded from: classes.dex */
public class LinearInterpolator implements Interpolator {
    public LinearInterpolator() {
    }

    public LinearInterpolator(Context context, AttributeSet attributeSet) {
    }

    @Override // androidx.core.animation.Interpolator
    public final float getInterpolation(float f) {
        return f;
    }
}
