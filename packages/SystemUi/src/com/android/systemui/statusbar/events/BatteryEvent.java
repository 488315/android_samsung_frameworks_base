package com.android.systemui.statusbar.events;

import android.content.Context;
import com.android.systemui.battery.BatteryMeterView;
import com.android.systemui.statusbar.BatteryStatusChip;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class BatteryEvent implements StatusEvent {
    public final int batteryLevel;
    public boolean forceVisible;
    public final int priority = 50;
    public final boolean showAnimation = true;
    public final String contentDescription = "";
    public final Function1 viewCreator = new Function1() { // from class: com.android.systemui.statusbar.events.BatteryEvent$viewCreator$1
        {
            super(1);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            BatteryStatusChip batteryStatusChip = new BatteryStatusChip((Context) obj, null, 2, 0 == true ? 1 : 0);
            int i = BatteryEvent.this.batteryLevel;
            batteryStatusChip.batteryMeterView.setPercentShowMode(1);
            BatteryMeterView batteryMeterView = batteryStatusChip.batteryMeterView;
            batteryMeterView.mCharging = true;
            batteryMeterView.mLevel = i;
            batteryMeterView.updatePercentText();
            return batteryStatusChip;
        }
    };

    public BatteryEvent(int i) {
        this.batteryLevel = i;
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

    @Override // com.android.systemui.statusbar.events.StatusEvent
    public final boolean shouldUpdateFromEvent(StatusEvent statusEvent) {
        return false;
    }

    public final String toString() {
        return "BatteryEvent";
    }

    @Override // com.android.systemui.statusbar.events.StatusEvent
    public final void updateFromEvent(StatusEvent statusEvent) {
    }
}
