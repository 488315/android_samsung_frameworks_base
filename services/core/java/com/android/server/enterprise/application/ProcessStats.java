package com.android.server.enterprise.application;

import android.os.Process;
import android.os.StrictMode;
import android.os.SystemClock;
import android.os.IInstalld;
import android.util.Log;
import android.util.SparseArray;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.display.DisplayPowerController2;
import com.android.server.pm.PackageManagerShellCommandDataLoader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/* loaded from: classes2.dex */
public class ProcessStats {
  public long mBaseIdleTime;
  public long mBaseIoWaitTime;
  public long mBaseIrqTime;
  public long mBaseSoftIrqTime;
  public long mBaseSystemTime;
  public long mBaseUserTime;
  public int[] mCurPids;
  public int[] mCurThreadPids;
  public long mCurrentSampleRealTime;
  public long mCurrentSampleTime;
  public final boolean mIncludeThreads;
  public long mLastSampleRealTime;
  public long mLastSampleTime;
  public int mRelIdleTime;
  public int mRelIoWaitTime;
  public int mRelIrqTime;
  public int mRelSoftIrqTime;
  public int mRelSystemTime;
  public int mRelUserTime;
  public boolean mWorkingProcsSorted;
  public static final int[] PROCESS_STATS_FORMAT = {
    32,
    FrameworkStatsLog.PACKAGE_MANAGER_SNAPSHOT_REPORTED,
    32,
    32,
    32,
    32,
    32,
    32,
    32,
    8224,
    32,
    8224,
    32,
    8224,
    8224
  };
  public static final int[] PROCESS_FULL_STATS_FORMAT = {
    32, 4640, 32, 32, 32, 32, 32, 32, 32, 8224, 32, 8224, 32, 8224, 8224, 32, 32, 32, 32, 32, 32,
    8224
  };
  public static final int[] SYSTEM_CPU_FORMAT = {288, 8224, 8224, 8224, 8224, 8224, 8224, 8224};
  public static final int[] LOAD_AVERAGE_FORMAT = {16416, 16416, 16416};
  public static final Comparator sLoadComparator =
      new Comparator() { // from class: com.android.server.enterprise.application.ProcessStats.1
        @Override // java.util.Comparator
        public final int compare(Stats stats, Stats stats2) {
          int i = stats.rel_utime + stats.rel_stime;
          int i2 = stats2.rel_utime + stats2.rel_stime;
          if (i != i2) {
            return i > i2 ? -1 : 1;
          }
          boolean z = stats.added;
          if (z != stats2.added) {
            return z ? -1 : 1;
          }
          if (stats.removed != stats2.removed) {
            return z ? -1 : 1;
          }
          return 0;
        }
      };
  public final long[] mProcessStatsData = new long[4];
  public final long[] mSinglePidStatsData = new long[4];
  public final String[] mProcessFullStatsStringData = new String[6];
  public final long[] mProcessFullStatsData = new long[6];
  public final long[] mSystemCpuData = new long[7];
  public final float[] mLoadAverageData = new float[3];
  public float mLoad1 = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
  public float mLoad5 = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
  public float mLoad15 = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
  public final ArrayList mProcStats = new ArrayList();
  public final ArrayList mWorkingProcs = new ArrayList();
  public boolean mFirst = true;
  public byte[] mBuffer = new byte[IInstalld.FLAG_USE_QUOTA];
  public final SparseArray mWorkingProcsMap = new SparseArray();

  public void onLoadChanged(float f, float f2, float f3) {}

  public int onMeasureProcessName(String str) {
    return 0;
  }

  public class Stats {
    public boolean active;
    public boolean added;
    public String baseName;
    public long base_majfaults;
    public long base_minfaults;
    public long base_stime;
    public long base_uptime;
    public long base_utime;
    public final String cmdlineFile;
    public boolean interesting;
    public String name;
    public int nameWidth;
    public final int pid;
    public int rel_majfaults;
    public int rel_minfaults;
    public int rel_stime;
    public long rel_uptime;
    public int rel_utime;
    public boolean removed;
    public final String statFile;
    public final ArrayList threadStats;
    public final String threadsDir;
    public boolean working;
    public final ArrayList workingThreads;

    public Stats(int i, int i2, boolean z) {
      this.pid = i;
      if (i2 < 0) {
        File file = new File("/proc", Integer.toString(i));
        this.statFile = new File(file, "stat").toString();
        this.cmdlineFile = new File(file, "cmdline").toString();
        this.threadsDir = new File(file, "task").toString();
        if (z) {
          this.threadStats = new ArrayList();
          this.workingThreads = new ArrayList();
          return;
        } else {
          this.threadStats = null;
          this.workingThreads = null;
          return;
        }
      }
      this.statFile =
          new File(
                  new File(
                      new File(new File("/proc", Integer.toString(i2)), "task"),
                      Integer.toString(i)),
                  "stat")
              .toString();
      this.cmdlineFile = null;
      this.threadsDir = null;
      this.threadStats = null;
      this.workingThreads = null;
    }
  }

  public ProcessStats(boolean z) {
    this.mIncludeThreads = z;
  }

  public void init() {
    Log.v("ProcessStats", "Init: " + this);
    this.mFirst = true;
    update();
  }

  public void update() {
    ProcessStats processStats;
    String str;
    Log.v("ProcessStats", "Update: " + this);
    this.mLastSampleTime = this.mCurrentSampleTime;
    this.mCurrentSampleTime = SystemClock.uptimeMillis();
    this.mLastSampleRealTime = this.mCurrentSampleRealTime;
    this.mCurrentSampleRealTime = SystemClock.elapsedRealtime();
    long[] jArr = this.mSystemCpuData;
    if (Process.readProcFile("/proc/stat", SYSTEM_CPU_FORMAT, null, jArr, null)) {
      long j = jArr[0] + jArr[1];
      long j2 = jArr[2];
      long j3 = jArr[3];
      long j4 = jArr[4];
      long j5 = jArr[5];
      long j6 = jArr[6];
      this.mRelUserTime = (int) (j - this.mBaseUserTime);
      this.mRelSystemTime = (int) (j2 - this.mBaseSystemTime);
      this.mRelIoWaitTime = (int) (j4 - this.mBaseIoWaitTime);
      this.mRelIrqTime = (int) (j5 - this.mBaseIrqTime);
      this.mRelSoftIrqTime = (int) (j6 - this.mBaseSoftIrqTime);
      this.mRelIdleTime = (int) (j3 - this.mBaseIdleTime);
      StringBuilder sb = new StringBuilder();
      sb.append("Total U:");
      sb.append(jArr[0]);
      sb.append(" N:");
      str = "ProcessStats";
      sb.append(jArr[1]);
      sb.append(" S:");
      sb.append(jArr[2]);
      sb.append(" I:");
      sb.append(jArr[3]);
      sb.append(" W:");
      sb.append(jArr[4]);
      sb.append(" Q:");
      sb.append(jArr[5]);
      sb.append(" O:");
      sb.append(jArr[6]);
      Log.i("Load", sb.toString());
      StringBuilder sb2 = new StringBuilder();
      sb2.append("Rel U:");
      processStats = this;
      sb2.append(processStats.mRelUserTime);
      sb2.append(" S:");
      sb2.append(processStats.mRelSystemTime);
      sb2.append(" I:");
      sb2.append(processStats.mRelIdleTime);
      sb2.append(" Q:");
      sb2.append(processStats.mRelIrqTime);
      Log.i("Load", sb2.toString());
      processStats.mBaseUserTime = j;
      processStats.mBaseSystemTime = j2;
      processStats.mBaseIoWaitTime = j4;
      processStats.mBaseIrqTime = j5;
      processStats.mBaseSoftIrqTime = j6;
      processStats.mBaseIdleTime = j3;
    } else {
      processStats = this;
      str = "ProcessStats";
    }
    processStats.mCurPids =
        collectStats(
            "/proc", -1, processStats.mFirst, processStats.mCurPids, processStats.mProcStats);
    float[] fArr = processStats.mLoadAverageData;
    if (Process.readProcFile("/proc/loadavg", LOAD_AVERAGE_FORMAT, null, null, fArr)) {
      float f = fArr[0];
      float f2 = fArr[1];
      float f3 = fArr[2];
      if (f != processStats.mLoad1 || f2 != processStats.mLoad5 || f3 != processStats.mLoad15) {
        processStats.mLoad1 = f;
        processStats.mLoad5 = f2;
        processStats.mLoad15 = f3;
        processStats.onLoadChanged(f, f2, f3);
      }
    }
    Log.i(
        str,
        "*** TIME TO COLLECT STATS: "
            + (SystemClock.uptimeMillis() - processStats.mCurrentSampleTime));
    processStats.mWorkingProcsSorted = false;
    processStats.mFirst = false;
  }

  public final int[] collectStats(String str, int i, boolean z, int[] iArr, ArrayList arrayList) {
    int i2;
    String str2;
    String str3;
    int i3;
    int i4;
    String str4;
    int i5;
    String str5;
    String str6;
    int i6;
    int i7;
    int i8;
    int i9;
    long j;
    String str7;
    long j2;
    String str8;
    long j3;
    long j4;
    int i10 = i;
    ArrayList arrayList2 = arrayList;
    int[] pids = Process.getPids(str, iArr);
    int length = pids == null ? 0 : pids.length;
    int size = arrayList.size();
    int i11 = 0;
    int i12 = 0;
    while (i12 < length && (i2 = pids[i12]) >= 0) {
      Stats stats = i11 < size ? (Stats) arrayList2.get(i11) : null;
      int i13 = length;
      int[] iArr2 = pids;
      if (stats == null) {
        str2 = " majfaults=";
        str3 = "Load";
        i3 = size;
        i4 = i12;
        str4 = " stime=";
      } else if (stats.pid == i2) {
        stats.added = false;
        stats.working = false;
        int i14 = i11 + 1;
        StringBuilder sb = new StringBuilder();
        sb.append("Existing ");
        sb.append(i10 < 0 ? "process" : "thread");
        sb.append(" pid ");
        sb.append(i2);
        sb.append(": ");
        sb.append(stats);
        Log.v("ProcessStats", sb.toString());
        if (stats.interesting) {
          long uptimeMillis = SystemClock.uptimeMillis();
          long[] jArr = this.mProcessStatsData;
          if (Process.readProcFile(
              stats.statFile.toString(), PROCESS_STATS_FORMAT, null, jArr, null)) {
            long j5 = jArr[0];
            i8 = size;
            i9 = i12;
            long j6 = jArr[1];
            long j7 = jArr[2];
            long j8 = jArr[3];
            if (j7 == stats.base_utime && j8 == stats.base_stime) {
              stats.rel_utime = 0;
              stats.rel_stime = 0;
              stats.rel_minfaults = 0;
              stats.rel_majfaults = 0;
              if (stats.active) {
                stats.active = false;
              }
            } else {
              if (!stats.active) {
                stats.active = true;
              }
              if (i10 < 0) {
                getName(stats, stats.cmdlineFile);
                ArrayList arrayList3 = stats.threadStats;
                if (arrayList3 != null) {
                  j = j6;
                  j4 = j7;
                  str7 = " stime=";
                  j2 = uptimeMillis;
                  j3 = j5;
                  str8 = " majfaults=";
                  this.mCurThreadPids =
                      collectStats(stats.threadsDir, i2, false, this.mCurThreadPids, arrayList3);
                  StringBuilder sb2 = new StringBuilder();
                  sb2.append("Stats changed ");
                  sb2.append(stats.name);
                  sb2.append(" pid=");
                  sb2.append(stats.pid);
                  sb2.append(" utime=");
                  sb2.append(j4);
                  sb2.append(PackageManagerShellCommandDataLoader.STDIN_PATH);
                  sb2.append(stats.base_utime);
                  sb2.append(str7);
                  sb2.append(j8);
                  sb2.append(PackageManagerShellCommandDataLoader.STDIN_PATH);
                  sb2.append(stats.base_stime);
                  sb2.append(" minfaults=");
                  long j9 = j3;
                  sb2.append(j9);
                  sb2.append(PackageManagerShellCommandDataLoader.STDIN_PATH);
                  sb2.append(stats.base_minfaults);
                  sb2.append(str8);
                  long j10 = j;
                  sb2.append(j10);
                  sb2.append(PackageManagerShellCommandDataLoader.STDIN_PATH);
                  sb2.append(stats.base_majfaults);
                  Log.v("Load", sb2.toString());
                  long j11 = j2;
                  stats.rel_uptime = j11 - stats.base_uptime;
                  stats.base_uptime = j11;
                  stats.rel_utime = (int) (j4 - stats.base_utime);
                  stats.rel_stime = (int) (j8 - stats.base_stime);
                  stats.base_utime = j4;
                  stats.base_stime = j8;
                  stats.rel_minfaults = (int) (j9 - stats.base_minfaults);
                  stats.rel_majfaults = (int) (j10 - stats.base_majfaults);
                  stats.base_minfaults = j9;
                  stats.base_majfaults = j10;
                  stats.working = true;
                }
              }
              j = j6;
              str7 = " stime=";
              j2 = uptimeMillis;
              str8 = " majfaults=";
              j3 = j5;
              j4 = j7;
              StringBuilder sb22 = new StringBuilder();
              sb22.append("Stats changed ");
              sb22.append(stats.name);
              sb22.append(" pid=");
              sb22.append(stats.pid);
              sb22.append(" utime=");
              sb22.append(j4);
              sb22.append(PackageManagerShellCommandDataLoader.STDIN_PATH);
              sb22.append(stats.base_utime);
              sb22.append(str7);
              sb22.append(j8);
              sb22.append(PackageManagerShellCommandDataLoader.STDIN_PATH);
              sb22.append(stats.base_stime);
              sb22.append(" minfaults=");
              long j92 = j3;
              sb22.append(j92);
              sb22.append(PackageManagerShellCommandDataLoader.STDIN_PATH);
              sb22.append(stats.base_minfaults);
              sb22.append(str8);
              long j102 = j;
              sb22.append(j102);
              sb22.append(PackageManagerShellCommandDataLoader.STDIN_PATH);
              sb22.append(stats.base_majfaults);
              Log.v("Load", sb22.toString());
              long j112 = j2;
              stats.rel_uptime = j112 - stats.base_uptime;
              stats.base_uptime = j112;
              stats.rel_utime = (int) (j4 - stats.base_utime);
              stats.rel_stime = (int) (j8 - stats.base_stime);
              stats.base_utime = j4;
              stats.base_stime = j8;
              stats.rel_minfaults = (int) (j92 - stats.base_minfaults);
              stats.rel_majfaults = (int) (j102 - stats.base_majfaults);
              stats.base_minfaults = j92;
              stats.base_majfaults = j102;
              stats.working = true;
            }
            i5 = i;
            i11 = i14;
            size = i8;
            i7 = i9;
            i6 = 1;
            i12 = i7 + i6;
            arrayList2 = arrayList;
            i10 = i5;
            length = i13;
            pids = iArr2;
          }
        }
        i8 = size;
        i9 = i12;
        i5 = i;
        i11 = i14;
        size = i8;
        i7 = i9;
        i6 = 1;
        i12 = i7 + i6;
        arrayList2 = arrayList;
        i10 = i5;
        length = i13;
        pids = iArr2;
      } else {
        str3 = "Load";
        i3 = size;
        i4 = i12;
        str4 = " stime=";
        str2 = " majfaults=";
      }
      if (stats == null || stats.pid > i2) {
        i5 = i;
        String str9 = str2;
        Stats stats2 = new Stats(i2, i5, this.mIncludeThreads);
        arrayList.add(i11, stats2);
        int i15 = i11 + 1;
        int i16 = i3 + 1;
        StringBuilder sb3 = new StringBuilder();
        String str10 = str4;
        sb3.append("New ");
        sb3.append(i5 < 0 ? "process" : "thread");
        sb3.append(" pid ");
        sb3.append(i2);
        sb3.append(": ");
        sb3.append(stats2);
        Log.v("ProcessStats", sb3.toString());
        String[] strArr = this.mProcessFullStatsStringData;
        long[] jArr2 = this.mProcessFullStatsData;
        stats2.base_uptime = SystemClock.uptimeMillis();
        if (Process.readProcFile(
            stats2.statFile.toString(), PROCESS_FULL_STATS_FORMAT, strArr, jArr2, null)) {
          stats2.interesting = true;
          stats2.baseName = strArr[0];
          str5 = str3;
          stats2.base_minfaults = jArr2[1];
          stats2.base_majfaults = jArr2[2];
          stats2.base_utime = jArr2[3];
          stats2.base_stime = jArr2[4];
        } else {
          str5 = str3;
          Log.w("ProcessStats", "Skipping unknown process pid " + i2);
          stats2.baseName = "<unknown>";
          stats2.base_stime = 0L;
          stats2.base_utime = 0L;
          stats2.base_majfaults = 0L;
          stats2.base_minfaults = 0L;
        }
        if (i5 < 0) {
          getName(stats2, stats2.cmdlineFile);
          ArrayList arrayList4 = stats2.threadStats;
          if (arrayList4 != null) {
            str6 = str10;
            this.mCurThreadPids =
                collectStats(stats2.threadsDir, i2, true, this.mCurThreadPids, arrayList4);
          } else {
            str6 = str10;
          }
        } else {
          str6 = str10;
          if (stats2.interesting) {
            String str11 = stats2.baseName;
            stats2.name = str11;
            stats2.nameWidth = onMeasureProcessName(str11);
          }
        }
        Log.v(
            str5,
            "Stats added "
                + stats2.name
                + " pid="
                + stats2.pid
                + " utime="
                + stats2.base_utime
                + str6
                + stats2.base_stime
                + " minfaults="
                + stats2.base_minfaults
                + str9
                + stats2.base_majfaults);
        stats2.rel_utime = 0;
        stats2.rel_stime = 0;
        stats2.rel_minfaults = 0;
        stats2.rel_majfaults = 0;
        i6 = 1;
        stats2.added = true;
        if (!z && stats2.interesting) {
          stats2.working = true;
        }
        size = i16;
        i11 = i15;
        i7 = i4;
        i12 = i7 + i6;
        arrayList2 = arrayList;
        i10 = i5;
        length = i13;
        pids = iArr2;
      } else {
        stats.rel_utime = 0;
        stats.rel_stime = 0;
        stats.rel_minfaults = 0;
        stats.rel_majfaults = 0;
        stats.removed = true;
        stats.working = true;
        arrayList.remove(i11);
        size = i3 - 1;
        StringBuilder sb4 = new StringBuilder();
        sb4.append("Removed ");
        i5 = i;
        sb4.append(i5 < 0 ? "process" : "thread");
        sb4.append(" pid ");
        sb4.append(i2);
        sb4.append(": ");
        sb4.append(stats);
        Log.v("ProcessStats", sb4.toString());
        i7 = i4 - 1;
        i6 = 1;
        i12 = i7 + i6;
        arrayList2 = arrayList;
        i10 = i5;
        length = i13;
        pids = iArr2;
      }
    }
    int[] iArr3 = pids;
    int i17 = size;
    while (i11 < i17) {
      Stats stats3 = (Stats) arrayList.get(i11);
      stats3.rel_utime = 0;
      stats3.rel_stime = 0;
      stats3.rel_minfaults = 0;
      stats3.rel_majfaults = 0;
      stats3.removed = true;
      stats3.working = true;
      arrayList.remove(i11);
      i17--;
      Log.v("ProcessStats", "Removed pid " + stats3.pid + ": " + stats3);
    }
    return iArr3;
  }

  public final int getLastUserTime() {
    return this.mRelUserTime;
  }

  public final int getLastSystemTime() {
    return this.mRelSystemTime;
  }

  public final int getLastIrqTime() {
    return this.mRelIrqTime;
  }

  public final int getLastIdleTime() {
    return this.mRelIdleTime;
  }

  public final void buildWorkingProcs() {
    if (this.mWorkingProcsSorted) {
      return;
    }
    this.mWorkingProcs.clear();
    int size = this.mProcStats.size();
    for (int i = 0; i < size; i++) {
      Stats stats = (Stats) this.mProcStats.get(i);
      if (stats.working) {
        this.mWorkingProcs.add(stats);
        ArrayList arrayList = stats.threadStats;
        if (arrayList != null && arrayList.size() > 1) {
          stats.workingThreads.clear();
          int size2 = stats.threadStats.size();
          for (int i2 = 0; i2 < size2; i2++) {
            Stats stats2 = (Stats) stats.threadStats.get(i2);
            if (stats2.working) {
              stats.workingThreads.add(stats2);
            }
          }
          Collections.sort(stats.workingThreads, sLoadComparator);
        }
      }
    }
    Collections.sort(this.mWorkingProcs, sLoadComparator);
    this.mWorkingProcsSorted = true;
  }

  public final int countWorkingStats() {
    buildWorkingProcs();
    return this.mWorkingProcs.size();
  }

  public final Stats getWorkingStats(int i) {
    return (Stats) this.mWorkingProcs.get(i);
  }

  /* JADX WARN: Code restructure failed: missing block: B:31:0x0043, code lost:

     if (r2 == null) goto L29;
  */
  /* JADX WARN: Code restructure failed: missing block: B:33:0x003f, code lost:

     if (r2 == null) goto L29;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final String readFile(String str, char c) {
    FileInputStream fileInputStream;
    StrictMode.ThreadPolicy allowThreadDiskReads = StrictMode.allowThreadDiskReads();
    FileInputStream fileInputStream2 = null;
    try {
      fileInputStream = new FileInputStream(str);
      try {
        int read = fileInputStream.read(this.mBuffer);
        fileInputStream.close();
        if (read > 0) {
          int i = 0;
          while (i < read && this.mBuffer[i] != c) {
            i++;
          }
          String str2 = new String(this.mBuffer, 0, i);
          try {
            fileInputStream.close();
          } catch (IOException unused) {
          }
          StrictMode.setThreadPolicy(allowThreadDiskReads);
          return str2;
        }
      } catch (FileNotFoundException unused2) {
      } catch (IOException unused3) {
      } catch (Throwable th) {
        th = th;
        fileInputStream2 = fileInputStream;
        if (fileInputStream2 != null) {
          try {
            fileInputStream2.close();
          } catch (IOException unused4) {
          }
        }
        StrictMode.setThreadPolicy(allowThreadDiskReads);
        throw th;
      }
    } catch (FileNotFoundException unused5) {
      fileInputStream = null;
    } catch (IOException unused6) {
      fileInputStream = null;
    } catch (Throwable th2) {
      th = th2;
    }
    try {
      fileInputStream.close();
    } catch (IOException unused7) {
    }
    StrictMode.setThreadPolicy(allowThreadDiskReads);
    return null;
  }

  public final void getName(Stats stats, String str) {
    String str2 = stats.name;
    if (str2 == null || str2.equals("app_process") || stats.name.equals("<pre-initialized>")) {
      String readFile = readFile(str, (char) 0);
      if (readFile != null && readFile.length() > 1) {
        int lastIndexOf = readFile.lastIndexOf("/");
        if (lastIndexOf > 0 && lastIndexOf < readFile.length() - 1) {
          readFile = readFile.substring(lastIndexOf + 1);
        }
        str2 = readFile;
      }
      if (str2 == null) {
        str2 = stats.baseName;
      }
    }
    String str3 = stats.name;
    if (str3 == null || !str2.equals(str3)) {
      stats.name = str2;
      stats.nameWidth = onMeasureProcessName(str2);
    }
  }
}
