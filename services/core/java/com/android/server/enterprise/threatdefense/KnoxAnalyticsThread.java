package com.android.server.enterprise.threatdefense;

import android.util.Log;
import com.samsung.android.knox.analytics.KnoxAnalytics;
import com.samsung.android.knox.analytics.KnoxAnalyticsData;
import com.samsung.android.knox.analytics.util.KnoxAnalyticsDataConverter;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes2.dex */
public class KnoxAnalyticsThread {
  public static final String TAG = "KnoxAnalyticsThread";
  public final Object mLock = new Object();
  public static final String[] EVENT = {"PROC_USAGE", "PROC_PID_USAGE", "GET_PID_USAGE"};
  public static final Hashtable sApiCalledCount = new Hashtable();

  public void countApiCall(String str, int i) {
    if (str == null || i >= 3) {
      return;
    }
    synchronized (this.mLock) {
      boolean z = true;
      try {
        try {
          try {
            Hashtable hashtable = sApiCalledCount;
            if (hashtable.get(str) == null) {
              Hashtable hashtable2 = new Hashtable();
              hashtable2.put(0, 0L);
              hashtable2.put(1, 0L);
              hashtable2.put(2, 0L);
              hashtable.put(str, hashtable2);
            }
            long longValue =
                ((Long) ((Hashtable) hashtable.get(str)).get(Integer.valueOf(i))).longValue();
            if (longValue == 2147483646) {
              if (ThreatDefenseService.DEBUG) {
                Log.d(TAG, "pkg=" + str + ", api=" + i + ", c=" + longValue);
              }
              sendKnoxAnalyticsLogs(str, i, longValue + 1);
              flush(str, i);
            } else {
              ((Hashtable) hashtable.get(str)).put(Integer.valueOf(i), Long.valueOf(longValue + 1));
            }
            z = false;
          } catch (IllegalArgumentException e) {
            e.printStackTrace();
          }
        } catch (ClassCastException e2) {
          e2.printStackTrace();
        }
      } catch (NullPointerException e3) {
        e3.printStackTrace();
      } catch (UnsupportedOperationException e4) {
        e4.printStackTrace();
      }
      if (z) {
        reset();
      }
    }
  }

  public void schedule() {
    synchronized (this.mLock) {
      Iterator it = sApiCalledCount.entrySet().iterator();
      while (it.hasNext()) {
        String str = (String) ((Map.Entry) it.next()).getKey();
        if (ThreatDefenseService.DEBUG) {
          Log.d(TAG, "KnoxAnalytics triggered pkg=" + str);
        }
        if (str != null && !str.isEmpty()) {
          for (int i = 0; i < 3; i++) {
            try {
              sendKnoxAnalyticsLogs(
                  str,
                  i,
                  ((Long) ((Hashtable) sApiCalledCount.get(str)).get(Integer.valueOf(i)))
                      .longValue());
            } catch (NullPointerException e) {
              e.printStackTrace();
            }
          }
        }
      }
    }
  }

  public final void sendKnoxAnalyticsLogs(String str, int i, long j) {
    KnoxAnalyticsData knoxAnalyticsData = new KnoxAnalyticsData("KNOX_MTD", 1, "MTD_USAGE_EVENT");
    knoxAnalyticsData.setProperty(KnoxAnalyticsDataConverter.EVENT, EVENT[i]);
    knoxAnalyticsData.setProperty("c", j);
    knoxAnalyticsData.setProperty("pN", str);
    if (j == 0) {
      Log.d(TAG, "Skip!! api=" + i);
      return;
    }
    KnoxAnalytics.log(knoxAnalyticsData);
    if (ThreatDefenseService.DEBUG) {
      Log.d(TAG, "KA Data : " + knoxAnalyticsData.toString());
    } else {
      Log.i(TAG, "KA Data : " + knoxAnalyticsData.getPayload().toString());
    }
    flush(str, i);
  }

  public final void reset() {
    Log.e(TAG, "Reset");
    synchronized (this.mLock) {
      sApiCalledCount.clear();
    }
  }

  public final void flush(String str, int i) {
    long j;
    if (str == null || i >= 3) {
      Log.e(TAG, "pkg=" + str + ", api=" + i);
      return;
    }
    long j2 = 0;
    try {
    } catch (NullPointerException e) {
      e = e;
      j = 0;
    }
    synchronized (this.mLock) {
      try {
        Hashtable hashtable = sApiCalledCount;
        long j3 = ((Long) ((Hashtable) hashtable.get(str)).get(Integer.valueOf(i))).longValue();
        try {
          ((Hashtable) hashtable.get(str)).replace(Integer.valueOf(i), 0L);
          j2 = ((Long) ((Hashtable) hashtable.get(str)).get(Integer.valueOf(i))).longValue();
          Log.i(TAG, "Flush!! status: " + j3 + "->" + j2);
        } catch (Throwable th) {
          th = th;
          long j4 = j2;
          j2 = j3;
          j = j4;
          while (true) {
            try {
              try {
                throw th;
              } catch (NullPointerException e2) {
                e = e2;
                e.printStackTrace();
                long j5 = j2;
                j2 = j;
                j3 = j5;
                Log.i(TAG, "Flush!! status: " + j3 + "->" + j2);
              }
            } catch (Throwable th2) {
              th = th2;
            }
          }
        }
      } catch (Throwable th3) {
        th = th3;
        j = 0;
      }
    }
  }
}
