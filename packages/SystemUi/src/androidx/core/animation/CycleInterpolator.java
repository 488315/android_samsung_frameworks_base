package androidx.core.animation;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
