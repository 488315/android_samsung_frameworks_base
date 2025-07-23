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
public class OvershootInterpolator extends BaseInterpolator implements NativeInterpolator {
    private final float mTension;

    public OvershootInterpolator() {
        this.mTension = 2.0f;
    }

    public OvershootInterpolator(float f) {
        this.mTension = f;
    }

    public OvershootInterpolator(Context context, AttributeSet attributeSet) {
        this(context.getResources(), context.getTheme(), attributeSet);
    }

    public OvershootInterpolator(Resources resources, Resources.Theme theme, AttributeSet attributeSet) {
        TypedArray obtainAttributes;
        if (theme != null) {
            obtainAttributes = theme.obtainStyledAttributes(attributeSet, R.styleable.OvershootInterpolator, 0, 0);
        } else {
            obtainAttributes = resources.obtainAttributes(attributeSet, R.styleable.OvershootInterpolator);
        }
        this.mTension = obtainAttributes.getFloat(0, 2.0f);
        setChangingConfiguration(obtainAttributes.getChangingConfigurations());
        obtainAttributes.recycle();
    }

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        float f2 = f - 1.0f;
        float f3 = this.mTension;
        return (f2 * f2 * (((f3 + 1.0f) * f2) + f3)) + 1.0f;
    }

    @Override // android.graphics.animation.NativeInterpolator
    public long createNativeInterpolator() {
        return NativeInterpolatorFactory.createOvershootInterpolator(this.mTension);
    }
}
