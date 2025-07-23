package androidx.core.animation;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class CycleInterpolator implements Interpolator {
    public final float mCycles;

    public CycleInterpolator(float f) {
        this.mCycles = f;
    }

    @Override // androidx.core.animation.Interpolator
    public final float getInterpolation(float f) {
        return (float) Math.sin(this.mCycles * 2.0f * 3.141592653589793d * f);
    }

    public CycleInterpolator(Context context, AttributeSet attributeSet) {
        this(context.getResources(), context.getTheme(), attributeSet);
    }

    public CycleInterpolator(Resources resources, Resources.Theme theme, AttributeSet attributeSet) {
        TypedArray obtainAttributes;
        int[] iArr = AndroidResources.STYLEABLE_CYCLE_INTERPOLATOR;
        if (theme != null) {
            obtainAttributes = theme.obtainStyledAttributes(attributeSet, iArr, 0, 0);
        } else {
            obtainAttributes = resources.obtainAttributes(attributeSet, iArr);
        }
        this.mCycles = obtainAttributes.getFloat(0, 1.0f);
        obtainAttributes.recycle();
    }
}
