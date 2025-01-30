package com.android.server.alarm;

import android.util.IndentingPrintWriter;
import android.util.Slog;
import android.util.proto.ProtoOutputStream;
import com.android.internal.util.jobs.StatLogger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.function.Predicate;
import java.util.function.ToLongFunction;

/* loaded from: classes.dex */
public class LazyAlarmStore implements AlarmStore {
  static final String TAG = "LazyAlarmStore";
  public static final Comparator sDecreasingTimeOrder =
      Comparator.comparingLong(
              new ToLongFunction() { // from class:
                                     // com.android.server.alarm.LazyAlarmStore$$ExternalSyntheticLambda0
                @Override // java.util.function.ToLongFunction
                public final long applyAsLong(Object obj) {
                  return ((Alarm) obj).getWhenElapsed();
                }
              })
          .reversed();
  public Runnable mOnAlarmClockRemoved;
  public final ArrayList mAlarms = new ArrayList();
  public final StatLogger mStatLogger =
      new StatLogger(
          TAG + " stats",
          new String[] {"GET_NEXT_DELIVERY_TIME", "GET_NEXT_WAKEUP_DELIVERY_TIME", "GET_COUNT"});

  @Override // com.android.server.alarm.AlarmStore
  public void add(Alarm alarm) {
    int binarySearch = Collections.binarySearch(this.mAlarms, alarm, sDecreasingTimeOrder);
    if (binarySearch < 0) {
      binarySearch = (0 - binarySearch) - 1;
    }
    this.mAlarms.add(binarySearch, alarm);
  }

  public void addAll(ArrayList arrayList) {
    if (arrayList == null) {
      return;
    }
    this.mAlarms.addAll(arrayList);
    Collections.sort(this.mAlarms, sDecreasingTimeOrder);
  }

  @Override // com.android.server.alarm.AlarmStore
  public ArrayList remove(Predicate predicate) {
    Runnable runnable;
    ArrayList arrayList = new ArrayList();
    for (int size = this.mAlarms.size() - 1; size >= 0; size--) {
      if (predicate.test((Alarm) this.mAlarms.get(size))) {
        Alarm alarm = (Alarm) this.mAlarms.remove(size);
        if (alarm.alarmClock != null && (runnable = this.mOnAlarmClockRemoved) != null) {
          runnable.run();
        }
        if (AlarmManagerService.isTimeTickAlarm(alarm)) {
          Slog.wtf(TAG, "Removed TIME_TICK alarm");
        }
        arrayList.add(alarm);
      }
    }
    return arrayList;
  }

  @Override // com.android.server.alarm.AlarmStore
  public void setAlarmClockRemovalListener(Runnable runnable) {
    this.mOnAlarmClockRemoved = runnable;
  }

  @Override // com.android.server.alarm.AlarmStore
  public Alarm getNextWakeFromIdleAlarm() {
    for (int size = this.mAlarms.size() - 1; size >= 0; size--) {
      Alarm alarm = (Alarm) this.mAlarms.get(size);
      if ((alarm.flags & 2) != 0) {
        return alarm;
      }
    }
    return null;
  }

  @Override // com.android.server.alarm.AlarmStore
  public int size() {
    return this.mAlarms.size();
  }

  @Override // com.android.server.alarm.AlarmStore
  public long getNextWakeupDeliveryTime() {
    long time = this.mStatLogger.getTime();
    long j = 0;
    for (int size = this.mAlarms.size() - 1; size >= 0; size--) {
      Alarm alarm = (Alarm) this.mAlarms.get(size);
      if (alarm.wakeup && !AlarmManagerService.isMARsRestricted(alarm)) {
        if (j == 0) {
          j = alarm.getMaxWhenElapsed();
        } else {
          if (alarm.getWhenElapsed() > j) {
            break;
          }
          j = Math.min(j, alarm.getMaxWhenElapsed());
        }
      }
    }
    this.mStatLogger.logDurationStat(1, time);
    return j;
  }

  @Override // com.android.server.alarm.AlarmStore
  public long getNextDeliveryTime() {
    long time = this.mStatLogger.getTime();
    int size = this.mAlarms.size();
    if (size == 0) {
      return 0L;
    }
    long maxWhenElapsed = ((Alarm) this.mAlarms.get(size - 1)).getMaxWhenElapsed();
    for (int i = size - 2; i >= 0; i--) {
      Alarm alarm = (Alarm) this.mAlarms.get(i);
      if (alarm.getWhenElapsed() > maxWhenElapsed) {
        break;
      }
      maxWhenElapsed = Math.min(maxWhenElapsed, alarm.getMaxWhenElapsed());
    }
    this.mStatLogger.logDurationStat(0, time);
    return maxWhenElapsed;
  }

  @Override // com.android.server.alarm.AlarmStore
  public ArrayList removePendingAlarms(long j) {
    ArrayList arrayList = new ArrayList();
    boolean z = false;
    boolean z2 = false;
    for (int size = this.mAlarms.size() - 1; size >= 0; size--) {
      Alarm alarm = (Alarm) this.mAlarms.get(size);
      if (alarm.getWhenElapsed() > j) {
        break;
      }
      this.mAlarms.remove(size);
      arrayList.add(alarm);
      if (alarm.wakeup && alarm.getMaxWhenElapsed() <= 500 + j) {
        z = true;
      }
      if ((alarm.flags & 1) != 0) {
        z2 = true;
      }
    }
    ArrayList arrayList2 = new ArrayList();
    for (int size2 = arrayList.size() - 1; size2 >= 0; size2--) {
      Alarm alarm2 = (Alarm) arrayList.get(size2);
      if ((z || !alarm2.wakeup) && (!z2 || (alarm2.flags & 1) != 0)) {
        arrayList.remove(size2);
        arrayList2.add(alarm2);
      }
    }
    addAll(arrayList);
    return arrayList2;
  }

  @Override // com.android.server.alarm.AlarmStore
  public boolean updateAlarmDeliveries(AlarmStore.AlarmDeliveryCalculator alarmDeliveryCalculator) {
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

  @Override // com.android.server.alarm.AlarmStore
  public ArrayList asList() {
    ArrayList arrayList = new ArrayList(this.mAlarms);
    Collections.reverse(arrayList);
    return arrayList;
  }

  @Override // com.android.server.alarm.AlarmStore
  public void dump(
      IndentingPrintWriter indentingPrintWriter, long j, SimpleDateFormat simpleDateFormat) {
    indentingPrintWriter.println(this.mAlarms.size() + " pending alarms: ");
    indentingPrintWriter.increaseIndent();
    AlarmManagerService.dumpAlarmList(indentingPrintWriter, this.mAlarms, j, simpleDateFormat);
    indentingPrintWriter.decreaseIndent();
    this.mStatLogger.dump(indentingPrintWriter);
  }

  @Override // com.android.server.alarm.AlarmStore
  public void dumpProto(ProtoOutputStream protoOutputStream, long j) {
    Iterator it = this.mAlarms.iterator();
    while (it.hasNext()) {
      ((Alarm) it.next()).dumpDebug(protoOutputStream, 2246267895850L, j);
    }
  }

  @Override // com.android.server.alarm.AlarmStore
  public int getCount(Predicate predicate) {
    long time = this.mStatLogger.getTime();
    Iterator it = this.mAlarms.iterator();
    int i = 0;
    while (it.hasNext()) {
      if (predicate.test((Alarm) it.next())) {
        i++;
      }
    }
    this.mStatLogger.logDurationStat(2, time);
    return i;
  }
}
