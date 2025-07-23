package com.android.systemui.statusbar.events;

import android.content.Context;
import android.widget.FrameLayout;
import com.airbnb.lottie.LottieAnimationView;
import com.android.systemui.R;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class BatteryEvent implements StatusEvent {
    public final int batteryLevel;
    public final BatteryEvent$$ExternalSyntheticLambda0 viewCreator;

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.statusbar.events.BatteryEvent$$ExternalSyntheticLambda0] */
    public BatteryEvent(int i, final int i2) {
        this.batteryLevel = i;
        this.viewCreator = new Function1() { // from class: com.android.systemui.statusbar.events.BatteryEvent$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                SamsungBatteryStatusChip samsungBatteryStatusChip = new SamsungBatteryStatusChip((Context) obj, null, 2, 0 == true ? 1 : 0);
                int i3 = BatteryEvent.this.batteryLevel;
                samsungBatteryStatusChip.batteryLevelText.level = i3;
                samsungBatteryStatusChip.batteryLevelProgress.setLayoutParams(new FrameLayout.LayoutParams((samsungBatteryStatusChip.getResources().getDimensionPixelSize(R.dimen.status_bar_battery_chip_width) * i3) / 100, -1));
                samsungBatteryStatusChip.batteryLevelProgressBg.setLayoutParams(new FrameLayout.LayoutParams((samsungBatteryStatusChip.getResources().getDimensionPixelSize(R.dimen.status_bar_battery_chip_width) * i3) / 100, -1));
                LottieAnimationView lottieAnimationView = samsungBatteryStatusChip.batteryLevelProgress;
                int i4 = i2;
                lottieAnimationView.setAnimation(i4 != 3 ? i4 != 4 ? R.raw.indicator_color_gradient_normal : R.raw.indicator_color_gradient_superfast : R.raw.indicator_color_gradient_fast);
                return samsungBatteryStatusChip;
            }
        };
    }

    @Override // com.android.systemui.statusbar.events.StatusEvent
    public final String getContentDescription() {
        return "";
    }

    @Override // com.android.systemui.statusbar.events.StatusEvent
    public final boolean getForceVisible() {
        return false;
    }

    @Override // com.android.systemui.statusbar.events.StatusEvent
    public final int getPriority() {
        return 50;
    }

    @Override // com.android.systemui.statusbar.events.StatusEvent
    public final boolean getShowAnimation() {
        return true;
    }

    @Override // com.android.systemui.statusbar.events.StatusEvent
    public final Function1 getViewCreator() {
        return this.viewCreator;
    }

    public final String toString() {
        return "BatteryEvent";
    }

    @Override // com.android.systemui.statusbar.events.StatusEvent
    public final void setForceVisible() {
    }
}
