package android.view.animation;

import android.content.Context;
import android.graphics.animation.HasNativeInterpolator;
import android.graphics.animation.NativeInterpolator;
import android.graphics.animation.NativeInterpolatorFactory;
import android.util.AttributeSet;

@HasNativeInterpolator
/* loaded from: classes4.dex */
public class BounceInterpolator extends BaseInterpolator implements NativeInterpolator {
    private static float bounce(float f) {
        return f * f * 8.0f;
    }

    public BounceInterpolator() {
    }

    public BounceInterpolator(Context context, AttributeSet attributeSet) {
    }

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        float bounce;
        float f2;
        float f3 = f * 1.1226f;
        if (f3 < 0.3535f) {
            return bounce(f3);
        }
        if (f3 < 0.7408f) {
            bounce = bounce(f3 - 0.54719f);
            f2 = 0.7f;
        } else if (f3 < 0.9644f) {
            bounce = bounce(f3 - 0.8526f);
            f2 = 0.9f;
        } else {
            bounce = bounce(f3 - 1.0435f);
            f2 = 0.95f;
        }
        return bounce + f2;
    }

    @Override // android.graphics.animation.NativeInterpolator
    public long createNativeInterpolator() {
        return NativeInterpolatorFactory.createBounceInterpolator();
    }
}
