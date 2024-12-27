package com.android.systemui.animation.back;

import android.util.DisplayMetrics;
import android.view.animation.Interpolator;
import android.window.BackEvent;
import com.android.systemui.util.DimensionKt;
import kotlin.jvm.functions.Function0;

public final class BackAnimationSpecKt$createFloatingSurfaceAnimationSpec$1 implements BackAnimationSpec {
    public final /* synthetic */ Function0 $displayMetricsProvider;
    public final /* synthetic */ float $maxMarginXdp;
    public final /* synthetic */ float $maxMarginYdp;
    public final /* synthetic */ float $minScale;
    public final /* synthetic */ Interpolator $scaleEasing;
    public final /* synthetic */ Interpolator $translateXEasing;
    public final /* synthetic */ Interpolator $translateYEasing;

    public BackAnimationSpecKt$createFloatingSurfaceAnimationSpec$1(Function0 function0, float f, float f2, float f3, Interpolator interpolator, Interpolator interpolator2, Interpolator interpolator3) {
        this.$displayMetricsProvider = function0;
        this.$maxMarginXdp = f;
        this.$maxMarginYdp = f2;
        this.$minScale = f3;
        this.$translateXEasing = interpolator;
        this.$translateYEasing = interpolator2;
        this.$scaleEasing = interpolator3;
    }

    @Override // com.android.systemui.animation.back.BackAnimationSpec
    public final void getBackTransformation(BackEvent backEvent, float f, BackTransformation backTransformation) {
        DisplayMetrics displayMetrics = (DisplayMetrics) this.$displayMetricsProvider.invoke();
        int i = displayMetrics.widthPixels;
        int i2 = displayMetrics.heightPixels;
        float dpToPx = DimensionKt.dpToPx(Float.valueOf(this.$maxMarginXdp), displayMetrics);
        float dpToPx2 = DimensionKt.dpToPx(Float.valueOf(this.$maxMarginYdp), displayMetrics);
        float f2 = i;
        float f3 = this.$minScale;
        float f4 = 2;
        float f5 = ((f2 - (f2 * f3)) / f4) - dpToPx;
        float f6 = i2;
        float f7 = ((f6 - (f6 * f3)) / f4) - dpToPx2;
        float f8 = 1.0f - f3;
        int i3 = backEvent.getSwipeEdge() == 0 ? 1 : -1;
        float progress = backEvent.getProgress();
        float interpolation = this.$translateXEasing.getInterpolation(progress);
        float interpolation2 = this.$translateYEasing.getInterpolation(f);
        float interpolation3 = this.$scaleEasing.getInterpolation(progress);
        backTransformation.translateX = interpolation * i3 * f5;
        backTransformation.translateY = interpolation2 * f7;
        backTransformation.scale = 1.0f - (interpolation3 * f8);
    }
}
