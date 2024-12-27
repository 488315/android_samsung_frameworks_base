package com.android.systemui.util;

import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class RecoilEffectAnimHelper {
    private static final long DOWN_DURATION = 100;
    private static final float DOWN_SCALE = 0.96f;
    private static final long RELEASE_DURATION = 350;
    private final View container;
    private ValueAnimator downAnimator;
    private ValueAnimator releaseAnimator;
    public static final Companion Companion = new Companion(null);
    public static final int $stable = 8;
    private static final Interpolator LINEAR_INTERPOLATOR = new PathInterpolator(0.0f, 0.0f, 1.0f, 1.0f);

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public RecoilEffectAnimHelper(View view) {
        this.container = view;
        ValueAnimatorUtil valueAnimatorUtil = ValueAnimatorUtil.INSTANCE;
        this.downAnimator = valueAnimatorUtil.createScaleAnimator(view, 1.0f, DOWN_SCALE, LINEAR_INTERPOLATOR, DOWN_DURATION);
        this.releaseAnimator = valueAnimatorUtil.createScaleAnimator(view, DOWN_SCALE, 1.0f, valueAnimatorUtil.getRELEASE_INTERPOLATOR(), RELEASE_DURATION);
    }

    public final void startTouchDownAnim() {
        ValueAnimator valueAnimator;
        View view = this.container;
        if (view == null || (valueAnimator = this.downAnimator) == null) {
            return;
        }
        ValueAnimatorUtil.INSTANCE.startDownScaleAnim(valueAnimator, view, DOWN_SCALE);
    }

    public final void startTouchReleaseAnim() {
        ValueAnimator valueAnimator;
        View view = this.container;
        if (view == null || (valueAnimator = this.releaseAnimator) == null) {
            return;
        }
        ValueAnimatorUtil.INSTANCE.startReleaseScaleAnim(valueAnimator, view, 1.0f, this.downAnimator);
    }
}
