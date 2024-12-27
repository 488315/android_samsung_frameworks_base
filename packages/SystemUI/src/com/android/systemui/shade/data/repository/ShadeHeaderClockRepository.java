package com.android.systemui.shade.data.repository;

import android.app.AlarmManager;
import android.app.PendingIntent;
import com.android.systemui.statusbar.policy.NextAlarmController;
import com.android.systemui.statusbar.policy.NextAlarmControllerImpl;

public final class ShadeHeaderClockRepository {
    public final ShadeHeaderClockRepository$nextAlarmCallback$1 nextAlarmCallback;
    public PendingIntent nextAlarmIntent;

    public ShadeHeaderClockRepository(NextAlarmController nextAlarmController) {
        ((NextAlarmControllerImpl) nextAlarmController).addCallback(new NextAlarmController.NextAlarmChangeCallback() { // from class: com.android.systemui.shade.data.repository.ShadeHeaderClockRepository$nextAlarmCallback$1
            @Override // com.android.systemui.statusbar.policy.NextAlarmController.NextAlarmChangeCallback
            public final void onNextAlarmChanged(AlarmManager.AlarmClockInfo alarmClockInfo) {
                ShadeHeaderClockRepository.this.nextAlarmIntent = alarmClockInfo != null ? alarmClockInfo.getShowIntent() : null;
            }
        });
    }
}
