package com.android.server.alarm;

import android.app.IAlarmListener;
import android.app.PendingIntent;
import android.content.Intent;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class AlarmManagerService$$ExternalSyntheticLambda8 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ AlarmManagerService$$ExternalSyntheticLambda8() {
        this.$r8$classId = 1;
        this.f$0 = "com.google.android.gms";
        this.f$1 = "com.google.android.intent.action.GCM_RECONNECT";
    }

    public /* synthetic */ AlarmManagerService$$ExternalSyntheticLambda8(PendingIntent pendingIntent, IAlarmListener iAlarmListener) {
        this.$r8$classId = 0;
        this.f$0 = pendingIntent;
        this.f$1 = iAlarmListener;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                PendingIntent pendingIntent = (PendingIntent) this.f$0;
                IAlarmListener iAlarmListener = (IAlarmListener) this.f$1;
                Alarm alarm = (Alarm) obj;
                Intent intent = AlarmManagerService.NEXT_ALARM_CLOCK_CHANGED_INTENT;
                PendingIntent pendingIntent2 = alarm.operation;
                if (pendingIntent2 == null) {
                    if (iAlarmListener != null && alarm.listener.asBinder().equals(iAlarmListener.asBinder())) {
                        break;
                    }
                } else {
                    break;
                }
                break;
            default:
                String str = (String) this.f$0;
                String str2 = (String) this.f$1;
                Alarm alarm2 = (Alarm) obj;
                Intent intent2 = AlarmManagerService.NEXT_ALARM_CLOCK_CHANGED_INTENT;
                PendingIntent pendingIntent3 = alarm2.operation;
                if (pendingIntent3 != null && str.equals(pendingIntent3.getCreatorPackage()) && alarm2.operation.getIntent() != null && str2.equals(alarm2.operation.getIntent().getAction())) {
                    break;
                }
                break;
        }
        return true;
    }
}
