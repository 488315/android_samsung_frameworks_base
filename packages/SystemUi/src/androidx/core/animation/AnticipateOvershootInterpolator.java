package androidx.core.animation;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class AnticipateOvershootInterpolator implements Interpolator {
    public final float mTension;

    public AnticipateOvershootInterpolator() {
        this.mTension = 3.0f;
    }

    @Override // androidx.core.animation.Interpolator
    public final float getInterpolation(float f) {
        float f2 = this.mTension;
        if (f < 0.5f) {
            float f3 = f * 2.0f;
            return (((1.0f + f2) * f3) - f2) * f3 * f3 * 0.5f;
        }
        float f4 = (f * 2.0f) - 2.0f;
        return (((((1.0f + f2) * f4) + f2) * f4 * f4) + 2.0f) * 0.5f;
    }

    public AnticipateOvershootInterpolator(float f) {
        this.mTension = f * 1.5f;
    }

    public AnticipateOvershootInterpolator(float f, float f2) {
        this.mTension = f * f2;
    }

    public AnticipateOvershootInterpolator(Context context, AttributeSet attributeSet) {
        this(context.getResources(), context.getTheme(), attributeSet);
    }

    public AnticipateOvershootInterpolator(Resources resources, Resources.Theme theme, AttributeSet attributeSet) {
        TypedArray obtainAttributes;
        int[] iArr = AndroidResources.STYLEABLE_ANTICIPATEOVERSHOOT_INTERPOLATOR;
        if (theme != null) {
            obtainAttributes = theme.obtainStyledAttributes(attributeSet, iArr, 0, 0);
        } else {
            obtainAttributes = resources.obtainAttributes(attributeSet, iArr);
        }
        this.mTension = obtainAttributes.getFloat(1, 1.5f) * obtainAttributes.getFloat(0, 2.0f);
        obtainAttributes.recycle();
    }
}
