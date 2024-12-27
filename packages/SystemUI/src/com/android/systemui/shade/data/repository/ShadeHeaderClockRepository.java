package com.android.systemui.shade.data.repository;

import android.app.AlarmManager;
import android.app.PendingIntent;
import com.android.systemui.statusbar.policy.NextAlarmController;
import com.android.systemui.statusbar.policy.NextAlarmControllerImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
