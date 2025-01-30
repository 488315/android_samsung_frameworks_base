package com.android.server;

import android.app.PendingIntent;

/* loaded from: classes.dex */
public interface AlarmManagerInternal {

  public interface InFlightListener {
    void broadcastAlarmComplete(int i);

    void broadcastAlarmPending(int i);
  }

  boolean isIdling();

  void onFreezeStateChanged(boolean z, int i);

  void registerInFlightListener(InFlightListener inFlightListener);

  void remove(PendingIntent pendingIntent);

  void removeAlarmsForUid(int i);

  void setTime(long j, int i, String str);

  void setTimeZone(String str, int i, String str2);

  boolean shouldGetBucketElevation(String str, int i);
}
