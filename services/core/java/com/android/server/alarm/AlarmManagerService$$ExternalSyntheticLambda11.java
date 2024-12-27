package com.android.server.alarm;

import android.content.Intent;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class AlarmManagerService$$ExternalSyntheticLambda11 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ int f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ AlarmManagerService$$ExternalSyntheticLambda11(int i, int i2, Object obj) {
        this.$r8$classId = i2;
        this.f$1 = obj;
        this.f$0 = i;
    }

    public /* synthetic */ AlarmManagerService$$ExternalSyntheticLambda11(int i, String str) {
        this.$r8$classId = 0;
        this.f$0 = i;
        this.f$1 = str;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                int i = this.f$0;
                String str = (String) this.f$1;
                Alarm alarm = (Alarm) obj;
                Intent intent = AlarmManagerService.NEXT_ALARM_CLOCK_CHANGED_INTENT;
                if (alarm.uid == i && alarm.packageName.equals(str) && alarm.windowLength == 0) {
                    break;
                }
                break;
            case 1:
                String str2 = (String) this.f$1;
                int i2 = this.f$0;
                Alarm alarm2 = (Alarm) obj;
                if (str2.equals(alarm2.sourcePackage) && alarm2.creatorUid == i2) {
                    break;
                }
                break;
            default:
                AlarmManagerService alarmManagerService = (AlarmManagerService) this.f$1;
                int i3 = this.f$0;
                Alarm alarm3 = (Alarm) obj;
                Intent intent2 = AlarmManagerService.NEXT_ALARM_CLOCK_CHANGED_INTENT;
                alarmManagerService.getClass();
                if (alarm3.uid == i3 && alarmManagerService.mActivityManagerInternal.isAppStartModeDisabled(i3, alarm3.packageName)) {
                    break;
                }
                break;
        }
        return true;
    }
}
