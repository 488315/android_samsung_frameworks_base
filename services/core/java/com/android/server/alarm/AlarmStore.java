package com.android.server.alarm;

import android.util.IndentingPrintWriter;
import android.util.proto.ProtoOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.function.Predicate;

/* loaded from: classes.dex */
public interface AlarmStore {

  public interface AlarmDeliveryCalculator {
    boolean updateAlarmDelivery(Alarm alarm);
  }

  void add(Alarm alarm);

  ArrayList asList();

  void dump(IndentingPrintWriter indentingPrintWriter, long j, SimpleDateFormat simpleDateFormat);

  void dumpProto(ProtoOutputStream protoOutputStream, long j);

  int getCount(Predicate predicate);

  long getNextDeliveryTime();

  Alarm getNextWakeFromIdleAlarm();

  long getNextWakeupDeliveryTime();

  ArrayList remove(Predicate predicate);

  ArrayList removePendingAlarms(long j);

  void setAlarmClockRemovalListener(Runnable runnable);

  int size();

  boolean updateAlarmDeliveries(AlarmDeliveryCalculator alarmDeliveryCalculator);
}
