package androidx.core.animation;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class OvershootInterpolator implements Interpolator {
    public final float mTension;

    public OvershootInterpolator() {
        this.mTension = 2.0f;
    }

    @Override // androidx.core.animation.Interpolator
    public final float getInterpolation(float f) {
        float f2 = f - 1.0f;
        float f3 = this.mTension;
        return ((((f3 + 1.0f) * f2) + f3) * f2 * f2) + 1.0f;
    }

    public OvershootInterpolator(float f) {
        this.mTension = f;
    }

    public OvershootInterpolator(Context context, AttributeSet attributeSet) {
        this(context.getResources(), context.getTheme(), attributeSet);
    }

    public OvershootInterpolator(Resources resources, Resources.Theme theme, AttributeSet attributeSet) {
        TypedArray obtainAttributes;
        int[] iArr = AndroidResources.STYLEABLE_OVERSHOOT_INTERPOLATOR;
        if (theme != null) {
            obtainAttributes = theme.obtainStyledAttributes(attributeSet, iArr, 0, 0);
        } else {
            obtainAttributes = resources.obtainAttributes(attributeSet, iArr);
        }
        this.mTension = obtainAttributes.getFloat(0, 2.0f);
        obtainAttributes.recycle();
    }
}
