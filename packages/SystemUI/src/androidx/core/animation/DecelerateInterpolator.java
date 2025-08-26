package androidx.core.animation;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;

/* loaded from: classes.dex */
public class DecelerateInterpolator implements Interpolator {
    public final float mFactor;

    public DecelerateInterpolator() {
        this.mFactor = 1.0f;
    }

    @Override // androidx.core.animation.Interpolator
    public final float getInterpolation(float f) {
        if (this.mFactor != 1.0f) {
            return (float) (1.0d - Math.pow(1.0f - f, r2 * 2.0f));
        }
        float f2 = 1.0f - f;
        return 1.0f - (f2 * f2);
    }

    public DecelerateInterpolator(float f) {
        this.mFactor = f;
    }

    public DecelerateInterpolator(Context context, AttributeSet attributeSet) {
        this(context.getResources(), context.getTheme(), attributeSet);
    }

    public DecelerateInterpolator(Resources resources, Resources.Theme theme, AttributeSet attributeSet) {
        TypedArray typedArrayObtainAttributes;
        this.mFactor = 1.0f;
        int[] iArr = AndroidResources.STYLEABLE_DECELERATE_INTERPOLATOR;
        if (theme != null) {
            typedArrayObtainAttributes = theme.obtainStyledAttributes(attributeSet, iArr, 0, 0);
        } else {
            typedArrayObtainAttributes = resources.obtainAttributes(attributeSet, iArr);
        }
        this.mFactor = typedArrayObtainAttributes.getFloat(0, 1.0f);
        typedArrayObtainAttributes.recycle();
    }
}
