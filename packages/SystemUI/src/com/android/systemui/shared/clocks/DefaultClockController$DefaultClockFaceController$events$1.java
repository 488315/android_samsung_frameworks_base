package com.android.systemui.shared.clocks;

import android.graphics.Rect;
import android.os.Debug;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.Logger;
import com.android.systemui.plugins.clocks.ClockFaceEvents;
import com.android.systemui.plugins.clocks.ThemeConfig;
import com.android.systemui.shared.clocks.DefaultClockController;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DefaultClockController$DefaultClockFaceController$events$1 implements ClockFaceEvents {
    public final /* synthetic */ DefaultClockController.DefaultClockFaceController this$0;
    public final /* synthetic */ DefaultClockController this$1;

    public DefaultClockController$DefaultClockFaceController$events$1(DefaultClockController.DefaultClockFaceController defaultClockFaceController, DefaultClockController defaultClockController) {
        this.this$0 = defaultClockFaceController;
        this.this$1 = defaultClockController;
    }

    @Override // com.android.systemui.plugins.clocks.ClockFaceEvents
    public final void onFontSettingChanged(float f) {
        this.this$0.view.setTextSize(0, f);
    }

    @Override // com.android.systemui.plugins.clocks.ClockFaceEvents
    public final void onTargetRegionChanged(Rect rect) {
        this.this$0.targetRegion = rect;
    }

    @Override // com.android.systemui.plugins.clocks.ClockFaceEvents
    public final void onThemeChanged(ThemeConfig themeConfig) {
        DefaultClockController.DefaultClockFaceController defaultClockFaceController = this.this$0;
        defaultClockFaceController.theme = themeConfig;
        int defaultColor = themeConfig.getDefaultColor(this.this$1.ctx);
        if (defaultClockFaceController.currentColor == defaultColor) {
            return;
        }
        defaultClockFaceController.currentColor = defaultColor;
        AnimatableClockView animatableClockView = defaultClockFaceController.view;
        animatableClockView.dozingColor = -1;
        animatableClockView.lockScreenColor = defaultColor;
        if (defaultClockFaceController.animations.dozeState.isActive) {
            return;
        }
        Logger.d$default(animatableClockView.getLogger(), "animateColorChange", null, 2, null);
        animatableClockView.setTextStyle(animatableClockView.getLockScreenWeight(), null, false, null, 0L, 0L, null);
        animatableClockView.setTextStyle(animatableClockView.getLockScreenWeight(), Integer.valueOf(animatableClockView.lockScreenColor), true, null, 400L, 0L, null);
    }

    @Override // com.android.systemui.plugins.clocks.ClockFaceEvents
    public final void onTimeTick() {
        this.this$0.view.refreshTime();
        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("onTimeTick. callers : ", Debug.getCallers(3, ""), "ClockFaceEvents");
    }

    @Override // com.android.systemui.plugins.clocks.ClockFaceEvents
    public final void onSecondaryDisplayChanged(boolean z) {
    }
}
