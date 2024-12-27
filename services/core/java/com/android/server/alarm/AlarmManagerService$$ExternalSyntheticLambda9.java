package com.android.server.alarm;

import android.content.Intent;
import android.os.UserHandle;

import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class AlarmManagerService$$ExternalSyntheticLambda9
        implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ int f$0;

    public /* synthetic */ AlarmManagerService$$ExternalSyntheticLambda9(int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = i;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        int i2 = this.f$0;
        Alarm alarm = (Alarm) obj;
        switch (i) {
            case 0:
                Intent intent = AlarmManagerService.NEXT_ALARM_CLOCK_CHANGED_INTENT;
                if (alarm.uid == i2) {
                    break;
                }
                break;
            default:
                Intent intent2 = AlarmManagerService.NEXT_ALARM_CLOCK_CHANGED_INTENT;
                if (UserHandle.getUserId(alarm.uid) == i2) {
                    break;
                }
                break;
        }
        return true;
    }
}
