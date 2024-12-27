package com.android.server.alarm;

import android.content.Intent;
import android.os.UserHandle;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class AlarmManagerService$$ExternalSyntheticLambda3
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AlarmManagerService f$0;

    public /* synthetic */ AlarmManagerService$$ExternalSyntheticLambda3(
            AlarmManagerService alarmManagerService, int i) {
        this.$r8$classId = i;
        this.f$0 = alarmManagerService;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        AlarmManagerService alarmManagerService = this.f$0;
        switch (i) {
            case 0:
                alarmManagerService.mNextAlarmClockMayChange = true;
                break;
            default:
                Intent intent = AlarmManagerService.NEXT_ALARM_CLOCK_CHANGED_INTENT;
                alarmManagerService
                        .getContext()
                        .sendBroadcastAsUser(
                                alarmManagerService.mTimeTickIntent,
                                UserHandle.ALL,
                                null,
                                alarmManagerService.mTimeTickOptions);
                break;
        }
    }
}
