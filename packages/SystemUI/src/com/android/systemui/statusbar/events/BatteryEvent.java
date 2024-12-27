package com.android.systemui.statusbar.events;

import android.content.Context;
import android.icu.text.NumberFormat;
import android.widget.FrameLayout;
import com.android.systemui.R;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

public final class BatteryEvent implements StatusEvent {
    public final int batteryLevel;
    public boolean forceVisible;
    public final Function1 viewCreator;
    public final int priority = 50;
    public final boolean showAnimation = true;
    public final String contentDescription = "";

    public BatteryEvent(int i, final int i2) {
        this.batteryLevel = i;
        this.viewCreator = new Function1() { // from class: com.android.systemui.statusbar.events.BatteryEvent$viewCreator$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                SamsungBatteryStatusChip samsungBatteryStatusChip = new SamsungBatteryStatusChip((Context) obj, null, 2, 0 == true ? 1 : 0);
                BatteryEvent batteryEvent = BatteryEvent.this;
                int i3 = i2;
                int i4 = batteryEvent.batteryLevel;
                String valueOf = Intrinsics.areEqual(samsungBatteryStatusChip.locale.toString(), "my_MM") ? String.valueOf(i4) : NumberFormat.getInstance(samsungBatteryStatusChip.getContext().getResources().getConfiguration().getLocales().get(0)).format(Integer.valueOf(i4));
                BatteryStatusChipClearTextView batteryStatusChipClearTextView = samsungBatteryStatusChip.batteryLevelText;
                Intrinsics.checkNotNull(valueOf);
                batteryStatusChipClearTextView.text = valueOf;
                samsungBatteryStatusChip.batteryLevelProgress.setLayoutParams(new FrameLayout.LayoutParams((samsungBatteryStatusChip.getResources().getDimensionPixelSize(R.dimen.status_bar_battery_chip_width) * i4) / 100, -1));
                samsungBatteryStatusChip.batteryLevelProgressBg.setLayoutParams(new FrameLayout.LayoutParams((samsungBatteryStatusChip.getResources().getDimensionPixelSize(R.dimen.status_bar_battery_chip_width) * i4) / 100, -1));
                samsungBatteryStatusChip.batteryLevelProgress.setAnimation(i3 != 3 ? i3 != 4 ? R.raw.indicator_color_gradient_normal : R.raw.indicator_color_gradient_superfast : R.raw.indicator_color_gradient_fast);
                return samsungBatteryStatusChip;
            }
        };
    }

    @Override // com.android.systemui.statusbar.events.StatusEvent
    public final String getContentDescription() {
        return this.contentDescription;
    }

    @Override // com.android.systemui.statusbar.events.StatusEvent
    public final boolean getForceVisible() {
        return this.forceVisible;
    }

    @Override // com.android.systemui.statusbar.events.StatusEvent
    public final int getPriority() {
        return this.priority;
    }

    @Override // com.android.systemui.statusbar.events.StatusEvent
    public final boolean getShowAnimation() {
        return this.showAnimation;
    }

    @Override // com.android.systemui.statusbar.events.StatusEvent
    public final Function1 getViewCreator() {
        return this.viewCreator;
    }

    @Override // com.android.systemui.statusbar.events.StatusEvent
    public final void setForceVisible() {
        this.forceVisible = false;
    }

    public final String toString() {
        return "BatteryEvent";
    }
}
