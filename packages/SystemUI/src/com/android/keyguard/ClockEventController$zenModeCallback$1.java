package com.android.keyguard;

import android.app.AlarmManager;
import com.android.systemui.plugins.clocks.AlarmData;
import com.android.systemui.plugins.clocks.ClockController;
import com.android.systemui.statusbar.policy.ZenModeController;
import com.android.systemui.statusbar.policy.ZenModeControllerImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
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
        ClockEventController.access$handleZenMode(this.this$0, i);
    }
}
