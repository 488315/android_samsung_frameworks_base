package com.android.server.alarm;

import android.content.Intent;
import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.util.IndentingPrintWriter;
import android.util.Slog;

import com.android.internal.util.jobs.StatLogger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class LazyAlarmStore implements AlarmStore {
    static final String TAG = "LazyAlarmStore";
    public static final Comparator sDecreasingTimeOrder =
            Comparator.comparingLong(new LazyAlarmStore$$ExternalSyntheticLambda0()).reversed();
    public Runnable mOnAlarmClockRemoved;
    public final ArrayList mAlarms = new ArrayList();
    public final StatLogger mStatLogger =
            new StatLogger(
                    AudioOffloadInfo$$ExternalSyntheticOutline0.m(
                            new StringBuilder(), TAG, " stats"),
                    new String[] {
                        "GET_NEXT_DELIVERY_TIME", "GET_NEXT_WAKEUP_DELIVERY_TIME", "GET_COUNT"
                    });

    public final void dump(
            IndentingPrintWriter indentingPrintWriter, long j, SimpleDateFormat simpleDateFormat) {
        indentingPrintWriter.println(this.mAlarms.size() + " pending alarms: ");
        indentingPrintWriter.increaseIndent();
        AlarmManagerService.dumpAlarmList(indentingPrintWriter, this.mAlarms, j, simpleDateFormat);
        indentingPrintWriter.decreaseIndent();
        this.mStatLogger.dump(indentingPrintWriter);
    }

    public final int getCount(Predicate predicate) {
        StatLogger statLogger = this.mStatLogger;
        long time = statLogger.getTime();
        Iterator it = this.mAlarms.iterator();
        int i = 0;
        while (it.hasNext()) {
            if (predicate.test((Alarm) it.next())) {
                i++;
            }
        }
        statLogger.logDurationStat(2, time);
        return i;
    }

    public final Alarm getNextWakeFromIdleAlarm() {
        for (int size = this.mAlarms.size() - 1; size >= 0; size--) {
            Alarm alarm = (Alarm) this.mAlarms.get(size);
            if ((alarm.flags & 2) != 0) {
                return alarm;
            }
        }
        return null;
    }

    public final ArrayList remove(Predicate predicate) {
        Runnable runnable;
        ArrayList arrayList = new ArrayList();
        for (int size = this.mAlarms.size() - 1; size >= 0; size--) {
            if (predicate.test((Alarm) this.mAlarms.get(size))) {
                Alarm alarm = (Alarm) this.mAlarms.remove(size);
                if (alarm.alarmClock != null && (runnable = this.mOnAlarmClockRemoved) != null) {
                    runnable.run();
                }
                Intent intent = AlarmManagerService.NEXT_ALARM_CLOCK_CHANGED_INTENT;
                if (alarm.uid == 1000 && "TIME_TICK".equals(alarm.listenerTag)) {
                    Slog.wtf(TAG, "Removed TIME_TICK alarm");
                }
                arrayList.add(alarm);
            }
        }
        return arrayList;
    }

    public final boolean updateAlarmDeliveries(
            AlarmStore.AlarmDeliveryCalculator alarmDeliveryCalculator) {
        Iterator it = this.mAlarms.iterator();
        boolean z = false;
        while (it.hasNext()) {
            z |= alarmDeliveryCalculator.updateAlarmDelivery((Alarm) it.next());
        }
        if (z) {
            Collections.sort(this.mAlarms, sDecreasingTimeOrder);
        }
        return z;
    }
}
