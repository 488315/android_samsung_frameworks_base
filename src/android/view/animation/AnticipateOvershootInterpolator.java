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
public class AnticipateOvershootInterpolator extends BaseInterpolator implements NativeInterpolator {
    private final float mTension;

    private static float a(float f, float f2) {
        return f * f * (((1.0f + f2) * f) - f2);
    }

    private static float o(float f, float f2) {
        return f * f * (((1.0f + f2) * f) + f2);
    }

    public AnticipateOvershootInterpolator() {
        this.mTension = 3.0f;
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
        if (theme != null) {
            obtainAttributes = theme.obtainStyledAttributes(attributeSet, R.styleable.AnticipateOvershootInterpolator, 0, 0);
        } else {
            obtainAttributes = resources.obtainAttributes(attributeSet, R.styleable.AnticipateOvershootInterpolator);
        }
        this.mTension = obtainAttributes.getFloat(0, 2.0f) * obtainAttributes.getFloat(1, 1.5f);
        setChangingConfiguration(obtainAttributes.getChangingConfigurations());
        obtainAttributes.recycle();
    }

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        float o;
        if (f < 0.5f) {
            o = a(f * 2.0f, this.mTension);
        } else {
            o = o((f * 2.0f) - 2.0f, this.mTension) + 2.0f;
        }
        return o * 0.5f;
    }

    @Override // android.graphics.animation.NativeInterpolator
    public long createNativeInterpolator() {
        return NativeInterpolatorFactory.createAnticipateOvershootInterpolator(this.mTension);
    }
}
