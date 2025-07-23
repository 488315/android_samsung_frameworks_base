package android.view.animation;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.animation.HasNativeInterpolator;
import android.graphics.animation.NativeInterpolator;
import android.graphics.animation.NativeInterpolatorFactory;
import android.util.AttributeSet;
import com.android.internal.R;

@HasNativeInterpolator
/* loaded from: classes4.dex */
public class DecelerateInterpolator extends BaseInterpolator implements NativeInterpolator {
    private float mFactor;

    public DecelerateInterpolator() {
        this.mFactor = 1.0f;
    }

    public DecelerateInterpolator(float f) {
        this.mFactor = f;
    }

    public DecelerateInterpolator(Context context, AttributeSet attributeSet) {
        this(context.getResources(), context.getTheme(), attributeSet);
    }

    public DecelerateInterpolator(Resources resources, Resources.Theme theme, AttributeSet attributeSet) {
        TypedArray obtainAttributes;
        this.mFactor = 1.0f;
        if (theme != null) {
            obtainAttributes = theme.obtainStyledAttributes(attributeSet, R.styleable.DecelerateInterpolator, 0, 0);
        } else {
            obtainAttributes = resources.obtainAttributes(attributeSet, R.styleable.DecelerateInterpolator);
        }
        this.mFactor = obtainAttributes.getFloat(0, 1.0f);
        setChangingConfiguration(obtainAttributes.getChangingConfigurations());
        obtainAttributes.recycle();
    }

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        if (this.mFactor != 1.0f) {
            return (float) (1.0d - Math.pow(1.0f - f, r2 * 2.0f));
        }
        float f2 = 1.0f - f;
        return 1.0f - (f2 * f2);
    }

    @Override // android.graphics.animation.NativeInterpolator
    public long createNativeInterpolator() {
        return NativeInterpolatorFactory.createDecelerateInterpolator(this.mFactor);
    }
}
