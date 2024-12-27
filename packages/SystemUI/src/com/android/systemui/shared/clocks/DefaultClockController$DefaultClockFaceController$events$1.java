package com.android.systemui.shared.clocks;

import android.graphics.Rect;
import com.android.systemui.plugins.clocks.ClockFaceEvents;
import com.android.systemui.shared.clocks.DefaultClockController;

public final class DefaultClockController$DefaultClockFaceController$events$1 implements ClockFaceEvents {
    public final /* synthetic */ DefaultClockController.DefaultClockFaceController this$0;
    public final /* synthetic */ DefaultClockController this$1;

    public DefaultClockController$DefaultClockFaceController$events$1(DefaultClockController.DefaultClockFaceController defaultClockFaceController, DefaultClockController defaultClockController) {
        this.this$0 = defaultClockFaceController;
        this.this$1 = defaultClockController;
    }

    @Override // com.android.systemui.plugins.clocks.ClockFaceEvents
    public final void onFontSettingChanged(float f) {
        DefaultClockController.DefaultClockFaceController defaultClockFaceController = this.this$0;
        defaultClockFaceController.view.setTextSize(0, f);
        defaultClockFaceController.recomputePadding(defaultClockFaceController.targetRegion);
    }

    @Override // com.android.systemui.plugins.clocks.ClockFaceEvents
    public final void onRegionDarknessChanged(boolean z) {
        DefaultClockController.DefaultClockFaceController defaultClockFaceController = this.this$0;
        defaultClockFaceController.isRegionDark = z;
        defaultClockFaceController.updateColor();
    }

    @Override // com.android.systemui.plugins.clocks.ClockFaceEvents
    public final void onSecondaryDisplayChanged(boolean z) {
        this.this$1.onSecondaryDisplay = z;
        this.this$0.recomputePadding(null);
    }

    @Override // com.android.systemui.plugins.clocks.ClockFaceEvents
    public final void onTargetRegionChanged(Rect rect) {
        DefaultClockController.DefaultClockFaceController defaultClockFaceController = this.this$0;
        defaultClockFaceController.targetRegion = rect;
        defaultClockFaceController.recomputePadding(rect);
    }

    @Override // com.android.systemui.plugins.clocks.ClockFaceEvents
    public final void onTimeTick() {
        this.this$0.view.refreshTime();
    }
}
