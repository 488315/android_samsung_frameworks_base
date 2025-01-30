package com.android.server.am;

import android.os.Build;
import android.os.Debug;
import android.os.FileUtils;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.util.Slog;
import android.util.SparseBooleanArray;
import com.android.internal.os.ProcessCpuTracker;
import com.android.internal.os.anr.AnrLatencyTracker;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.backup.BackupManagerConstants;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;

/* loaded from: classes.dex */
public abstract class StackTracesDumpHelper {
  public static final SimpleDateFormat ANR_FILE_DATE_FORMAT =
      new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS");
  public static final int NATIVE_DUMP_TIMEOUT_MS;
  public static final int TEMP_DUMP_TIME_LIMIT;

  static {
    int i = Build.HW_TIMEOUT_MULTIPLIER;
    NATIVE_DUMP_TIMEOUT_MS = i * 2000;
    TEMP_DUMP_TIME_LIMIT = i * 10000;
  }

  public static File dumpStackTraces(
      ArrayList arrayList,
      ProcessCpuTracker processCpuTracker,
      SparseBooleanArray sparseBooleanArray,
      Future future,
      StringWriter stringWriter,
      Executor executor,
      AnrLatencyTracker anrLatencyTracker) {
    return dumpStackTraces(
        arrayList,
        processCpuTracker,
        sparseBooleanArray,
        future,
        stringWriter,
        null,
        null,
        null,
        null,
        executor,
        null,
        anrLatencyTracker);
  }

  public static File dumpStackTraces(
      ArrayList arrayList,
      ProcessCpuTracker processCpuTracker,
      SparseBooleanArray sparseBooleanArray,
      Future future,
      StringWriter stringWriter,
      String str,
      String str2,
      Executor executor,
      AnrLatencyTracker anrLatencyTracker) {
    return dumpStackTraces(
        arrayList,
        processCpuTracker,
        sparseBooleanArray,
        future,
        stringWriter,
        null,
        str,
        str2,
        null,
        executor,
        null,
        anrLatencyTracker);
  }

  public static File dumpStackTraces(
      ArrayList arrayList,
      final ProcessCpuTracker processCpuTracker,
      final SparseBooleanArray sparseBooleanArray,
      Future future,
      StringWriter stringWriter,
      AtomicLong atomicLong,
      String str,
      String str2,
      String str3,
      Executor executor,
      Future future2,
      final AnrLatencyTracker anrLatencyTracker) {
    String str4;
    String str5;
    if (anrLatencyTracker != null) {
      try {
        anrLatencyTracker.dumpStackTracesStarted();
      } catch (Throwable th) {
        if (anrLatencyTracker != null) {
          anrLatencyTracker.dumpStackTracesEnded();
        }
        throw th;
      }
    }
    Slog.i("ActivityManager", "dumpStackTraces pids=" + sparseBooleanArray);
    Supplier supplier =
        processCpuTracker != null
            ? new Supplier() { // from class:
                               // com.android.server.am.StackTracesDumpHelper$$ExternalSyntheticLambda0
              @Override // java.util.function.Supplier
              public final Object get() {
                ArrayList extraPids;
                extraPids =
                    StackTracesDumpHelper.getExtraPids(
                        processCpuTracker, sparseBooleanArray, anrLatencyTracker);
                return extraPids;
              }
            }
            : null;
    CompletableFuture supplyAsync =
        supplier != null ? CompletableFuture.supplyAsync(supplier, executor) : null;
    File file = new File("/data/anr");
    try {
      File createAnrDumpFile = createAnrDumpFile(file);
      if (str != null || str2 != null || str3 != null) {
        String absolutePath = createAnrDumpFile.getAbsolutePath();
        StringBuilder sb = new StringBuilder();
        if (str != null) {
          str4 = "Subject: " + str + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE;
        } else {
          str4 = "";
        }
        sb.append(str4);
        if (str3 != null) {
          str5 = str3 + "\n\n";
        } else {
          str5 = "";
        }
        sb.append(str5);
        sb.append(str2 != null ? str2 : "");
        appendtoANRFile(absolutePath, sb.toString());
      }
      long dumpStackTraces =
          dumpStackTraces(
              createAnrDumpFile.getAbsolutePath(),
              arrayList,
              future,
              supplyAsync,
              future2,
              anrLatencyTracker);
      if (atomicLong != null) {
        atomicLong.set(dumpStackTraces);
      }
      maybePruneOldTraces(file);
      if (anrLatencyTracker != null) {
        anrLatencyTracker.dumpStackTracesEnded();
      }
      return createAnrDumpFile;
    } catch (IOException e) {
      Slog.w("ActivityManager", "Exception creating ANR dump file:", e);
      if (stringWriter != null) {
        stringWriter.append("----- Exception creating ANR dump file -----\n");
        e.printStackTrace(new PrintWriter(stringWriter));
      }
      if (anrLatencyTracker != null) {
        anrLatencyTracker.anrSkippedDumpStackTraces();
      }
      if (anrLatencyTracker != null) {
        anrLatencyTracker.dumpStackTracesEnded();
      }
      return null;
    }
  }

  /* JADX WARN: Multi-variable type inference failed */
  /* JADX WARN: Type inference failed for: r3v2 */
  public static long dumpStackTraces(
      String str,
      ArrayList arrayList,
      Future future,
      Future future2,
      Future future3,
      AnrLatencyTracker anrLatencyTracker) {
    boolean z;
    Slog.i("ActivityManager", "Dumping to " + str);
    long j = (long) (Build.HW_TIMEOUT_MULTIPLIER * 20000);
    long j2 = -1;
    if (future3 == null || arrayList == null || arrayList.size() <= 0) {
      z = 0;
    } else {
      int intValue = ((Integer) arrayList.get(0)).intValue();
      long elapsedRealtime = SystemClock.elapsedRealtime();
      z = copyFirstPidTempDump(str, future3, j, anrLatencyTracker);
      j -= SystemClock.elapsedRealtime() - elapsedRealtime;
      if (j <= 0) {
        Slog.e(
            "ActivityManager",
            "Aborting stack trace dump (currently copying primary pid"
                + intValue
                + "); deadline exceeded.");
        return -1L;
      }
      if (z != 0 && intValue != ActivityManagerService.MY_PID) {
        j2 = new File(str).length();
      }
      if (z != 0 && anrLatencyTracker != null) {
        appendtoANRFile(str, anrLatencyTracker.dumpAsCommaSeparatedArrayWithHeader());
      }
    }
    if (arrayList != null) {
      if (anrLatencyTracker != null) {
        anrLatencyTracker.dumpingFirstPidsStarted();
      }
      int size = arrayList.size();
      int i = z;
      while (i < size) {
        int intValue2 = ((Integer) arrayList.get(i)).intValue();
        boolean z2 = i == 0 && ActivityManagerService.MY_PID != intValue2;
        Slog.i("ActivityManager", "Collecting stacks for pid " + intValue2);
        j -= dumpJavaTracesTombstoned(intValue2, str, j, anrLatencyTracker);
        if (j <= 0) {
          Slog.e(
              "ActivityManager",
              "Aborting stack trace dump (current firstPid=" + intValue2 + "); deadline exceeded.");
          return j2;
        }
        if (z2) {
          j2 = new File(str).length();
          if (anrLatencyTracker != null) {
            appendtoANRFile(str, anrLatencyTracker.dumpAsCommaSeparatedArrayWithHeader());
          }
        }
        i++;
      }
      if (anrLatencyTracker != null) {
        anrLatencyTracker.dumpingFirstPidsEnded();
      }
    }
    ArrayList collectPids = collectPids(future, "native pids");
    Slog.i("ActivityManager", "dumpStackTraces nativepids=" + collectPids);
    if (collectPids != null) {
      if (anrLatencyTracker != null) {
        anrLatencyTracker.dumpingNativePidsStarted();
      }
      Iterator it = collectPids.iterator();
      while (it.hasNext()) {
        int intValue3 = ((Integer) it.next()).intValue();
        Slog.i("ActivityManager", "Collecting stacks for native pid " + intValue3);
        long min = Math.min((long) NATIVE_DUMP_TIMEOUT_MS, j);
        if (anrLatencyTracker != null) {
          anrLatencyTracker.dumpingPidStarted(intValue3);
        }
        long elapsedRealtime2 = SystemClock.elapsedRealtime();
        Debug.dumpNativeBacktraceToFileTimeout(intValue3, str, (int) (min / 1000));
        long elapsedRealtime3 = SystemClock.elapsedRealtime() - elapsedRealtime2;
        if (anrLatencyTracker != null) {
          anrLatencyTracker.dumpingPidEnded();
        }
        j -= elapsedRealtime3;
        if (j <= 0) {
          Slog.e(
              "ActivityManager",
              "Aborting stack trace dump (current native pid="
                  + intValue3
                  + "); deadline exceeded.");
          return j2;
        }
      }
      if (anrLatencyTracker != null) {
        anrLatencyTracker.dumpingNativePidsEnded();
      }
    }
    ArrayList collectPids2 = collectPids(future2, "extra pids");
    if (future2 != null) {
      try {
        collectPids2 = (ArrayList) future2.get();
      } catch (InterruptedException e) {
        Slog.w("ActivityManager", "Interrupted while collecting extra pids", e);
      } catch (ExecutionException e2) {
        Slog.w("ActivityManager", "Failed to collect extra pids", e2.getCause());
      }
    }
    Slog.i("ActivityManager", "dumpStackTraces extraPids=" + collectPids2);
    if (collectPids2 != null) {
      if (anrLatencyTracker != null) {
        anrLatencyTracker.dumpingExtraPidsStarted();
      }
      Iterator it2 = collectPids2.iterator();
      while (it2.hasNext()) {
        int intValue4 = ((Integer) it2.next()).intValue();
        Slog.i("ActivityManager", "Collecting stacks for extra pid " + intValue4);
        j -= dumpJavaTracesTombstoned(intValue4, str, j, anrLatencyTracker);
        if (j <= 0) {
          Slog.e(
              "ActivityManager",
              "Aborting stack trace dump (current extra pid="
                  + intValue4
                  + "); deadline exceeded.");
          return j2;
        }
      }
      if (anrLatencyTracker != null) {
        anrLatencyTracker.dumpingExtraPidsEnded();
      }
    }
    appendtoANRFile(
        str,
        "----- dumping ended at "
            + SystemClock.uptimeMillis()
            + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
    Slog.i("ActivityManager", "Done dumping");
    return j2;
  }

  public static File dumpStackTracesTempFile(int i, AnrLatencyTracker anrLatencyTracker) {
    if (anrLatencyTracker != null) {
      try {
        anrLatencyTracker.dumpStackTracesTempFileStarted();
      } catch (Throwable th) {
        if (anrLatencyTracker != null) {
          anrLatencyTracker.dumpStackTracesTempFileEnded();
        }
        throw th;
      }
    }
    try {
      File createTempFile = File.createTempFile("temp_anr_", ".txt", new File("/data/anr"));
      Slog.d("ActivityManager", "created ANR temporary file:" + createTempFile.getAbsolutePath());
      Slog.i(
          "ActivityManager",
          "Collecting stacks for pid " + i + " into temporary file " + createTempFile.getName());
      if (anrLatencyTracker != null) {
        anrLatencyTracker.dumpingPidStarted(i);
      }
      String absolutePath = createTempFile.getAbsolutePath();
      int i2 = TEMP_DUMP_TIME_LIMIT;
      long dumpJavaTracesTombstoned = dumpJavaTracesTombstoned(i, absolutePath, i2);
      if (anrLatencyTracker != null) {
        anrLatencyTracker.dumpingPidEnded();
      }
      if (i2 <= dumpJavaTracesTombstoned) {
        Slog.e(
            "ActivityManager",
            "Aborted stack trace dump (current primary pid=" + i + "); deadline exceeded.");
        if (anrLatencyTracker != null) {
          anrLatencyTracker.dumpStackTracesTempFileTimedOut();
        }
      }
      if (anrLatencyTracker != null) {
        anrLatencyTracker.dumpStackTracesTempFileEnded();
      }
      return createTempFile;
    } catch (IOException e) {
      Slog.w("ActivityManager", "Exception creating temporary ANR dump file:", e);
      if (anrLatencyTracker != null) {
        anrLatencyTracker.dumpStackTracesTempFileCreationFailed();
      }
      if (anrLatencyTracker == null) {
        return null;
      }
      anrLatencyTracker.dumpStackTracesTempFileEnded();
      return null;
    }
  }

  /* JADX WARN: Not initialized variable reg: 3, insn: 0x0081: MOVE (r1 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r3 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]), block:B:50:0x0081 */
  /* JADX WARN: Removed duplicated region for block: B:52:0x0084  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public static boolean copyFirstPidTempDump(
      String str, Future future, long j, AnrLatencyTracker anrLatencyTracker) {
    boolean z;
    boolean z2;
    boolean z3 = false;
    try {
      try {
        z = true;
        FileOutputStream fileOutputStream = new FileOutputStream(str, true);
        if (anrLatencyTracker != null) {
          try {
            anrLatencyTracker.copyingFirstPidStarted();
          } catch (Throwable th) {
            try {
              fileOutputStream.close();
            } catch (Throwable th2) {
              th.addSuppressed(th2);
            }
            throw th;
          }
        }
        File file = (File) future.get(j, TimeUnit.MILLISECONDS);
        if (file == null) {
          fileOutputStream.close();
          if (anrLatencyTracker != null) {
            anrLatencyTracker.copyingFirstPidEnded(false);
          }
          return false;
        }
        Files.copy(file.toPath(), fileOutputStream);
        file.delete();
        try {
          fileOutputStream.close();
          if (anrLatencyTracker != null) {
            anrLatencyTracker.copyingFirstPidEnded(true);
          }
          return true;
        } catch (IOException e) {
          e = e;
          Slog.w("ActivityManager", "Failed to read the first pid's predump file", e);
          if (anrLatencyTracker != null) {
            anrLatencyTracker.copyingFirstPidEnded(z);
          }
          return false;
        } catch (InterruptedException e2) {
          e = e2;
          Slog.w(
              "ActivityManager",
              "Interrupted while collecting the first pid's predump to the main ANR file",
              e);
          if (anrLatencyTracker != null) {
            anrLatencyTracker.copyingFirstPidEnded(z);
          }
          return false;
        } catch (ExecutionException e3) {
          e = e3;
          Slog.w(
              "ActivityManager",
              "Failed to collect the first pid's predump to the main ANR file",
              e.getCause());
          if (anrLatencyTracker != null) {
            anrLatencyTracker.copyingFirstPidEnded(z);
          }
          return false;
        } catch (TimeoutException e4) {
          e = e4;
          Slog.w("ActivityManager", "Copying the first pid timed out", e);
          if (anrLatencyTracker != null) {
            anrLatencyTracker.copyingFirstPidEnded(z);
          }
          return false;
        }
      } catch (Throwable th3) {
        th = th3;
        z3 = z2;
        if (anrLatencyTracker != null) {
          anrLatencyTracker.copyingFirstPidEnded(z3);
        }
        throw th;
      }
    } catch (IOException e5) {
      e = e5;
      z = false;
    } catch (InterruptedException e6) {
      e = e6;
      z = false;
    } catch (ExecutionException e7) {
      e = e7;
      z = false;
    } catch (TimeoutException e8) {
      e = e8;
      z = false;
    } catch (Throwable th4) {
      th = th4;
      if (anrLatencyTracker != null) {}
      throw th;
    }
  }

  public static synchronized File createAnrDumpFile(File file) {
    File file2;
    synchronized (StackTracesDumpHelper.class) {
      file2 = new File(file, "anr_" + ANR_FILE_DATE_FORMAT.format(new Date()));
      if (file2.createNewFile()) {
        FileUtils.setPermissions(
            file2.getAbsolutePath(),
            FrameworkStatsLog.NON_A11Y_TOOL_SERVICE_WARNING_REPORT,
            -1,
            -1);
      } else {
        throw new IOException("Unable to create ANR dump file: createNewFile failed");
      }
    }
    return file2;
  }

  public static ArrayList getExtraPids(
      ProcessCpuTracker processCpuTracker,
      SparseBooleanArray sparseBooleanArray,
      AnrLatencyTracker anrLatencyTracker) {
    if (anrLatencyTracker != null) {
      anrLatencyTracker.processCpuTrackerMethodsCalled();
    }
    ArrayList arrayList = new ArrayList();
    processCpuTracker.init();
    try {
      Thread.sleep(200L);
    } catch (InterruptedException unused) {
    }
    processCpuTracker.update();
    int countWorkingStats = processCpuTracker.countWorkingStats();
    for (int i = 0; i < countWorkingStats && arrayList.size() < 2; i++) {
      ProcessCpuTracker.Stats workingStats = processCpuTracker.getWorkingStats(i);
      if (sparseBooleanArray.indexOfKey(workingStats.pid) >= 0) {
        arrayList.add(Integer.valueOf(workingStats.pid));
      } else {
        Slog.i(
            "ActivityManager",
            "Skipping next CPU consuming process, not a java proc: " + workingStats.pid);
      }
    }
    if (anrLatencyTracker != null) {
      anrLatencyTracker.processCpuTrackerMethodsReturned();
    }
    return arrayList;
  }

  public static void maybePruneOldTraces(File file) {
    File[] listFiles = file.listFiles();
    if (listFiles == null) {
      return;
    }
    int i = SystemProperties.getInt("tombstoned.max_anr_count", 64);
    long currentTimeMillis = System.currentTimeMillis();
    try {
      Arrays.sort(
          listFiles,
          Comparator.comparingLong(new StackTracesDumpHelper$$ExternalSyntheticLambda1())
              .reversed());
      for (int i2 = 0; i2 < listFiles.length; i2++) {
        if ((i2 > i
                || currentTimeMillis - listFiles[i2].lastModified()
                    > BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS)
            && !listFiles[i2].delete()) {
          Slog.w("ActivityManager", "Unable to prune stale trace file: " + listFiles[i2]);
        }
      }
    } catch (IllegalArgumentException e) {
      Slog.w(
          "ActivityManager", "tombstone modification times changed while sorting; not pruning", e);
    }
  }

  public static long dumpJavaTracesTombstoned(
      int i, String str, long j, AnrLatencyTracker anrLatencyTracker) {
    if (anrLatencyTracker != null) {
      try {
        anrLatencyTracker.dumpingPidStarted(i);
      } finally {
        if (anrLatencyTracker != null) {
          anrLatencyTracker.dumpingPidEnded();
        }
      }
    }
    return dumpJavaTracesTombstoned(i, str, j);
  }

  public static long dumpJavaTracesTombstoned(int i, String str, long j) {
    long elapsedRealtime = SystemClock.elapsedRealtime();
    int writeUptimeStartHeaderForPid = writeUptimeStartHeaderForPid(i, str);
    boolean dumpJavaBacktraceToFileTimeout =
        Debug.dumpJavaBacktraceToFileTimeout(i, str, (int) (j / 1000));
    if (dumpJavaBacktraceToFileTimeout) {
      try {
      } catch (Exception e) {
        Slog.w("ActivityManager", "Unable to get ANR file size", e);
      }
      if (new File(str).length() - writeUptimeStartHeaderForPid < 100) {
        Slog.w("ActivityManager", "Successfully created Java ANR file is empty!");
        dumpJavaBacktraceToFileTimeout = false;
      }
    }
    if (!dumpJavaBacktraceToFileTimeout) {
      Slog.w("ActivityManager", "Dumping Java threads failed, initiating native stack dump.");
      if (!Debug.dumpNativeBacktraceToFileTimeout(i, str, NATIVE_DUMP_TIMEOUT_MS / 1000)) {
        Slog.w("ActivityManager", "Native stack dump failed!");
      }
    }
    return SystemClock.elapsedRealtime() - elapsedRealtime;
  }

  public static int appendtoANRFile(String str, String str2) {
    try {
      FileOutputStream fileOutputStream = new FileOutputStream(str, true);
      try {
        byte[] bytes = str2.getBytes(StandardCharsets.UTF_8);
        fileOutputStream.write(bytes);
        int length = bytes.length;
        fileOutputStream.close();
        return length;
      } finally {
      }
    } catch (IOException e) {
      Slog.w("ActivityManager", "Exception writing to ANR dump file:", e);
      return 0;
    }
  }

  public static int writeUptimeStartHeaderForPid(int i, String str) {
    return appendtoANRFile(
        str,
        "----- dumping pid: "
            + i
            + " at "
            + SystemClock.uptimeMillis()
            + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
  }

  public static ArrayList collectPids(Future future, String str) {
    if (future == null) {
      return null;
    }
    try {
      return (ArrayList) future.get();
    } catch (InterruptedException e) {
      Slog.w("ActivityManager", "Interrupted while collecting " + str, e);
      return null;
    } catch (ExecutionException e2) {
      Slog.w("ActivityManager", "Failed to collect " + str, e2.getCause());
      return null;
    }
  }

  public static BinderTransaction.BinderProcsInfo getBinderTransactionInfo(int i) {
    return new BinderTransaction().getInfo(i);
  }
}
