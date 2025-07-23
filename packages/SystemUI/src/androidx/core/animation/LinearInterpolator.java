package androidx.core.animation;

import android.content.Context;
import android.util.AttributeSet;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
