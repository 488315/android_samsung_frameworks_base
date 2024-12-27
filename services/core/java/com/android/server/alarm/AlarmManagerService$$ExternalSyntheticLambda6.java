package com.android.server.alarm;

import android.content.Intent;
import android.os.UserHandle;

import com.android.internal.util.jobs.ArrayUtils;
import com.android.server.ProfileService$1$$ExternalSyntheticOutline0;

import java.util.function.Predicate;

public final /* synthetic */ class AlarmManagerService$$ExternalSyntheticLambda6
        implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ AlarmManagerService$$ExternalSyntheticLambda6(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                Alarm alarm = (Alarm) obj;
                Intent intent = AlarmManagerService.NEXT_ALARM_CLOCK_CHANGED_INTENT;
                if (!ArrayUtils.contains((int[]) obj2, alarm.uid)
                        || alarm.listener == null
                        || alarm.windowLength != 0) {
                    return false;
                }
                StringBuilder sb = new StringBuilder("Alarm ");
                sb.append(alarm.listenerTag);
                sb.append(" being removed for ");
                sb.append(UserHandle.formatUid(alarm.uid));
                sb.append(":");
                ProfileService$1$$ExternalSyntheticOutline0.m(
                        sb, alarm.packageName, " because the app got frozen", "AlarmManager");
                return true;
            case 1:
                Intent intent2 = AlarmManagerService.NEXT_ALARM_CLOCK_CHANGED_INTENT;
                return ((AlarmManagerService) obj2).isBackgroundRestricted((Alarm) obj);
            case 2:
                Intent intent3 = AlarmManagerService.NEXT_ALARM_CLOCK_CHANGED_INTENT;
                return ((String) obj2).equals(((Alarm) obj).sourcePackage);
            default:
                return ((Alarm) obj2).equals((Alarm) obj);
        }
    }
}
