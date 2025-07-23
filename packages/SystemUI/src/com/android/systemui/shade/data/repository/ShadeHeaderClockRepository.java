package com.android.systemui.shade.data.repository;

import android.app.AlarmManager;
import android.app.PendingIntent;
import com.android.systemui.statusbar.policy.NextAlarmController;
import com.android.systemui.statusbar.policy.NextAlarmControllerImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ShadeHeaderClockRepository {
    public final ShadeHeaderClockRepository$nextAlarmCallback$1 nextAlarmCallback;
    public PendingIntent nextAlarmIntent;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.shade.data.repository.ShadeHeaderClockRepository$nextAlarmCallback$1, java.lang.Object] */
    public ShadeHeaderClockRepository(NextAlarmController nextAlarmController) {
        ?? r0 = new NextAlarmController.NextAlarmChangeCallback() { // from class: com.android.systemui.shade.data.repository.ShadeHeaderClockRepository$nextAlarmCallback$1
            @Override // com.android.systemui.statusbar.policy.NextAlarmController.NextAlarmChangeCallback
            public final void onNextAlarmChanged(AlarmManager.AlarmClockInfo alarmClockInfo) {
                ShadeHeaderClockRepository.this.nextAlarmIntent = alarmClockInfo != null ? alarmClockInfo.getShowIntent() : null;
            }
        };
        this.nextAlarmCallback = r0;
        ((NextAlarmControllerImpl) nextAlarmController).addCallback(r0);
    }
}
