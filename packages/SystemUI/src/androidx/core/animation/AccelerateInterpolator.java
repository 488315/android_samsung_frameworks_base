package androidx.core.animation;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class AccelerateInterpolator implements Interpolator {
    public final double mDoubleFactor;
    public final float mFactor;

    public AccelerateInterpolator() {
        this.mFactor = 1.0f;
        this.mDoubleFactor = 2.0d;
    }

    @Override // androidx.core.animation.Interpolator
    public final float getInterpolation(float f) {
        return this.mFactor == 1.0f ? f * f : (float) Math.pow(f, this.mDoubleFactor);
    }

    public AccelerateInterpolator(float f) {
        this.mFactor = f;
        this.mDoubleFactor = f * 2.0f;
    }

    public AccelerateInterpolator(Context context, AttributeSet attributeSet) {
        this(context.getResources(), context.getTheme(), attributeSet);
    }

    public AccelerateInterpolator(Resources resources, Resources.Theme theme, AttributeSet attributeSet) {
        TypedArray obtainAttributes;
        int[] iArr = AndroidResources.STYLEABLE_ACCELERATE_INTERPOLATOR;
        if (theme != null) {
            obtainAttributes = theme.obtainStyledAttributes(attributeSet, iArr, 0, 0);
        } else {
            obtainAttributes = resources.obtainAttributes(attributeSet, iArr);
        }
        this.mFactor = obtainAttributes.getFloat(0, 1.0f);
        this.mDoubleFactor = r4 * 2.0f;
        obtainAttributes.recycle();
    }
}
