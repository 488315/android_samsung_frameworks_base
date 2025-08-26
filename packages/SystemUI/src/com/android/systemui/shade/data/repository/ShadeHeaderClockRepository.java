package com.android.systemui.shade.data.repository;

import android.app.AlarmManager;
import android.app.PendingIntent;
import com.android.systemui.statusbar.policy.NextAlarmController;
import com.android.systemui.statusbar.policy.NextAlarmControllerImpl;

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
                this.this$0.nextAlarmIntent = alarmClockInfo != null ? alarmClockInfo.getShowIntent() : null;
            }
        };
        this.nextAlarmCallback = r0;
        ((NextAlarmControllerImpl) nextAlarmController).addCallback(r0);
    }
}
