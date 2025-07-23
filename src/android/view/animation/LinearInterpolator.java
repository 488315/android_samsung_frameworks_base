package android.view.animation;

import android.content.Context;
import android.graphics.animation.HasNativeInterpolator;
import android.graphics.animation.NativeInterpolator;
import android.graphics.animation.NativeInterpolatorFactory;
import android.util.AttributeSet;

@HasNativeInterpolator
/* loaded from: classes4.dex */
public class LinearInterpolator extends BaseInterpolator implements NativeInterpolator {
    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        return f;
    }

    public LinearInterpolator() {
    }

    public LinearInterpolator(Context context, AttributeSet attributeSet) {
    }

    @Override // android.graphics.animation.NativeInterpolator
    public long createNativeInterpolator() {
        return NativeInterpolatorFactory.createLinearInterpolator();
    }
}
