package com.android.server.chimera.psitracker;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.content.Context;
import android.database.Cursor;
import android.net.INetd;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.android.server.backup.BackupManagerConstants;
import com.android.server.chimera.SystemEventListener;
import com.android.server.chimera.SystemRepository;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/* loaded from: classes.dex */
public class PSITracker implements SystemEventListener.TimeOrTimeZoneChangedListener {
  public static boolean DEBUG = true;
  public static boolean mFirstTriggeredAfterBooting = false;

  /* renamed from: db */
  public PSIDBManager f1669db;
  public final AlarmManager.OnAlarmListener mAvailMemRecord240AlarmListener =
      new AlarmManager
          .OnAlarmListener() { // from class: com.android.server.chimera.psitracker.PSITracker.1
        @Override // android.app.AlarmManager.OnAlarmListener
        public void onAlarm() {
          PSITracker.this.scheduleAvailMem240PeriodRecord("240 Alarm fired");
          PSITracker.this.mHandler.sendMessage(PSITracker.this.mHandler.obtainMessage(1));
        }
      };
  public PSITrackerCollector mCollector;
  public Context mContext;
  public PSITrackerHandler mHandler;
  public SystemRepository mSystemRepository;

  public static boolean isSystemApp(int i) {
    if (i < 10000) {
      return ((i >= 5000 && i <= 5999) || i == 1200 || i == 1201) ? false : true;
    }
    return false;
  }

  public PSITracker(SystemRepository systemRepository, Context context, Looper looper) {
    this.mSystemRepository = systemRepository;
    this.mContext = context;
    this.mCollector = new PSITrackerCollector(this.mContext);
    PSIDBManager.init(this.mContext);
    this.mHandler = new PSITrackerHandler(looper);
  }

  public void scheduleAvailMem240PeriodRecord(String str) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(System.currentTimeMillis());
    long currentTimeMillis = System.currentTimeMillis();
    calendar.setTimeInMillis(
        System.currentTimeMillis()
            + BackupManagerConstants.DEFAULT_KEY_VALUE_BACKUP_INTERVAL_MILLISECONDS);
    Log.d("PSITracker", "Schedule next trigger time interval: 4 /now Time: " + currentTimeMillis);
    Log.d(
        "PSITracker",
        "Schedule next trigger time: " + calendar.getTimeInMillis() + " reason = " + str);
    AlarmManager alarmManager = (AlarmManager) this.mContext.getSystemService("alarm");
    alarmManager.cancel(this.mAvailMemRecord240AlarmListener);
    alarmManager.set(
        1,
        calendar.getTimeInMillis(),
        "RecordAvailMem240",
        this.mAvailMemRecord240AlarmListener,
        this.mHandler);
  }

  public final void recordAvailableMemValue(long j, long j2, long j3) {
    PSIAvailableMemRecord generateAvailableMemRecord =
        this.mCollector.generateAvailableMemRecord(j, j2, j3, System.currentTimeMillis());
    if (generateAvailableMemRecord != null) {
      this.mCollector.saveAvailableMemRecord(generateAvailableMemRecord);
    }
    if (this.mCollector.isAvailableMemRecordsSizeMax()) {
      this.mHandler.sendMessage(this.mHandler.obtainMessage(2));
    }
  }

  /* JADX WARN: Removed duplicated region for block: B:25:0x0054  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final void record240MinutesAvailMem() {
    long j;
    ActivityManager.MemoryInfo memoryInfo = this.mSystemRepository.getMemoryInfo();
    long j2 = 0;
    try {
      j = 0;
      for (SystemRepository.RunningAppProcessInfo runningAppProcessInfo :
          this.mSystemRepository.getRunningAppProcesses()) {
        try {
          if (runningAppProcessInfo.importance >= 400) {
            j2 += this.mSystemRepository.getMemmoryOfPid(runningAppProcessInfo.pid);
          } else if (!isSystemApp(runningAppProcessInfo.uid)) {
            j += this.mSystemRepository.getMemmoryOfPid(runningAppProcessInfo.pid);
          }
        } catch (Exception e) {
          e = e;
          e.printStackTrace();
          long j3 = j2;
          long j4 = j;
          long j5 = memoryInfo.availMem / 1024;
          if (DEBUG) {}
          recordAvailableMemValue(j5, j4, j3);
        }
      }
    } catch (Exception e2) {
      e = e2;
      j = 0;
    }
    long j32 = j2;
    long j42 = j;
    long j52 = memoryInfo.availMem / 1024;
    if (DEBUG) {
      Log.d(
          "PSITracker",
          " getAvailable memInfo.totalMem = "
              + memoryInfo.totalMem
              + "  available = "
              + j52
              + " runningSize = "
              + j42
              + " cachedSize = "
              + j32);
    }
    recordAvailableMemValue(j52, j42, j32);
  }

  /* JADX WARN: Code restructure failed: missing block: B:19:0x00e4, code lost:

     if (r2 != null) goto L27;
  */
  /* JADX WARN: Code restructure failed: missing block: B:20:0x00f2, code lost:

     r0 = new java.util.ArrayList();
     r1 = 0;
  */
  /* JADX WARN: Code restructure failed: missing block: B:22:0x00fc, code lost:

     if (r1 >= r5.size()) goto L40;
  */
  /* JADX WARN: Code restructure failed: missing block: B:23:0x00fe, code lost:

     r0.add(new com.samsung.android.chimera.PSIAvailableMem(((java.lang.Long) r6.get(r1)).longValue(), ((java.lang.Long) r7.get(r1)).longValue(), ((java.lang.Long) r8.get(r1)).longValue(), ((java.lang.Long) r9.get(r1)).longValue()));
     r1 = r1 + 1;
  */
  /* JADX WARN: Code restructure failed: missing block: B:25:0x0132, code lost:

     return r0;
  */
  /* JADX WARN: Code restructure failed: missing block: B:27:0x00ef, code lost:

     r2.close();
  */
  /* JADX WARN: Code restructure failed: missing block: B:30:0x00ed, code lost:

     if (r2 == null) goto L28;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public List getAvailableMemInfo(long j, long j2) {
    String str;
    ArrayList arrayList = new ArrayList();
    ArrayList arrayList2 = new ArrayList();
    ArrayList arrayList3 = new ArrayList();
    ArrayList arrayList4 = new ArrayList();
    ArrayList arrayList5 = new ArrayList();
    PSIDBManager pSIDBManager = PSIDBManager.getInstance();
    this.f1669db = pSIDBManager;
    if (pSIDBManager.isDBClosed()) {
      Log.e("PSITracker", "getAvailableMemInfo failed! db is closed!");
    }
    if (j != 0 || j2 != 0) {
      str =
          "SELECT id,availMem,running,cached,checkTime FROM "
              + this.f1669db.mAvailMemTable
              + " WHERE checkTime <= "
              + j2
              + " AND checkTime > "
              + j
              + " ORDER BY id";
    } else {
      str =
          "SELECT id,availMem,running,cached,checkTime FROM "
              + this.f1669db.mAvailMemTable
              + " ORDER BY id";
    }
    Cursor cursor = null;
    try {
      try {
        cursor = this.f1669db.rawQuery(str, null);
        if (cursor != null) {
          int columnIndex = cursor.getColumnIndex("id");
          int columnIndex2 = cursor.getColumnIndex("availMem");
          int columnIndex3 = cursor.getColumnIndex(INetd.IF_FLAG_RUNNING);
          int columnIndex4 = cursor.getColumnIndex("cached");
          int columnIndex5 = cursor.getColumnIndex("checkTime");
          while (cursor.moveToNext()) {
            arrayList.add(Integer.valueOf(cursor.getInt(columnIndex)));
            arrayList2.add(Long.valueOf(cursor.getLong(columnIndex2)));
            arrayList3.add(Long.valueOf(cursor.getLong(columnIndex3)));
            arrayList4.add(Long.valueOf(cursor.getLong(columnIndex4)));
            arrayList5.add(Long.valueOf(cursor.getLong(columnIndex5)));
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    } catch (Throwable th) {
      if (cursor != null) {
        cursor.close();
      }
      throw th;
    }
  }

  @Override // com.android.server.chimera.SystemEventListener.TimeOrTimeZoneChangedListener
  public void onTimeOrTimeZoneChanged(String str) {
    if (DEBUG) {
      this.mSystemRepository.log("PSITracker", "onTimeOrTimeZoneChanged() action: " + str);
    }
    scheduleAvailMem240PeriodRecord("TIME_CHANGED");
  }

  public class PSITrackerHandler extends Handler {
    public PSITrackerHandler(Looper looper) {
      super(looper);
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
      int i = message.what;
      if (i == 1) {
        PSITracker.this.record240MinutesAvailMem();
      } else if (i == 2 && PSITracker.this.mCollector != null) {
        PSITracker.this.mCollector.saveAvailableMemRecords();
      }
    }
  }

  /* JADX WARN: Code restructure failed: missing block: B:21:0x00df, code lost:

     if (r13 != null) goto L28;
  */
  /* JADX WARN: Code restructure failed: missing block: B:22:0x00ed, code lost:

     r10 = 0;
  */
  /* JADX WARN: Code restructure failed: missing block: B:24:0x00f2, code lost:

     if (r10 >= r0.size()) goto L40;
  */
  /* JADX WARN: Code restructure failed: missing block: B:25:0x00f4, code lost:

     r11.println("AvailMem ID : " + r0.get(r10) + "Availmem : " + r1.get(r10) + ", running : " + r2.get(r10) + ", cached : " + r3.get(r10) + ", checkTime : " + r4.get(r10));
     r10 = r10 + 1;
  */
  /* JADX WARN: Code restructure failed: missing block: B:27:0x013f, code lost:

     return;
  */
  /* JADX WARN: Code restructure failed: missing block: B:28:0x00ea, code lost:

     r13.close();
  */
  /* JADX WARN: Code restructure failed: missing block: B:35:0x00e8, code lost:

     if (r13 == null) goto L29;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public void getPSIValueListDump(PrintWriter printWriter, long j, long j2) {
    String str;
    ArrayList arrayList = new ArrayList();
    ArrayList arrayList2 = new ArrayList();
    ArrayList arrayList3 = new ArrayList();
    ArrayList arrayList4 = new ArrayList();
    ArrayList arrayList5 = new ArrayList();
    PSIDBManager pSIDBManager = PSIDBManager.getInstance();
    this.f1669db = pSIDBManager;
    if (pSIDBManager.isDBClosed()) {
      Log.e("PSITracker", "getPSI failed! db is closed!");
      return;
    }
    if (j != 0 || j2 != 0) {
      str =
          "SELECT id,availMem,running,cached,checkTime FROM "
              + this.f1669db.mAvailMemTable
              + " WHERE checkTime <= "
              + j2
              + " AND checkTime > "
              + j
              + " ORDER BY id";
    } else {
      str =
          "SELECT id,availMem,running,cached,checkTime FROM "
              + this.f1669db.mAvailMemTable
              + " ORDER BY id";
    }
    Cursor cursor = null;
    try {
      try {
        cursor = this.f1669db.rawQuery(str, null);
        if (cursor != null) {
          int columnIndex = cursor.getColumnIndex("id");
          int columnIndex2 = cursor.getColumnIndex("availMem");
          int columnIndex3 = cursor.getColumnIndex(INetd.IF_FLAG_RUNNING);
          int columnIndex4 = cursor.getColumnIndex("cached");
          int columnIndex5 = cursor.getColumnIndex("checkTime");
          while (cursor.moveToNext()) {
            arrayList.add(Integer.valueOf(cursor.getInt(columnIndex)));
            arrayList2.add(Long.valueOf(cursor.getLong(columnIndex2)));
            arrayList3.add(Long.valueOf(cursor.getLong(columnIndex3)));
            arrayList4.add(Long.valueOf(cursor.getLong(columnIndex4)));
            arrayList5.add(Long.valueOf(cursor.getLong(columnIndex5)));
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    } catch (Throwable th) {
      if (cursor != null) {
        cursor.close();
      }
      throw th;
    }
  }

  public void dumpInfo(PrintWriter printWriter, String[] strArr) {
    if (strArr.length == 3) {
      String str = strArr[1];
      String str2 = strArr[2];
      if ("0".equals(str) || "0".equals(str2)) {
        getPSIValueListDump(printWriter, 0L, 0L);
        return;
      }
      try {
        getPSIValueListDump(printWriter, Long.parseLong(str), Long.parseLong(str2));
        return;
      } catch (NumberFormatException unused) {
        getPSIValueListDump(printWriter, 0L, 0L);
        return;
      }
    }
    getPSIValueListDump(printWriter, 0L, 0L);
  }
}
