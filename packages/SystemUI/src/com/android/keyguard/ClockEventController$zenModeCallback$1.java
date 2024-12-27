package com.android.keyguard;

import android.app.AlarmManager;
import com.android.systemui.plugins.clocks.AlarmData;
import com.android.systemui.plugins.clocks.ClockController;
import com.android.systemui.plugins.clocks.ZenData;
import com.android.systemui.statusbar.policy.ZenModeController;
import com.android.systemui.statusbar.policy.ZenModeControllerImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ClockEventController$zenModeCallback$1 implements ZenModeController.Callback {
    public final /* synthetic */ ClockEventController this$0;

    public ClockEventController$zenModeCallback$1(ClockEventController clockEventController) {
        this.this$0 = clockEventController;
    }

    @Override // com.android.systemui.statusbar.policy.ZenModeController.Callback
    public final void onNextAlarmChanged() {
        final ClockEventController clockEventController = this.this$0;
        ZenModeControllerImpl zenModeControllerImpl = (ZenModeControllerImpl) clockEventController.zenModeController;
        AlarmManager.AlarmClockInfo nextAlarmClock = zenModeControllerImpl.mAlarmManager.getNextAlarmClock(zenModeControllerImpl.mUserId);
        long triggerTime = nextAlarmClock != null ? nextAlarmClock.getTriggerTime() : 0L;
        final AlarmData alarmData = new AlarmData(triggerTime > 0 ? Long.valueOf(triggerTime) : null, "status_bar_alarm");
        clockEventController.mainExecutor.execute(new Runnable() { // from class: com.android.keyguard.ClockEventController$zenModeCallback$1$onNextAlarmChanged$1$1
            @Override // java.lang.Runnable
            public final void run() {
                ClockController clockController = ClockEventController.this.clock;
                if (clockController != null) {
                    clockController.getEvents().onAlarmDataChanged(alarmData);
                }
            }
        });
        clockEventController.alarmData = alarmData;
    }

    @Override // com.android.systemui.statusbar.policy.ZenModeController.Callback
    public final void onZenChanged(int i) {
        ZenData.ZenMode fromInt = ZenData.ZenMode.Companion.fromInt(i);
        if (fromInt == null) {
            ClockEventController$zenModeCallback$1$$ExternalSyntheticOutline0.m(i, "Failed to get zen mode from int: ", "ClockEventController");
            return;
        }
        final ZenData zenData = new ZenData(fromInt, fromInt == ZenData.ZenMode.OFF ? "dnd_is_off" : "dnd_is_on");
        final ClockEventController clockEventController = this.this$0;
        clockEventController.mainExecutor.execute(new Runnable() { // from class: com.android.keyguard.ClockEventController$zenModeCallback$1$onZenChanged$1$1
            @Override // java.lang.Runnable
            public final void run() {
                ClockController clockController = ClockEventController.this.clock;
                if (clockController != null) {
                    clockController.getEvents().onZenDataChanged(zenData);
                }
            }
        });
        clockEventController.zenData = zenData;
    }
}
