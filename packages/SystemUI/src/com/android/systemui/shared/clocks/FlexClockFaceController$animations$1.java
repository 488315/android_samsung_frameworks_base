package com.android.systemui.shared.clocks;

import android.util.MathUtils;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import com.android.systemui.animation.AxisDefinition;
import com.android.systemui.animation.GSFAxes;
import com.android.systemui.plugins.clocks.ClockAnimations;
import com.android.systemui.plugins.clocks.ClockAxisStyle;
import com.android.systemui.plugins.clocks.ClockFontAxis;
import com.android.systemui.plugins.clocks.ClockSettings;
import com.android.systemui.shared.clocks.FlexClockController;
import com.android.systemui.shared.clocks.view.FlexClockView;
import com.android.systemui.shared.clocks.view.SimpleDigitalClockTextView;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class FlexClockFaceController$animations$1 implements ClockAnimations {
    public final /* synthetic */ ClockContext $clockCtx;
    public final /* synthetic */ FlexClockFaceController this$0;

    public FlexClockFaceController$animations$1(FlexClockFaceController flexClockFaceController, ClockContext clockContext) {
        this.this$0 = flexClockFaceController;
        this.$clockCtx = clockContext;
    }

    @Override // com.android.systemui.plugins.clocks.ClockAnimations
    public final void charge() {
        this.this$0.layerController.getAnimations().charge();
    }

    @Override // com.android.systemui.plugins.clocks.ClockAnimations
    public final void doze(float f) {
        this.this$0.layerController.getAnimations().doze(f);
    }

    @Override // com.android.systemui.plugins.clocks.ClockAnimations
    public final void enter() {
        this.this$0.layerController.getAnimations().enter();
    }

    @Override // com.android.systemui.plugins.clocks.ClockAnimations
    public final void fold(float f) {
        this.this$0.layerController.getAnimations().fold(f);
    }

    @Override // com.android.systemui.plugins.clocks.ClockAnimations
    public final void onFidgetTap(float f, float f2) {
        this.this$0.layerController.getAnimations().onFidgetTap(f, f2);
    }

    @Override // com.android.systemui.plugins.clocks.ClockAnimations
    public final void onFontAxesChanged(ClockAxisStyle clockAxisStyle) {
        ClockFontAxis.Companion companion = ClockFontAxis.Companion;
        FlexClockController.Companion companion2 = FlexClockController.Companion;
        ClockSettings clockSettings = this.$clockCtx.settings;
        companion2.getClass();
        ClockAxisStyle clockAxisStyle2 = new ClockAxisStyle(companion.merge(FlexClockController.Companion.getDefaultAxes(clockSettings), clockAxisStyle));
        FlexClockFaceController flexClockFaceController = this.this$0;
        if (!flexClockFaceController.isLargeClock) {
            FontUtils fontUtils = FontUtils.INSTANCE;
            GSFAxes.INSTANCE.getClass();
            AxisDefinition axisDefinition = GSFAxes.WIDTH;
            fontUtils.getClass();
            Float f = clockAxisStyle2.get(axisDefinition.tag);
            float floatValue = f != null ? f.floatValue() : axisDefinition.defaultValue;
            FlexClockFaceController.Companion.getClass();
            float f2 = FlexClockFaceController.SMALL_CLOCK_MAX_WDTH;
            if (floatValue > f2) {
                clockAxisStyle2.set(axisDefinition.tag, f2);
            }
        }
        flexClockFaceController.layerController.getAnimations().onFontAxesChanged(clockAxisStyle2);
    }

    @Override // com.android.systemui.plugins.clocks.ClockAnimations
    public final void onPickerCarouselSwiping(float f) {
        FlexClockFaceController flexClockFaceController = this.this$0;
        boolean z = flexClockFaceController.isLargeClock;
        SimpleClockLayerController simpleClockLayerController = flexClockFaceController.layerController;
        if (z) {
            simpleClockLayerController.getView().setTranslationY((flexClockFaceController.keyguardLargeClockTopMargin / 2.0f) * f);
        }
        simpleClockLayerController.getAnimations().onPickerCarouselSwiping(f);
        simpleClockLayerController.getView().invalidate();
    }

    @Override // com.android.systemui.plugins.clocks.ClockAnimations
    public final void onPositionUpdated(float f, float f2) {
        this.this$0.layerController.getAnimations().onPositionUpdated(f, f2);
    }

    @Override // com.android.systemui.plugins.clocks.ClockAnimations
    public final void onPositionUpdated(int i, int i2, float f) {
        List list;
        FlexClockFaceController flexClockFaceController = this.this$0;
        flexClockFaceController.layerController.getAnimations().onPositionUpdated(i, i2, f);
        if (flexClockFaceController.isLargeClock) {
            View view = flexClockFaceController.layerController.getView();
            FlexClockView flexClockView = view instanceof FlexClockView ? (FlexClockView) view : null;
            if (flexClockView != null) {
                int i3 = 0;
                boolean z = !flexClockView.isLayoutRtl() ? i2 <= 0 : i2 >= 0;
                int left = flexClockView.getLeft() - i;
                for (SimpleDigitalClockTextView simpleDigitalClockTextView : flexClockView.getChildViews()) {
                    int i4 = i3 + 1;
                    if (z) {
                        list = flexClockView.isLayoutRtl() ? FlexClockView.MOVE_LEFT_DELAYS : FlexClockView.MOVE_RIGHT_DELAYS;
                    } else {
                        list = flexClockView.isLayoutRtl() ? FlexClockView.MOVE_RIGHT_DELAYS : FlexClockView.MOVE_LEFT_DELAYS;
                    }
                    float floatValue = ((Number) list.get(i3)).floatValue() * 0.033f;
                    Interpolator interpolator = FlexClockView.MOVE_INTERPOLATOR;
                    FlexClockView.Companion companion = FlexClockView.Companion;
                    int size = flexClockView.getChildViews().size();
                    companion.getClass();
                    float f2 = left;
                    float interpolation = ((PathInterpolator) interpolator).getInterpolation(MathUtils.constrainedMap(0.0f, 1.0f, floatValue, (1.0f - ((size - 1) * 0.033f)) + floatValue, f)) * f2;
                    float f3 = interpolation - f2;
                    if (z && interpolation < 0.0f) {
                        f3 *= -1;
                    }
                    flexClockView.digitOffsets.put(Integer.valueOf(simpleDigitalClockTextView.getId()), Float.valueOf(f3));
                    flexClockView.invalidate();
                    i3 = i4;
                }
            }
        }
    }
}
