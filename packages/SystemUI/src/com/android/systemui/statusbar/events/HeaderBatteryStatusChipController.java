package com.android.systemui.statusbar.events;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.core.animation.Animator;
import com.android.systemui.R;
import com.android.systemui.statusbar.phone.IndicatorScaleGardener;

public final class HeaderBatteryStatusChipController implements SystemStatusAnimationCallback {
    public final FrameLayout batteryChipContainer;
    public final Context context;
    public BackgroundAnimatableView currentAnimatedView;

    public HeaderBatteryStatusChipController(Context context, MotionLayout motionLayout, IndicatorScaleGardener indicatorScaleGardener) {
        this.context = context;
        this.batteryChipContainer = (FrameLayout) motionLayout.requireViewById(R.id.battery_chip_container);
    }

    @Override // com.android.systemui.statusbar.events.SystemStatusAnimationCallback
    public final Animator onSystemEventAnimationBegin(boolean z, boolean z2) {
        Object obj;
        if (!z2 || (obj = this.currentAnimatedView) == null) {
            return null;
        }
        ((View) obj).setVisibility(0);
        return null;
    }

    @Override // com.android.systemui.statusbar.events.SystemStatusAnimationCallback
    public final Animator onSystemEventAnimationFinish(boolean z, boolean z2, boolean z3) {
        Object obj;
        if (z3 && (obj = this.currentAnimatedView) != null) {
            ((View) obj).setVisibility(8);
        }
        Object obj2 = this.currentAnimatedView;
        if (obj2 == null) {
            return null;
        }
        this.batteryChipContainer.removeView((View) obj2);
        return null;
    }
}
