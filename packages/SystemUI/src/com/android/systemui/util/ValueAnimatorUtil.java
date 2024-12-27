package com.android.systemui.util;

import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ValueAnimatorUtil {
    public static final float RELEASE_SCALE = 1.0f;
    public static final ValueAnimatorUtil INSTANCE = new ValueAnimatorUtil();
    private static final Interpolator RELEASE_INTERPOLATOR = new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f);
    public static final int $stable = 8;

    private ValueAnimatorUtil() {
    }

    public final ValueAnimator createScaleAnimator(final View view, float f, float f2, Interpolator interpolator, long j) {
        ValueAnimator ofFloat = ValueAnimator.ofFloat(f, f2);
        if (view == null) {
            Intrinsics.checkNotNull(ofFloat);
            return ofFloat;
        }
        ofFloat.setDuration(j);
        ofFloat.setInterpolator(interpolator);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.util.ValueAnimatorUtil$createScaleAnimator$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                Float f3 = (Float) valueAnimator.getAnimatedValue();
                View view2 = view;
                float floatValue = f3.floatValue();
                view2.setScaleX(floatValue);
                view2.setScaleY(floatValue);
            }
        });
        return ofFloat;
    }

    public final Interpolator getRELEASE_INTERPOLATOR() {
        return RELEASE_INTERPOLATOR;
    }

    public final void startDownScaleAnim(ValueAnimator valueAnimator, View view, float f) {
        if (valueAnimator.isRunning()) {
            valueAnimator.cancel();
        }
        if (view.getScaleX() == f) {
            return;
        }
        valueAnimator.setFloatValues(view.getScaleX(), f);
        valueAnimator.start();
    }

    public final void startReleaseScaleAnim(ValueAnimator valueAnimator, View view, float f, ValueAnimator valueAnimator2) {
        if (valueAnimator2 != null && valueAnimator2.isRunning() && valueAnimator2 != null) {
            valueAnimator2.cancel();
        }
        if (view.getScaleX() == f || valueAnimator.isRunning()) {
            return;
        }
        valueAnimator.setFloatValues(view.getScaleX(), f);
        valueAnimator.start();
    }
}
